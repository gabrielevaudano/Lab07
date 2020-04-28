package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.*;

public class Model {
	
	private PowerOutageDAO podao;
	private NercMap map;

	private Integer maxHours;
	private Integer maxYears;
	private Integer maxCustomers;
	
	private Integer startingYear;

	private List<PowerOutage> powerOutagesFinale;
	private List<PowerOutage> powerOutages;

	
	public Model() {
		podao = new PowerOutageDAO();	
		map = new NercMap();

		maxCustomers = 0;
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList(map);
	}
	
	public List<PowerOutage> getPowerOutage() {
		return podao.getPowerOutageList(map);
	}
	

}
