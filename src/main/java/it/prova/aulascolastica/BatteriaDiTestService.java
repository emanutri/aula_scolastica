package it.prova.aulascolastica;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.aulascolastica.model.Aula;
import it.prova.aulascolastica.model.Studente;
import it.prova.aulascolastica.service.AulaService;
import it.prova.aulascolastica.service.StudenteService;

@Service
public class BatteriaDiTestService {

	@Autowired
	private AulaService aulaService;

	@Autowired
	private StudenteService studenteService;

	// casi di test (usare valorizzando la variabile casoDaTestare nel main)
	public static final String INSERISCI_NUOVA_AULA = "INSERISCI_NUOVA_AULA";
	public static final String INSERISCI_STUDENTI_DATA_AULA = "INSERISCI_STUDENTI_DATA_AULA";
	public static final String CERCA_STUDENTI_DATO_ID_AULA = "CERCA_STUDENTI_DATO_ID_AULA";
	public static final String RIMUOVI_AULA = "RIMUOVI_AULA";
	public static final String RIMUOVI_STUDENTE = "RIMUOVI_STUDENTE";
	public static final String ELENCA_TUTTE_LE_AULE = "ELENCA_TUTTE_LE_AULE";
	public static final String FIND_BY_EXAMPLE_BY_CODICE = "FIND_BY_EXAMPLE_BY_CODICE";
	public static final String FIND_BY_EXAMPLE_BY_NOME = "FIND_BY_EXAMPLE_BY_NOME";
	public static final String UPDATE_STUDENTE = "UPDATE_STUDENTE";
	public static final String UPDATE_AULA = "UPDATE_AULA";

	public void eseguiBatteriaDiTest(String casoDaTestare) {
		try {
			switch (casoDaTestare) {

			case INSERISCI_NUOVA_AULA:
				// creo nuova aula
				Aula nuovaAula = new Aula("A_41", "matematica", 20);
				// salvo
				aulaService.inserisciNuovo(nuovaAula);
				System.out.println("Aula appena inserita: " + nuovaAula);
				break;

			case INSERISCI_STUDENTI_DATA_AULA:
				// creo nuovo studente
				String inputData = "1992/02/10";
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				Date data = format.parse(inputData);
				Studente nuovoStudente = new Studente("simona", "densi", data);

				// controllo che l'aula abbia posto per lo studente
				studenteService.inserisciStudenteInAula(nuovoStudente, aulaService.caricaSingolaAula(2L));

				// salvo
				studenteService.inserisciNuovo(nuovoStudente);
				break;

			case CERCA_STUDENTI_DATO_ID_AULA:
				// stampo gli studenti di una determinata aula
				System.out.println(studenteService.cercaStudentiInAula(aulaService.caricaSingolaAula(1L)));
				break;

			case RIMUOVI_AULA:
				// per cancellare l'aula, non permesso se c'è studente
				aulaService.rimuovi(aulaService.caricaSingolaAula(3L));
				break;

			case RIMUOVI_STUDENTE:
				// per cancellare uno studente
				studenteService.rimuovi(studenteService.caricaSingoloStudente(2L));
				break;

			case ELENCA_TUTTE_LE_AULE:
				// elencare le aule
				System.out.println("Elenco le aule:");
				for (Aula aulaItem : aulaService.listAllAule()) {
					System.out.println(aulaItem);
				}
				break;

			case FIND_BY_EXAMPLE_BY_CODICE:
				System.out.println("########### EXAMPLE ########################");
				// find by example: voglio ricercare le aule con
				// codice A_41
				Aula aulaExample = new Aula();
				aulaExample.setCodice("A_41");
				for (Aula aulaItem : aulaService.findByExample(aulaExample)) {
					System.out.println(aulaItem);
				}
				break;

			case FIND_BY_EXAMPLE_BY_NOME:
				System.out.println("########### EXAMPLE ########################");
				// find by example: voglio ricercare gli studenti con
				// nome rocco
				Studente studenteExample = new Studente();
				studenteExample.setNome("rocco");
				for (Studente studenteItem : studenteService.findByExample(studenteExample)) {
					System.out.println(studenteItem);
				}
				break;

			case UPDATE_STUDENTE:
				// cambio il cognome dello studente x
				Studente studenteInstance = studenteService.caricaSingoloStudente(4L);
				if (studenteInstance != null) {
					studenteInstance.setCognome("losco");
					studenteService.aggiorna(studenteInstance);
				}
				break;

			case UPDATE_AULA:
				// cambio il codice dell'aula y
				Aula aulaInstance = aulaService.caricaSingolaAula(1L);
				aulaInstance.setCodice("A_01");
				aulaService.aggiorna(aulaInstance);
				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
