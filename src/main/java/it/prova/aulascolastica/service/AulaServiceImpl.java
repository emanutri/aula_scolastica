package it.prova.aulascolastica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.aulascolastica.dao.AulaDao;
import it.prova.aulascolastica.model.Aula;

@Service
public class AulaServiceImpl implements AulaService {

	@Autowired
	private AulaDao aulaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Aula> listAllAule() {
		return aulaDao.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Aula caricaSingolaAula(Long id) {
		return aulaDao.get(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Aula caricaSingolaAulaEagerStudenti(Long idAula) {
		return aulaDao.findEagerFetch(idAula);
	}

	@Override
	@Transactional
	public void aggiorna(Aula aulaInstance) {
		aulaDao.update(aulaInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Aula aulaInstance) {
		aulaDao.insert(aulaInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Aula aulaInstance) {
		Aula aulaTemp = aulaDao.findEagerFetch(aulaInstance.getId());
		if (aulaTemp.getStudenti().isEmpty()) {
			aulaDao.delete(aulaInstance);
		} else {
			throw new RuntimeException("Impossibie eliminare un'aula piena");
		}
	}

	@Override
	@Transactional
	public List<Aula> findByExample(Aula example) {
		return aulaDao.findByExample(example);
	}
}
