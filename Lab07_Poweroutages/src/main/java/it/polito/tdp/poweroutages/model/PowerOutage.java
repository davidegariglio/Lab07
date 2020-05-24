package it.polito.tdp.poweroutages.model;

import java.time.*;

public class PowerOutage {

	private Nerc nerc;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	private Duration durata;
	private int vittime;
	
	public PowerOutage(Nerc nerc, LocalDateTime inizio, LocalDateTime fine, int vittime) {
		super();
		this.nerc = nerc;
		this.inizio = inizio;
		this.fine = fine;
		this.durata = Duration.between(inizio, fine);
		this.vittime = vittime;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public Duration getDurata() {
		return durata;
	}

	public int getVittime() {
		return vittime;
	}
	
	public String toString() {
		return this.inizio.toString()+"-"+this.fine.toString()+"persone coinvolte :"+this.vittime;
	}
	
	
}
