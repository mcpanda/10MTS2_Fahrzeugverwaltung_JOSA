/**************************************************************************************************/
/*! \file
  FILE         : $Source: SortedTableController.java $
  BESCHREIBUNG : Controller
                 Controller für die Sortierung von Personen
***************************************************************************************************/

/** \addtogroup View
 *  @{
 */

package ch.makery.address.view;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.model.Buchung;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/***************************************************************************
CLASS:	SortedTableController
*//*!
 Die Klasse BuchungEditDialogController hat nur einen Standardkonstruktor.

***************************************************************************/

public class SortedTableController {

	// Reference to the main application.
    private MainApp mainApp;

	/**************************************************************************/
	/*                                                                        */
	/* FXML Column/Labeln Section                                             */
	/*                                                                        */
	/**************************************************************************/

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, Integer> personIDColumn;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> streetColumn;
    @FXML
    private TableColumn<Person, Integer> postalColumn;
    @FXML
    private TableColumn<Person, String> cityColumn;
    @FXML
    private TableColumn<Person, String> lizenzColumn;
    @FXML
    private TableColumn<Person, String> birthdayColumn;
    @FXML
    private TableColumn<Person, LocalDate> ausgeliehenColumn;

	/**************************************************************************/
	/*                                                                        */
	/* Constructor                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktor. Muss vor dem Initialisieren aufgerufen werden.   */

	public SortedTableController() {

	}

	/**************************************************************************/
	/*                                                                        */
	/* Local Operation Section                                                */
	/*                                                                        */
	/**************************************************************************/

    /***************************************************************************
    METHODENNAME:	initialize
    *//*!
     Initialisiert die Controller Klasse. Diese Methode wird automatisch
     aufgerufen, nachdem die fxml Datei geladen wurde.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
    	personIDColumn.setCellValueFactory(cellData -> cellData.getValue().personIDProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        streetColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());
        postalColumn.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty().asObject());
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        lizenzColumn.setCellValueFactory(cellData -> cellData.getValue().lizenzProperty());
    }

    /***************************************************************************
    METHODENNAME:	setMainApp
    *//*!
     Is called by the main application to give a reference back to itself.

     \param   mainApp

     \return  void

    ***************************************************************************/

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /***************************************************************************
    METHODENNAME:	ShowSortNachname
    *//*!
     Anzeige der Personen, sortiert nach Nachnamen.

     \param   void

     \return  void

    ***************************************************************************/

    public void ShowSortNachname() {

        personTable.setItems(sortNachname(mainApp.getPersonData()));
    }

    /***************************************************************************
    METHODENNAME:	ShowSortVorname
    *//*!
     Anzeige der Personen, sortiert nach Vornamen

     \param   void

     \return  void

    ***************************************************************************/

    public void ShowSortVorname() {

        personTable.setItems(sortVorname(mainApp.getPersonData()));
    }

    /***************************************************************************
    METHODENNAME:	ShowSortStadt
    *//*!
     Anzeige der Personen, sortiert nach Stadt.

     \param   void

     \return  void

    ***************************************************************************/

    public void ShowSortStadt() {

        personTable.setItems(sortStadt(mainApp.getPersonData()));
    }

    /***************************************************************************
    METHODENNAME:	ShowSortAusleihende
    *//*!
     Anzeige der Personen, sortiert nach Rückgabedatum.

     \param   void

     \return  void

    ***************************************************************************/

    public void ShowSortAusleihende() {

        personTable.setItems(sortAusleihende(mainApp.getPersonData()));
    }

    /***************************************************************************
    METHODENNAME:	sortNachname
    *//*!
     Sortiert eine ObservableList aus Personen nach dem Nachnamen.

     \param   ObservableList<Person>

     \return  ObservableList<Person>

    ***************************************************************************/


