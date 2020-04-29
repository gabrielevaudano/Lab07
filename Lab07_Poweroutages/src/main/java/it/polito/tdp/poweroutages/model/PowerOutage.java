package it.polito.tdp.poweroutages.model;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutage {
	private int id;
	private Nerc nerc;
	private LocalDateTime dateEventBegan;
	private LocalDateTime dateEventFinished;
	private int customerAffected;
	
	private long period;
	private int year;
	
	public PowerOutage(int id, Nerc nerc, LocalDateTime dateEventBegan, LocalDateTime dateEventFinished,
			int customerAffected) {
		
		this.id = id;
		this.nerc = nerc;
		this.dateEventBegan = dateEventBegan;
		this.dateEventFinished = dateEventFinished;
		this.customerAffected = customerAffected;
		
		this.period = dateEventBegan.until(dateEventFinished, ChronoUnit.HOURS);
		this.year = dateEventBegan.getYear();
	}

	public int getId() {
		return id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public LocalDateTime getDateEventBegan() {
		return dateEventBegan;
	}

	public LocalDateTime getDateEventFinished() {
		return dateEventFinished;
	}


	public int getCustomerAffected() {
		return customerAffected;
	}

	public long getPeriod() {
		return this.period;
	}


	public int getYear() {
		return year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("\t%s\t%s\t%s\t%s\t%s", year, dateEventBegan, dateEventFinished, customerAffected, period);
	}


	
	
	
	
}
