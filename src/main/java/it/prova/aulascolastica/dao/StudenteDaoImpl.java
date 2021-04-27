package it.prova.aulascolastica.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.prova.aulascolastica.model.Aula;
import it.prova.aulascolastica.model.Studente;

@Component
public class StudenteDaoImpl implements StudenteDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Studente> list() {
		return entityManager.createQuery("from Studente", Studente.class).getResultList();
	}

	@Override
	public Studente get(Long id) {
		return entityManager.find(Studente.class, id);
	}

	@Override
	public void update(Studente studenteInstance) {
		studenteInstance = entityManager.merge(studenteInstance);
	}

	@Override
	public void insert(Studente studenteInstance) {
		entityManager.persist(studenteInstance);
	}

	@Override
	public void delete(Studente studenteInstance) {
		entityManager.remove(entityManager.merge(studenteInstance));
	}

	@Override
	public List<Studente> findByExample(Studente studenteInstance) {

		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder(
				"select s from Studente s join fetch s.aula a where s.id = s.id ");

		if (StringUtils.isNotEmpty(studenteInstance.getNome())) {
			whereClauses.add(" s.nome  like :nome ");
			paramaterMap.put("nome", "%" + studenteInstance.getNome() + "%");
		}
		if (StringUtils.isNotEmpty(studenteInstance.getCognome())) {
			whereClauses.add(" s.cognome like :cognome ");
			paramaterMap.put("cognome", "%" + studenteInstance.getCognome() + "%");
		}
		if (studenteInstance.getDataNascita() != null) {
			whereClauses.add("s.dataNascita >= :dataNascita ");
			paramaterMap.put("dataNascita", studenteInstance.getDataNascita());
		}
		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		System.out.println(queryBuilder.toString());
		TypedQuery<Studente> typedQuery = entityManager.createQuery(queryBuilder.toString(), Studente.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();

	}

	@Override
	public List<Studente> findAllByAula(Aula input) {
		TypedQuery<Studente> query = entityManager
				.createQuery("select s FROM Studente s JOIN FETCH s.aula a where s.aula =:aulaInput", Studente.class);
		return query.setParameter("aulaInput", input).getResultList();
	}

}