    public ObservableList<Person> sortNachname (ObservableList<Person> persons) {		// Methodenkopf mit Rueckgabe- und Parameterwert
    	ObservableList<Person> personenListe= FXCollections.observableArrayList();		// neue Liste initialisieren
    	personenListe.setAll(persons);													// alte Liste in neue Liste kopieren
    	Person Platzhalter= new Person();												// Platzhalter von der Klasse Person
    																					// notwendig, für das spaetere tauschen innerhalb der Liste
        for (int i= 0; i < personenListe.size()- 1; i++)								// gehe die Liste durch
        {
        	int minIndex = i;															// Annahme: aktuelle Person = Minimum; Index speichern
        	Platzhalter= personenListe.get(i);											// Annahme: aktuelle Person = Minimum; Person speichern

        	for (int j = i + 1; j < personenListe.size(); j++) 							// gehe den Rest der Liste durch
        	{
        		boolean b_ungleich = false;															// Var um Eintraege auf Ungleichheit zu pruefen
        		char[] arr1 = Platzhalter.getLastName().toLowerCase().toCharArray();				// Umwandlung des Platzhalter in ein char Array
        		char[] arr2 = personenListe.get(j).getLastName().toLowerCase().toCharArray();		// Umwandlung des Nachnamens an der Stelle j in char Array

       			int min_Laenge = arr1.length;														// Festlegung der minimalen Laenge der char Arrays

       			if (arr2.length < arr1.length) {
       				min_Laenge = arr2.length;
       			}

    			for (int t = 0; t < min_Laenge; t++)												// vergleiche die char Arrays innerhalb der jeweiligen min. Laenge
    			{
    				if (arr2[t] < arr1[t]) {											// falls der Eintrag an der Stelle j < Eintrag an der Stelle i
    					b_ungleich  = true;
    					minIndex = j;													// Person an der Stelle j (= neues Minimum); Index speichern
    					Platzhalter = personenListe.get(j);								// Person als neues Minimum speichern
    					break;

    				} else {
    					if (arr2[t] > arr1[t])
        				{
    						b_ungleich  = true;
    					break;
    					}
    				}
    			}

    			if (b_ungleich == false) {
    				if (arr2.length < arr1.length) {
    					minIndex = j;													// Person an der Stelle j
    					Platzhalter = personenListe.get(j);								// wird als neues Minimum gespeichert
    				}
    			}
        	}

			personenListe.set(minIndex, personenListe.get(i));							// an der Position mit dem neuen Minimum wird das i-te Element eingetragen
			personenListe.set(i, Platzhalter);											// an der Position i wird das gefundene Minimum eingetragen
        }
		return personenListe;
    }

//    /***************************************************************************
//    METHODENNAME:	sortNachname
//    *//*!
//     Sortiert eine ObservableList aus Personen nach dem Nachnamen.
//
//     \param   ObservableList<Person>
//
//     \return  ObservableList<Person>
//
//    ***************************************************************************/
//
//    public ObservableList<Person> sortNachname (ObservableList<Person> persons) {
//    	ObservableList<Person> personenListe= FXCollections.observableArrayList();
//    	personenListe.setAll(persons);			// Erstelle Kopie von persons
//    	Person Platzhalter= new Person();		// Platzhalter von der Klasse Person. Nötig, für das Spätere tauschen innerhalb der Liste
//
//		for (int i= 0; i < personenListe.size() - 1; i++) {			// durchlaufe die gesamte personenListe
//			int minIndex= i;										// Annahme, die aktuelle Position enthält den kleinsten Eintrag
//			Platzhalter= personenListe.get(i);
//
//			for (int j= i + 1; j < personenListe.size(); j++) {		// durchlaufe den Rest der Liste
//				if (Platzhalter.getLastName().compareTo(personenListe.get(j).getLastName()) > 0) {	// vergleiche ob es sich ein kleinerer Eintrag finden lässt
//					minIndex= j;																	// falls ja, so wird die Position und Inhalt des neuen Minimum zwischengespeichert
//					Platzhalter= personenListe.get(j);
//				}
//
//				if(Platzhalter.getLastName().compareTo(personenListe.get(j).getLastName()) == 0) { 			// falls der Nachnahme identisch ist,
//					if (Platzhalter.getFirstName().compareTo(personenListe.get(j).getFirstName()) > 0) {	// so wird der Vorname verglichen
//						minIndex= j;
//						Platzhalter= personenListe.get(j);
//					}
//
//					if (Platzhalter.getFirstName().compareTo(personenListe.get(j).getFirstName()) == 0) {		// falls auch der Vorname identisch ist,
//						if (Platzhalter.getPersonID() > personenListe.get(j).getPersonID()) {					// so wird die ID verglichen
//							minIndex= j;
//							Platzhalter= personenListe.get(j);
//						}
//					}
//				}													// am Ende haben wir ein Minimum von der Liste
//			}
//			personenListe.set(minIndex, personenListe.get(i));		// Tausche aktuelle Position mit der Postion des Minimum
//			personenListe.set(i, Platzhalter);
//		}
//		return personenListe;
//	}

    /***************************************************************************
    METHODENNAME:	sortVorname
    *//*!
     Sortiert eine ObservableList aus Personen nach dem Vornamen

     \param   ObservableList<Person>

     \return  ObservableList<Person>

    ***************************************************************************/

    public ObservableList<Person> sortVorname (ObservableList<Person> persons) {
    	ObservableList<Person> personenListe= FXCollections.observableArrayList();
    	personenListe.setAll(persons);			// Erstelle Kopie von persons
    	Person Platzhalter= new Person();		// Platzhalter von der Klasse Person. Nötig, für das Spätere tauschen innerhalb der Liste

		for (int i= 0; i < personenListe.size() - 1; i++) {			// durchlaufe die gesamte personenListe
			int minIndex= i;										// Annahme, die aktuelle Position enthält den kleinsten Eintrag
			Platzhalter= personenListe.get(i);

			for (int j= i + 1; j < personenListe.size(); j++) {		// durchlaufe den Rest der Liste
				if (Platzhalter.getFirstName().compareTo(personenListe.get(j).getFirstName()) > 0) {		// vergleiche ob es sich ein kleinerer Eintrag finden lässt
					minIndex= j;																			// falls ja, so wird die Position und Inhalt des neuen Minimum zwischengespeichert
					Platzhalter= personenListe.get(j);
				}

				if (Platzhalter.getFirstName().compareTo(personenListe.get(j).getFirstName()) == 0) {		// falls der Vornahme identisch ist,
					if (Platzhalter.getLastName().compareTo(personenListe.get(j).getLastName()) > 0) {		// so wird der Nachname verglichen
						minIndex= j;
						Platzhalter= personenListe.get(j);
					}
					if (Platzhalter.getLastName().compareTo(personenListe.get(j).getLastName()) == 0) {		// falls der Nachnahme identisch ist,
						if (Platzhalter.getPersonID() > personenListe.get(j).getPersonID()) {				// so wird die ID verglichen
							minIndex= j;
							Platzhalter= personenListe.get(j);
						}
					}
				}					// am Ende haben wir ein Minimum von der Liste
			}

			personenListe.set(minIndex, personenListe.get(i));		// Tausche aktuelle Position mit der Postion des Minimum
			personenListe.set(i, Platzhalter);
		}
		return personenListe;
	}

