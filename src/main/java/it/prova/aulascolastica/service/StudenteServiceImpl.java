package it.prova.aulascolastica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.aulascolastica.dao.AulaDao;
import it.prova.aulascolastica.dao.StudenteDao;
import it.prova.aulascolastica.model.Aula;
import it.prova.aulascolastica.model.Studente;

@Service
public class StudenteServiceImpl implements StudenteService {

	@Autowired
	private StudenteDao studenteDao;

	@Autowired
	private AulaDao aulaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Studente> listAllStudenti() {
		return studenteDao.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Studente caricaSingoloStudente(Long id) {
		return studenteDao.get(id);
	}

	@Override
	@Transactional
	public void aggiorna(Studente studenteInstance) {
		studenteDao.update(studenteInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Studente studenteInstance) {
		studenteDao.insert(studenteInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Studente studenteInstance) {
		studenteDao.delete(studenteInstance);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Studente> findByExample(Studente example) {
		return studenteDao.findByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Studente> cercaStudentiInAula(Aula input) {
		return studenteDao.findAllByAula(input);
	}

	@Override
	@Transactional
	public Studente inserisciStudenteInAula(Studente studenteInput, Aula aulaInput) {

		if (aulaDao.findEagerFetch(aulaInput.getId()).getStudenti().size() < aulaInput.getCapienza()) {

			studenteInput.setAula(aulaInput);
			return studenteInput;
		} else {
			throw new RuntimeException("Impossibie inserire studente, l'aula è piena");
		}
	}
}
