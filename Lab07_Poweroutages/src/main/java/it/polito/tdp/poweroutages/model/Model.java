package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.*;

public class Model {
	
	private PowerOutageDAO podao;
	private List<PowerOutage> powerOutages;
	private Long maxHours;
	private Integer startingYear;
	private Integer durationYear;
	private Integer maxCustomers;
	private List<PowerOutage> powerOutagesFinale;
	private NercMap map;
	
	public Model() {
		podao = new PowerOutageDAO();		
		maxHours = 0L;
		startingYear = 0;
		durationYear = 0;
		maxCustomers = 0;
		map = new NercMap();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList(map);
	}
	
	public List<PowerOutage> getPowerOutage() {
		return podao.getPowerOutageList(map);
	}
	
	public List<PowerOutage> ricercaWorstCase(Nerc n, Integer maxCustomers, Long maxHours) {
		this.getNercList();

		powerOutages = podao.getPowerOutageListByNerc(map, n);
		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		
		this.maxCustomers = maxCustomers;
		this.maxHours = maxHours;
		
		cerca(0L, parziale);
		
		return powerOutagesFinale;
	}
	
	private void cerca(Long livello, List<PowerOutage> parziale) {
		// Condizione di terminazione
		if(livello >= maxHours) {
			Integer customersAffected = 0;
			for (PowerOutage po: parziale)
				customersAffected += po.getCustomerAffected();
			
			// Generazione di una soluzione possibile
			if (customersAffected > maxCustomers) {
				maxCustomers = customersAffected;
				powerOutagesFinale = new ArrayList<PowerOutage>(parziale);
			}
			
			return;
		}
		
		// Condizione generale
		for(PowerOutage po : powerOutages) {
			if (parziale.size()==0)
				startingYear = po.getYear();
			
			if (this.isValido(po, livello)) {
				parziale.add(po);
				livello += po.getPeriod();
				
				cerca(livello, parziale);
				
				// Backtracking
				parziale.remove(po);
				livello -= po.getPeriod();
			}
		}
	}

	private boolean isValido(PowerOutage po, Long livello) {
		if ((po.getYear() - startingYear) >= durationYear || (po.getYear() - startingYear) < 0)
			return false;
		
		if (livello + po.getPeriod() > maxHours)
			return false;

		return true;
	}
	

}
