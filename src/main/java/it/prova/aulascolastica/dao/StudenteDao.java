package it.prova.aulascolastica.dao;

import java.util.List;

import it.prova.aulascolastica.model.Aula;
import it.prova.aulascolastica.model.Studente;

public interface StudenteDao {

	public List<Studente> list();

	public Studente get(Long id);

	public void update(Studente studenteInstance);

	public void insert(Studente studenteInstance);

	public void delete(Studente studenteInstance);

	public List<Studente> findByExample(Studente studenteInstance);

	public List<Studente> findAllByAula(Aula input);

}
