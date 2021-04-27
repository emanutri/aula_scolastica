package it.prova.aulascolastica.service;

import java.util.List;

import it.prova.aulascolastica.model.Aula;
import it.prova.aulascolastica.model.Studente;

public interface StudenteService {

	public List<Studente> cercaStudentiInAula(Aula input);

	public List<Studente> listAllStudenti();

	public Studente caricaSingoloStudente(Long id);

	public void aggiorna(Studente studenteInstance);

	public void inserisciNuovo(Studente studenteInstance);

	public void rimuovi(Studente studenteInstance);

	public List<Studente> findByExample(Studente example);

	public Studente inserisciStudenteInAula(Studente studenteInput, Aula aulaInput);

}
