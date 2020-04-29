package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.polito.tdp.poweroutages.DAO.*;

public class Model {
	
	private PowerOutageDAO podao;
	private NercMap map;

	private Map<Integer, PowerOutage> powerOutages;
	private List<PowerOutage> maxPowerOutages;

	private int maxCustomerAffected;
	
	public Model() {
		podao = new PowerOutageDAO();	
		map = new NercMap();
		maxCustomerAffected = 0;
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList(map);
	}
	
	public List<PowerOutage> getPowerOutage() {
		return podao.getPowerOutageList(map);
	}
	
	
	private boolean checkHours(LinkedHashMap<Integer, PowerOutage> parziale, int maxHours) {
		int hours = 0;
		
		for(PowerOutage po : parziale.values())
			hours += po.getPeriod();
		
		if (hours > maxHours)
			return false;
		
		return true;
	}
	
	private boolean checkYears(Map<Integer, PowerOutage> parziale, int maxYears) {
		if (parziale.size()<2)
			return true;
		
		List<PowerOutage> dati = new ArrayList<PowerOutage>(parziale.values());
		
		int y1 = dati.get(0).getYear();
		int y2 = dati.get(parziale.size()-1).getYear();
		
		if (Math.abs(y2 - y1 + 1) >= maxYears) {
			return false;
		}
	
		return true;

	}	

	private Map<Integer, PowerOutage> sortPowerOutageMap(Map<Integer, PowerOutage> map) {
		List<Map.Entry<Integer, PowerOutage>> entries =
				  new ArrayList<Map.Entry<Integer, PowerOutage>>(map.entrySet());
		
		Collections.sort(entries, new Comparator<Map.Entry<Integer, PowerOutage>>() {
			  public int compare(Map.Entry<Integer, PowerOutage> a, Map.Entry<Integer, PowerOutage> b){
			    return a.getValue().getDateEventBegan().compareTo(b.getValue().getDateEventBegan());
			  }
			});
		
		Map<Integer, PowerOutage> mapping = new LinkedHashMap<Integer, PowerOutage>();
		for (Entry<Integer, PowerOutage> entry : entries) {
			mapping.put(entry.getKey(), entry.getValue());
		}
		
		return mapping;
	}
	
	
	public int sumAffectedPeople(LinkedHashMap<Integer, PowerOutage> parziale) {
		int sum = 0;
		List<PowerOutage> dati = new ArrayList<PowerOutage>(parziale.values());
		
		for (PowerOutage poe : dati) {
			sum += poe.getCustomerAffected();
		}
		return sum;
	}
	
	public List<PowerOutage> ricercaWorstCase(Nerc n, Integer maxYears, Integer maxHours) {
		this.getNercList();
		
		powerOutages = this.sortPowerOutageMap(podao.getPowerOutageListByNerc(map, n));
		
		cerca(new LinkedHashMap<Integer, PowerOutage>(), maxYears, maxHours);
		return maxPowerOutages;
	}
	

	
	private void cerca(LinkedHashMap<Integer, PowerOutage> parziale, int maxYears, int maxHours) {
		// Condizione di terminazione
		if (sumAffectedPeople(parziale) >= maxCustomerAffected) {

			maxCustomerAffected = sumAffectedPeople(parziale);
			maxPowerOutages = new ArrayList<PowerOutage>(parziale.values());
		}
		
		// Condizione generale
		
		 for (Entry<Integer, PowerOutage> poe : powerOutages.entrySet()) {
			Integer key = poe.getKey();
			
			if (!parziale.containsKey(key)) {
				PowerOutage poeValue = poe.getValue();
				parziale.put(key, poeValue);
				
				if (checkYears(parziale, maxYears) && checkHours(parziale, maxHours)) {
						cerca(parziale, maxYears, maxHours);
					}
				}

				parziale.remove(poe.getKey());
			}
		 }
}
