package it.prova.aulascolastica.dao;

import java.util.List;

import it.prova.aulascolastica.model.Aula;

public interface AulaDao {

	public List<Aula> list();

	public Aula get(Long id);

	public Aula findEagerFetch(long idAula);

	public void update(Aula aulaInstance);

	public void insert(Aula aulaInstance);

	public void delete(Aula aulaInstance);

	public List<Aula> findByExample(Aula aulaInstance);

}
