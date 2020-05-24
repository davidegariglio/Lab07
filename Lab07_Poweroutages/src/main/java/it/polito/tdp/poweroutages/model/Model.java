package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO dao;
	List<PowerOutage> best;
	List<PowerOutage> listaPO;
	
	public Model() {
		dao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return dao.getNercList();
	}
	
	public List<PowerOutage> doCalcola(Nerc nerc, int years, int hours){
		this.best = null;
		this.listaPO = new ArrayList<>(this.dao.getPOFromNerc(nerc));
		List<PowerOutage> parziale = new ArrayList<>();
		
		cerca(parziale, 0, years, hours);
		
		return best;
	}

	private void cerca(List<PowerOutage> parziale, int livello, int years, int hours) {

		if(calcolaVittime(parziale) > calcolaVittime(this.best)) {
			best = new ArrayList<PowerOutage>(parziale);
			
		}
		
		for(PowerOutage prova : this.listaPO) {
			if(aggiuntaValida(prova, parziale, years, hours)) {
				parziale.add(prova);
				cerca(parziale, livello+1, years, hours);
				parziale.remove(parziale.size()-1);
			}
		}
		return;
	}

	/**
	 * Verifica se il PO che si prova ad inserire nella soluzione parziale non sfora
	 * nel totale di ore e rispetta il vincolo degli anni
	 * @param prova PO che si prova ad inserire
	 * @param parziale Solzione parziale (Lista di PO)
	 * @param years Anni max inseriti in input dall'utente
	 * @param hours Ore max di disservizio
	 * @return true se l'aggiunta è valida, altrimetni false
	 */
	private boolean aggiuntaValida(PowerOutage prova, List<PowerOutage> parziale, int years, int hours) {
		//Se parziale è vuota, l'aggiunta è sicuramente valida
		if(parziale.isEmpty()) {
			return true;
		}
		if(calcolaDurata(parziale) + prova.getDurata().getSeconds() < hours*3600 
				&& parziale.get(0).getInizio().getYear()-prova.getInizio().getYear() <= years) {
			return true;
		}
		return false;
	}
	

	private long calcolaDurata(List<PowerOutage> parziale) {
		long totale = 0;
		for(PowerOutage po : parziale) {
			totale += po.getDurata().getSeconds(); 
		}
		return totale;
	}

	private int calcolaVittime(List<PowerOutage> parziale) {
		
		int vittime = 0;
		
		if(parziale == null) {
			return 0;
		}
		for(PowerOutage po : parziale) {
			vittime += po.getVittime();
		}
		
		return vittime;
	}

}
