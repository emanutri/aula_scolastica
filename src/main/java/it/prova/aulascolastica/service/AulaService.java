package it.prova.aulascolastica.service;

import java.util.List;

import it.prova.aulascolastica.model.Aula;

public interface AulaService {

	public List<Aula> listAllAule();

	public Aula caricaSingolaAula(Long id);

	public Aula caricaSingolaAulaEagerStudenti(Long idAula);

	public void aggiorna(Aula aulaInstance);

	public void inserisciNuovo(Aula aulaInstance);

	public void rimuovi(Aula aulaInstance);

	public List<Aula> findByExample(Aula example);

}
