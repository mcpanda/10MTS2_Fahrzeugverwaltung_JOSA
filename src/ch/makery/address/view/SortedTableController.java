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
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**************************************************************************/
/*                                                                        */
/* Class SortLastNameController                                         */
/*                                                                        */
/**************************************************************************/

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
	/* Constructur                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktur. Muss vor dem Initializieren aufgerufen werden.   */

	public void SortedTable() {

	}

	/**************************************************************************/
	/*                                                                        */
	/* Local Operation Section                                                */
	/*                                                                        */
	/**************************************************************************/

	/***************************************************************************

	METHODENNAME:	initialize

	BESCHREIBUNG:   Initialisiert die Controller Klasse. Diese Methode wird
					automatisch aufgerufen, nachdem die fxml Datei
					geladen wurde

	PARAMETER: 		void

	RETURN:			void

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

	BESCHREIBUNG:   Is called by the main application to give a reference back
					to itself.

	PARAMETER: 		mainApp

	RETURN:			void

	***************************************************************************/

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	/***************************************************************************

	METHODENNAME:	ShowSortNachname

	BESCHREIBUNG:	Anzeige der Personen, sortiert nach Nachnamen

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortNachname() {

        personTable.setItems(sortNachname(mainApp.getPersonData()));
    }

	/***************************************************************************

	METHODENNAME:	ShowSortVorname

	BESCHREIBUNG:	Anzeige der Personen, sortiert nach Vornamen

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortVorname() {

        personTable.setItems(sortVorname(mainApp.getPersonData()));
    }

    /***************************************************************************

	METHODENNAME:	sortNachname

	BESCHREIBUNG:   Sortiert eine ObservableList aus Personen nach dem Nachnamen.

	PARAMETER: 		ObservableList<Person>

	RETURN:			ObservableList<Person>

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

	BESCHREIBUNG:   Sortiert eine ObservableList aus Personen nach dem Vornamen
					Codekommentierung: siehe sortNachname

	PARAMETER: 		ObservableList<Person>

	RETURN:			ObservableList<Person>

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

}
