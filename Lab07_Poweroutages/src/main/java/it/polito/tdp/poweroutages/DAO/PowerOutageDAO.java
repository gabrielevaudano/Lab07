 package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.NercMap;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {

	public List<Nerc> getNercList(NercMap map) {
		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if (map.get(res.getInt("id"))== null) {

					Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
					map.get(n);
				}

				nercList.add(map.get(res.getInt("id")));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutage> getPowerOutageList(NercMap map) {
		String sql = "SELECT id, nerc_id, customers_affected, date_event_began , date_event_finished "
				  + " FROM poweroutages ";
		
		List<PowerOutage> powerOutageList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if (map.get(res.getInt("nerc_id"))==null) {
					conn.close();
					throw new NullPointerException("Nerc inserita non esistente. Archivio inconsistente.");
				}
				
				Nerc nerc = map.get(res.getInt("nerc_id"));
				
				LocalDateTime beginDate = res.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime endDate = res.getTimestamp("date_event_finished").toLocalDateTime();
				
				Integer id = res.getInt("id");
				Integer customersAffected = res.getInt("customers_affected");
				
				PowerOutage po = new PowerOutage(id, nerc, beginDate, endDate, customersAffected);
				powerOutageList.add(po);
				
				conn.close();

			} 
			
		
		} catch (SQLException e) {
			throw new RuntimeException("Errore database");
		}

		return powerOutageList;
	}
	
	public Map<Integer, PowerOutage> getPowerOutageListByNerc(NercMap map, Nerc n) {
		String sql = "SELECT id, nerc_id, date_event_began, date_event_finished, customers_affected FROM poweroutages "
			       + "	WHERE nerc_id = ? ";
		Map<Integer, PowerOutage> powerOutageEventList = new LinkedHashMap<Integer, PowerOutage>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, n.getId());

			ResultSet res = st.executeQuery();
			
			
			while (res.next()) {
				Nerc nerc = map.get(res.getInt("nerc_id"));
				
				if (nerc == null) {
					System.err.println("Database is not consistent. Missing corresponding NERC");
				} else {
					PowerOutage poe = new PowerOutage(res.getInt("id"), nerc,
							res.getTimestamp("date_event_began").toLocalDateTime(),
							res.getTimestamp("date_event_finished").toLocalDateTime(),
							res.getInt("customers_affected"));

					powerOutageEventList.put(res.getInt("id"), poe);
				}

			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return powerOutageEventList;
	}
	

}
