package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;

import it.polito.tdp.poweroutages.model.NercMap;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		NercMap map = new NercMap();
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			dao.getNercList(map);
			System.out.println(dao.getPowerOutageList(map)) ;

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

	}

}
