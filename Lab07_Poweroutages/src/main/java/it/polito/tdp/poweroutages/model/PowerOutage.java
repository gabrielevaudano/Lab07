package it.polito.tdp.poweroutages.model;
import java.time.Duration;
import java.time.LocalDateTime;

public class PowerOutage {
	private int id;
	private Nerc nerc;
	private LocalDateTime dateEventBegan;
	private LocalDateTime dateEventFinished;
	private Integer customerAffected;
	
	private Integer period;
	private Integer year;
	
	public PowerOutage(int id, Nerc nerc, LocalDateTime dateEventBegan, LocalDateTime dateEventFinished,
			Integer customerAffected) {
		
		this.id = id;
		this.nerc = nerc;
		this.dateEventBegan = dateEventBegan;
		this.dateEventFinished = dateEventFinished;
		this.customerAffected = customerAffected;
		
		Long period = Duration.between(dateEventBegan, dateEventFinished).toHours();
		this.period = period.intValue();
		
		if (dateEventBegan.getYear()==dateEventFinished.getYear())
			this.year = dateEventBegan.getYear();
		else 
			System.err.println("La valutazione da inserire è a cavallo tra due anni. Non verrà pertanto considerata.");
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


	public Integer getCustomerAffected() {
		return customerAffected;
	}

	public Integer getPeriod() {
		return this.period;
	}


	public Integer getYear() {
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
		return this.id + " : " + this.nerc + "; duration: " + this.period;
	}

	
	
	
	
}
