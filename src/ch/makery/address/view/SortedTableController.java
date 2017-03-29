package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Person;
import java.time.LocalDate;

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

	public void Sort() {

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
//        postalColumn.setCellValueFactory(cellData -> cellData.getValue().postalCodeProperty());
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        lizenzColumn.setCellValueFactory(cellData -> cellData.getValue().lizenzProperty());
//        ausgeliehenColumn.setCellValueFactory(cellData -> cellData.getValue().ausgeliehenProperty());
//        birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());


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

        // Add observable list data to the table
        // personTable.setItems(sortNachname(mainApp.getPersonData()));
    }

	/***************************************************************************

	METHODENNAME:	ShowSortNachname

	BESCHREIBUNG:

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortNachname() {

        personTable.setItems(sortNachname(mainApp.getPersonData()));
    }

	/***************************************************************************

	METHODENNAME:	ShowSortVorname

	BESCHREIBUNG:

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortVorname() {

        personTable.setItems(sortVorname(mainApp.getPersonData()));
    }
    
	/***************************************************************************

	METHODENNAME:	ShowSortStadt

	BESCHREIBUNG:

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortStadt() {

        personTable.setItems(sortStadt(mainApp.getPersonData()));
    }
    
    /***************************************************************************

	METHODENNAME:	sortNachname

	BESCHREIBUNG:   Sortiert eine ObservableList aus Personen nach dem Nachnamen

	PARAMETER: 		ObservableList<Person>

	RETURN:			ObservableList<Person>

	***************************************************************************/

    public ObservableList<Person> sortNachname (ObservableList<Person> persons) {	// 
    	ObservableList<Person> personenListe= FXCollections.observableArrayList();	// neue Liste initialisieren
    	personenListe.setAll(persons);												// alte Liste in neue Liste kopieren
    	Person Platzhalter= new Person();											// neue Person als Platzhalter erzeugen

		for (int i= 0; i < personenListe.size() - 1; i++) {
			int minIndex= i;														// Annahme: aktueller Nachname = minimum; Index speichern
			Platzhalter= personenListe.get(i);										// Annahme: aktueller Nachname = minimum; Person speichern

			for (int j= i + 1; j < personenListe.size(); j++) {
				if (Platzhalter.getLastName().compareTo(personenListe.get(j).getLastName()) > 0) {		// Vergleich: mit Rest der Liste (ab aktuell + 1, nacheinander)
					minIndex= j;						//Person an d Stelle j //Attribut an d Stelle j		(wobei bsp. B => i; A =>j)
					Platzhalter= personenListe.get(j);		// A als neues min�mum gespeichert
				}
			}
			personenListe.set(minIndex, personenListe.get(i));
			personenListe.set(i, Platzhalter);
		}
		return personenListe;
	}

    /***************************************************************************

	METHODENNAME:	sortVorname

	BESCHREIBUNG:   Sortiert eine ObservableList aus Personen nach dem Vornamen

	PARAMETER: 		ObservableList<Person>

	RETURN:			ObservableList<Person>

	***************************************************************************/

    public ObservableList<Person> sortVorname (ObservableList<Person> persons) {
    	ObservableList<Person> personenListe= FXCollections.observableArrayList();
    	personenListe.setAll(persons);
    	Person Platzhalter= new Person();


    	mainApp.getPersonData();

		for (int i= 0; i < personenListe.size() - 1; i++) {
			int minIndex= i;
			Platzhalter= personenListe.get(i);

			for (int j= i + 1; j < personenListe.size(); j++) {
				if (Platzhalter.getFirstName().compareTo(personenListe.get(j).getFirstName()) > 0) {
					minIndex= j;
					Platzhalter= personenListe.get(j);
				}
			}
			System.out.println(minIndex + " " + personenListe.get(i));
			personenListe.set(minIndex, personenListe.get(i));
			personenListe.set(i, Platzhalter);
		}
		return personenListe;
	}
    
    /***************************************************************************

	METHODENNAME:	sortStadt

	BESCHREIBUNG:   Sortiert eine ObservableList aus Personen nach der Stadt

	PARAMETER: 		ObservableList<Person>

	RETURN:			ObservableList<Person>

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
					minIndex= j;						//Person an d Stelle j //Attribut an d Stelle j		(wobei bsp. B => i; A =>j)
					Platzhalter= personenListe.get(j);		// A als neues min�mum gespeichert
				}
			}
			personenListe.set(minIndex, personenListe.get(i));
			personenListe.set(i, Platzhalter);
		}
		return personenListe;
	}
    
    /***************************************************************************

 	METHODENNAME:	sortAusleihende

 	BESCHREIBUNG:   Sortiert eine ObservableList aus Personen nach Ausleihende

 	PARAMETER: 		ObservableList<Buchung>

 	RETURN:			ObservableList<Buchung>

 	***************************************************************************/

     public ObservableList<Buchung> sortAusleihende (ObservableList<Buchung> persons) {	// 
     	ObservableList<Buchung> personenListe= FXCollections.observableArrayList();	// neue Liste initialisieren
     	personenListe.setAll(persons);												// alte Liste in neue Liste kopieren
     	Buchung Platzhalter= new Buchung();											// neue Person als Platzhalter erzeugen

 		for (int i= 0; i < personenListe.size() - 1; i++) {
 			int minIndex= i;														// Annahme: aktueller Nachname = minimum; Index speichern
 			Platzhalter= personenListe.get(i);										// Annahme: aktueller Nachname = minimum; Person speichern

 			for (int j= i + 1; j < personenListe.size(); j++) {
 				if (Platzhalter.getRueckgabedatum().compareTo(personenListe.get(j).getRueckgabedatum()) > 0) {		// Vergleich: mit Rest der Liste (ab aktuell + 1, nacheinander)
 					minIndex= j;						//Person an d Stelle j //Attribut an d Stelle j		(wobei bsp. B => i; A =>j)
 					Platzhalter= personenListe.get(j);		// A als neues min�mum gespeichert
 				}
 			}
 			personenListe.set(minIndex, personenListe.get(i));
 			personenListe.set(i, Platzhalter);
 		}
 		return personenListe;
 	}
}
