package it.polito.tdp.poweroutages.model;

import java.util.HashMap;
import java.util.Map;

public class NercMap {
	Map<Integer, Nerc> map;

	public NercMap() {
		this.map = new HashMap<Integer, Nerc>();
	}

	public Map<Integer, Nerc> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Nerc> map) {
		this.map = map;
	}
	
	public Nerc get(Integer id) {
		if (map.get(id)==null)
			return null;
		
		return map.get(id);
	}
	
	public Nerc get(Nerc n) {
		if (!map.containsValue(n))
			map.put(n.getId(), n);
		
		return map.get(n.getId());
	}
	
	
}
