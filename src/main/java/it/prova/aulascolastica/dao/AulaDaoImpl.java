package it.prova.aulascolastica.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.prova.aulascolastica.model.Aula;

@Component
public class AulaDaoImpl implements AulaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Aula> list() {
		// dopo la from bisogna specificare il nome dell'oggetto (lettera
		// maiuscola) e non la tabella
		return entityManager.createQuery("from Aula", Aula.class).getResultList();
	}

	@Override
	public Aula get(Long id) {
		return entityManager.find(Aula.class, id);
	}

	@Override
	public Aula findEagerFetch(long idAula) {
		Query q = entityManager.createQuery("SELECT a FROM Aula a left JOIN FETCH a.studenti s WHERE a.id = :id");
		q.setParameter("id", idAula);
		return (Aula) q.getSingleResult();
	}

	@Override
	public void update(Aula aulaInstance) {
		aulaInstance = entityManager.merge(aulaInstance);
	}

	@Override
	public void insert(Aula aulaInstance) {
		entityManager.persist(aulaInstance);
	}

	@Override
	public void delete(Aula aulaInstance) {
		entityManager.remove(entityManager.merge(aulaInstance));
	}

	@Override
	public List<Aula> findByExample(Aula aulaInstance) {

		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder(
				"select a from Aula a join fetch a.studenti s where a.id = a.id ");

		if (StringUtils.isNotEmpty(aulaInstance.getCodice())) {
			whereClauses.add(" a.codice  like :codice ");
			paramaterMap.put("codice", "%" + aulaInstance.getCodice() + "%");
		}
		if (StringUtils.isNotEmpty(aulaInstance.getMateria())) {
			whereClauses.add(" a.materia like :materia ");
			paramaterMap.put("materia", "%" + aulaInstance.getMateria() + "%");
		}
		if (aulaInstance.getCapienza() > 0) {
			whereClauses.add(" a.capienza like :capienza ");
			paramaterMap.put("capienza", "=" + aulaInstance.getCapienza());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		System.out.println(queryBuilder.toString());
		TypedQuery<Aula> typedQuery = entityManager.createQuery(queryBuilder.toString(), Aula.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();

	}
}
