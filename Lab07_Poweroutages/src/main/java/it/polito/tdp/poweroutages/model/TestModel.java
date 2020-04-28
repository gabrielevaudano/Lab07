package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();

		System.out.println(model.ricercaWorstCase(new Nerc(1, "ERCOT"), 4, 200));

	}
	
	

}
