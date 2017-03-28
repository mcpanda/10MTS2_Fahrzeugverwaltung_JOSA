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

	public void SortLastName() {

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
        personTable.setItems(sortNachname(mainApp.getPersonData()));
    }

    /***************************************************************************

	METHODENNAME:	sortNachname

	BESCHREIBUNG:   Sortiert eine ObservableList aus Personen nach dem Nachnamen

	PARAMETER: 		ObservableList<Person>

	RETURN:			ObservableList<Person>

	***************************************************************************/

    public ObservableList<Person> sortNachname (ObservableList<Person> persons) {
    	ObservableList<Person> personenListe= FXCollections.observableArrayList();
    	personenListe.setAll(persons);
    	Person Platzhalter= new Person();

    	for( int i= 0; i< personenListe.size(); i++) {
    		System.out.print(personenListe.get(i).getLastName()+ " ");
    	}
    	System.out.println(" ");
    	System.out.println(personenListe.size());
		for (int i= 0; i < personenListe.size() - 1; i++) {
			System.out.println("index 0: " + personenListe.get(0).getLastName());
			int minIndex= i;
			Platzhalter= personenListe.get(i);
			System.out.println(personenListe.get(i).getLastName());
			for (int j= i + 1; j < personenListe.size(); j++) {
				if (Platzhalter.getLastName().compareTo(personenListe.get(j).getLastName()) > 0) {
					minIndex= j;
					Platzhalter= persons.get(j);
				}
			}
			System.out.println(minIndex + " " + Platzhalter.getLastName());
			System.out.println(i + " " + personenListe.get(i).getLastName());
			personenListe.set(minIndex, personenListe.get(i));
			personenListe.set(i, Platzhalter);
			for( int k= 0; k< personenListe.size(); k++) {
	    		System.out.print(personenListe.get(k).getLastName()+ " ");
	    	}
	    	System.out.println(" ");

		}
		return personenListe;
	}
}
