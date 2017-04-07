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
//        ausgeliehenColumn.setCellValueFactory(cellData -> cellData.getValue().ausgeliehenProperty());
//        birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty().asObject());


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

    public ObservableList<Person> sortNachname (ObservableList<Person> persons) {
    	ObservableList<Person> personenListe= FXCollections.observableArrayList();
    	personenListe.setAll(persons);			// Erstelle Kopie von persons
    	Person Platzhalter= new Person();		// Platzhalter von der Klasse Person. Nötig, für das Spätere tauschen innerhalb der Liste

		for (int i= 0; i < personenListe.size() - 1; i++) {			// durchlaufe die gesamte personenListe
			int minIndex= i;										// Annahme, die aktuelle Position enthält den kleinsten Eintrag
			Platzhalter= personenListe.get(i);

			for (int j= i + 1; j < personenListe.size(); j++) {		// durchlaufe den Rest der Liste
				if (Platzhalter.getLastName().compareTo(personenListe.get(j).getLastName()) > 0) {	// vergleiche ob es sich ein kleinerer Eintrag finden lässt
					minIndex= j;																	// falls ja, so wird die Position und Inhalt des neuen Minimum zwischengespeichert
					Platzhalter= personenListe.get(j);
				}																					// am Ende haben wir ein Minimum von der Liste
			}
			personenListe.set(minIndex, personenListe.get(i));										// Tausche aktuelle Position mit der Postion des Minimum
			personenListe.set(i, Platzhalter);
		}
		return personenListe;
	}

    /***************************************************************************
    METHODENNAME:	sortVorname
    *//*!
     Sortiert eine ObservableList aus Personen nach dem Vornamen
     Codekommentierung: siehe sortNachname

     \param   ObservableList<Person>

     \return  ObservableList<Person>

    ***************************************************************************/

    public ObservableList<Person> sortVorname (ObservableList<Person> persons) {
    	ObservableList<Person> personenListe= FXCollections.observableArrayList();
    	personenListe.setAll(persons);
    	Person Platzhalter= new Person();

		for (int i= 0; i < personenListe.size() - 1; i++) {
			int minIndex= i;
			Platzhalter= personenListe.get(i);

			for (int j= i + 1; j < personenListe.size(); j++) {
				if (Platzhalter.getFirstName().compareTo(personenListe.get(j).getFirstName()) > 0) {
					minIndex= j;
					Platzhalter= personenListe.get(j);
				}
			}

			personenListe.set(minIndex, personenListe.get(i));
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

    public ObservableList<Person> sortStadt (ObservableList<Person> persons) {	//
    	ObservableList<Person> personenListe= FXCollections.observableArrayList();	// neue Liste initialisieren
    	personenListe.setAll(persons);												// alte Liste in neue Liste kopieren
    	Person Platzhalter= new Person();											// neue Person als Platzhalter erzeugen

		for (int i= 0; i < personenListe.size() - 1; i++) {
			int minIndex= i;														// Annahme: aktueller Nachname = minimum; Index speichern
			Platzhalter= personenListe.get(i);										// Annahme: aktueller Nachname = minimum; Person speichern

			for (int j= i + 1; j < personenListe.size(); j++) {
				if (Platzhalter.getCity().compareTo(personenListe.get(j).getCity()) > 0) {		// Vergleich: mit Rest der Liste (ab aktuell + 1, nacheinander)
					minIndex= j;							//Person an d Stelle j //Attribut an d Stelle j		(wobei bsp. B => i; A =>j)
					Platzhalter= personenListe.get(j);		// A als neues minimum gespeichert
				}
			}
			personenListe.set(minIndex, personenListe.get(i));
			personenListe.set(i, Platzhalter);
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

     public ObservableList<Person> sortAusleihende (ObservableList<Person> persons) {	//

     	ObservableList<Buchung> buchungListe= FXCollections.observableArrayList();	// neue Liste initialisieren
     	buchungListe.setAll(mainApp.getBuchungData());											// alte Liste in neue Liste kopieren
     	Buchung Platzhalter= new Buchung();											// neue Person als Platzhalter erzeugen

     	mainApp.getBuchungData();

 		for (int i= 0; i < buchungListe.size() - 1; i++) {
 			int minIndex= i;														// Annahme: aktueller Nachname = minimum; Index speichern
 			Platzhalter= buchungListe.get(i);										// Annahme: aktueller Nachname = minimum; Person speichern

 			for (int j= i + 1; j < buchungListe.size(); j++) {
 				if (Platzhalter.getRueckgabedatum().compareTo(buchungListe.get(j).getRueckgabedatum()) > 0) {		// Vergleich: mit Rest der Liste (ab aktuell + 1, nacheinander)
 					minIndex= j;							//Person an d Stelle j //Attribut an d Stelle j		(wobei bsp. B => i; A =>j)
 					Platzhalter= buchungListe.get(j);		// A als neues minimum gespeichert
 				}
 			}
 			buchungListe.set(minIndex, buchungListe.get(i));
 			buchungListe.set(i, Platzhalter);
 		}

 		ObservableList<Person> personenListe= FXCollections.observableArrayList();
 		for (int i= 0; i < buchungListe.size(); i++) {

 			for (int j = 0;  j < persons.size(); j++)  {
 				if (buchungListe.get(i).getPersonID() == persons.get(j).getPersonID()) {

 					personenListe.add(persons.get(j));
 				}
 			}
 		}
 		return personenListe;
 	}
}

/** @}*/ /*end of doxygen group*/