    /***************************************************************************
    METHODENNAME:	sortStadt
    *//*!
     Sortiert eine ObservableList aus Personen nach der Stadt

     \param   ObservableList<Person>

     \return  ObservableList<Person>

    ***************************************************************************/

    public ObservableList<Person> sortStadt (ObservableList<Person> persons) {					//
    	ObservableList<Person> personenListe= FXCollections.observableArrayList();				// neue Liste initialisieren
    	personenListe.setAll(persons);															// alte Liste in neue Liste kopieren // Erstelle Kopie von persons
    	Person Platzhalter= new Person();														// neue Person als Platzhalter erzeugen
    																							// notwendig, für das spaetere tauschen innerhalb der Liste

		for (int i= 0; i < personenListe.size() - 1; i++) {
			int minIndex= i;																	// Annahme: aktuelle Person = Minimum; Index speichern
			Platzhalter= personenListe.get(i);													// Annahme: aktuelle Person = Minimum; Person speichern

			for (int j= i + 1; j < personenListe.size(); j++) {
				if (Platzhalter.getCity().compareTo(personenListe.get(j).getCity()) > 0) {		// Vergleich: mit Rest der Liste (ab aktuell + 1, nacheinander)
					minIndex= j;																// Person an der Stelle j
					Platzhalter= personenListe.get(j);											// wird als neues Minimum gespeichert
				}
			}
			if (minIndex > i) {
			personenListe.set(minIndex, personenListe.get(i));									// an der Position mit dem neuen Minimum wird das i-te Element eingetragen
			personenListe.set(i, Platzhalter);													// an der Position i wird das gefundene Minimum eingetragen
			}
		}
		return personenListe;
	}

    /***************************************************************************
    METHODENNAME:	sortAusleihende
    *//*!
     Sortiert eine ObservableList aus Personen nach Ausleihende

     \param   ObservableList<Person>

     \return  ObservableList<Person>

    ***************************************************************************/

     public ObservableList<Person> sortAusleihende (ObservableList<Person> persons) {

     	ObservableList<Buchung> buchungListe= FXCollections.observableArrayList();				// neue Liste initialisieren
     	buchungListe.setAll(mainApp.getBuchungData());											// alte Liste in neue Liste kopieren
     	Buchung Platzhalter= new Buchung();														// neue Person als Platzhalter erzeugen
     																							// notwendig, für das spaetere tauschen innerhalb der Liste
 		for (int i= 0; i < buchungListe.size() - 1; i++) {
 			int minIndex= i;																	// Annahme: aktuelle Buchung = Minimum; Index speichern
 			Platzhalter= buchungListe.get(i);													// Annahme: aktuelle Buchung = Minimum; Buchung speichern

 			for (int j= i + 1; j < buchungListe.size(); j++) {
 				if (Platzhalter.getRueckgabedatum().compareTo(buchungListe.get(j).getRueckgabedatum()) > 0) {		// Vergleich: mit Rest der Liste (ab aktuell + 1, nacheinander)
 					minIndex= j;																					// Person an der Stelle j
 					Platzhalter= buchungListe.get(j);																// neues Minimum gespeichert
 				}
 			}
 			if (minIndex > i) {
 				buchungListe.set(minIndex, buchungListe.get(i));								// an der Position mit dem neuen Minimum wird das i-te Element eingetragen
 				buchungListe.set(i, Platzhalter);												// an der Position i wird das gefundene Minimum eingetragen
 			}
 		}
 		ObservableList<Person> personenListe= FXCollections.observableArrayList();
 		for (int i= 0; i < buchungListe.size(); i++) {											// gehe die nach Datum sortierte buchungListe durch

 			for (int j = 0;  j < persons.size(); j++)  											// gehe die Personenliste durch,
 			{
 				if (buchungListe.get(i).getPersonID() == persons.get(j).getPersonID()) 			// um die entsprechende Person anhand der ID zu finden
 				{
 					personenListe.add(persons.get(j));											// fuege die Person zur sortierten Personenliste hinzu
 				}
 			}
 		}
 		return personenListe;
 	}
}

/** @}*/ /*end of doxygen group*/