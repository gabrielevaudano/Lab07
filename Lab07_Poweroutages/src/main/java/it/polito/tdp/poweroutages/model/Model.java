package it.polito.tdp.poweroutages.model;

import java.util.List;

import it.polito.tdp.poweroutages.DAO.*;

public class Model {
	
	private PowerOutageDAO podao;
	private NercMap map;

	
	public Model() {
		podao = new PowerOutageDAO();	
		map = new NercMap();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList(map);
	}
	
	public List<PowerOutage> getPowerOutage() {
		return podao.getPowerOutageList(map);
	}
	

}
