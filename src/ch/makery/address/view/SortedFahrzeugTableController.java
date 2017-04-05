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
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.model.Person;
import java.time.LocalDate;

/**************************************************************************/
/*                                                                        */
/* Class SortedFahrzeugTableController                                    */
/*                                                                        */
/**************************************************************************/

public class SortedFahrzeugTableController {

	// Reference to the main application.
    private MainApp mainApp;

	/**************************************************************************/
	/*                                                                        */
	/* FXML Column/Labeln Section                                             */
	/*                                                                        */
	/**************************************************************************/

    @FXML
    private TableView<Fahrzeug> fahrzeugTable;
    @FXML
    private TableColumn<Fahrzeug, Integer> fahrzeugIDColumn;
    @FXML
    private TableColumn<Fahrzeug, String> herstellerColumn;
    @FXML
    private TableColumn<Fahrzeug, String> markeColumn;
    @FXML
    private TableColumn<Fahrzeug, Integer> leistungColumn;
    @FXML
    private TableColumn<Fahrzeug, Integer> kilometerstandColumn;


//    @FXML
//    private TableColumn<Fahrzeug, String> rueckgabedatumColumn;

//    @FXML
//    private TableColumn<Fahrzeug, Integer> postalColumn;
//    @FXML
//    private TableColumn<Fahrzeug, String> cityColumn;
//    @FXML
//    private TableColumn<Fahrzeug, LocalDate> ausgeliehenColumn;

	/**************************************************************************/
	/*                                                                        */
	/* Constructor                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktur. Muss vor dem Initialisieren aufgerufen werden.   */

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
        // Initialize the Fahrzeug table with the two columns.
    	fahrzeugIDColumn.setCellValueFactory(cellData -> cellData.getValue().fahrzeugIDProperty().asObject());
        herstellerColumn.setCellValueFactory(cellData -> cellData.getValue().herstellerProperty());
        markeColumn.setCellValueFactory(cellData -> cellData.getValue().markeProperty());
        leistungColumn.setCellValueFactory(cellData -> cellData.getValue().leistungProperty().asObject());
        kilometerstandColumn.setCellValueFactory(cellData -> cellData.getValue().kilometerstandProperty().asObject());
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

	METHODENNAME:	ShowSortFahrzeugHersteller

	BESCHREIBUNG:

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortFahrzeugHersteller() {

        fahrzeugTable.setItems(sortFahrzeugHersteller(mainApp.getFahrzeugData()));
    }


	/***************************************************************************

	METHODENNAME:	ShowSortFahrzeugMarke

	BESCHREIBUNG:

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortFahrzeugMarke() {

        fahrzeugTable.setItems(sortFahrzeugMarke(mainApp.getFahrzeugData()));
    }


	/***************************************************************************

	METHODENNAME:	ShowSortFahrzeugKilometerstand

	BESCHREIBUNG:

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortFahrzeugKilometerstand() {

        fahrzeugTable.setItems(sortFahrzeugKilometerstand(mainApp.getFahrzeugData()));
    }


	/***************************************************************************

	METHODENNAME:	ShowSortFahrzeugLeistung

	BESCHREIBUNG:

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortFahrzeugLeistung() {

        fahrzeugTable.setItems(sortFahrzeugLeistung(mainApp.getFahrzeugData()));
    }

    /***************************************************************************

 	METHODENNAME:	sortFahrzeugHersteller

 	BESCHREIBUNG:   Sortiert eine ObservableList aus Fahrzeug nach Hersteller

 	PARAMETER: 		ObservableList<Fahrzeug>

 	RETURN:			ObservableList<Fahrzeug>

 	***************************************************************************/


     public ObservableList<Fahrzeug> sortFahrzeugHersteller (ObservableList<Fahrzeug> fahrzeugs) {	//


     	ObservableList<Fahrzeug> fahrzeugListe= FXCollections.observableArrayList();	// neue Liste initialisieren
     	fahrzeugListe.setAll(mainApp.getFahrzeugData());								// alte Liste in neue Liste kopieren
     	Fahrzeug Platzhalter= new Fahrzeug();											// neues Fahrzeug als Platzhalter erzeugen

     	mainApp.getFahrzeugData();


 		for (int i= 0; i < fahrzeugListe.size() -1; i++) {
 			int minIndex= i;														// Annahme: aktueller Hersteller = minimum; Index speichern
 			Platzhalter= fahrzeugListe.get(i);										// Annahme: aktueller Hersteller = minimum; Fahrzeug speichern

 			for (int j= i + 1; j < fahrzeugListe.size(); j++) {

 				if (Platzhalter.getHersteller().compareTo(fahrzeugListe.get(j).getHersteller()) > 0) {		// Vergleich: mit Rest der Liste (ab aktuell +1)
 					minIndex= j;							//Fahrzeug an der Stelle j
 					Platzhalter= fahrzeugListe.get(j);	}	//neues minimum gespeichert
 			}
 			fahrzeugListe.set(minIndex, fahrzeugListe.get(i));
 			fahrzeugListe.set(i, Platzhalter);
 		}
 		return fahrzeugListe;
 	}

     /***************************************************************************

  	METHODENNAME:	sortFahrzeugMarke

  	BESCHREIBUNG:   Sortiert eine ObservableList aus Fahrzeug nach Marke

  	PARAMETER: 		ObservableList<Fahrzeug>

  	RETURN:			ObservableList<Fahrzeug>

  	***************************************************************************/


      public ObservableList<Fahrzeug> sortFahrzeugMarke (ObservableList<Fahrzeug> fahrzeugs) {	//


      	ObservableList<Fahrzeug> fahrzeugListe= FXCollections.observableArrayList();	// neue Liste initialisieren
      	fahrzeugListe.setAll(mainApp.getFahrzeugData());											// alte Liste in neue Liste kopieren
      	Fahrzeug Platzhalter= new Fahrzeug();											// neues Fahrzeug als Platzhalter erzeugen

      	mainApp.getFahrzeugData();


  		for (int i= 0; i < fahrzeugListe.size() - 1; i++) {
  			int minIndex= i;														// Annahme: aktuelle Marke = minimum; Index speichern
  			Platzhalter= fahrzeugListe.get(i);										// Annahme: aktuelle Marke = minimum; Fahrzeug speichern

  			for (int j= i + 1; j < fahrzeugListe.size(); j++) {

  				if (Platzhalter.getMarke().compareTo(fahrzeugListe.get(j).getMarke()) > 0) {		// Vergleich: mit Rest der Liste (ab aktuell + 1, nacheinander)
  					minIndex= j;							//Fahrzeug an d Stelle j //Attribut an d Stelle j		(wobei bsp. B => i; A =>j)
  					Platzhalter= fahrzeugListe.get(j);		// A als neues minimum gespeichert
  				}
  			}
  			fahrzeugListe.set(minIndex, fahrzeugListe.get(i));
  			fahrzeugListe.set(i, Platzhalter);
  		}
  		return fahrzeugListe;
  	}

      /***************************************************************************

   	METHODENNAME:	sortFahrzeugKilometerstand

   	BESCHREIBUNG:   Sortiert eine ObservableList aus Fahrzeug nach Kilometerstand

   	PARAMETER: 		ObservableList<Fahrzeug>

   	RETURN:			ObservableList<Fahrzeug>

   	***************************************************************************/


       public ObservableList<Fahrzeug> sortFahrzeugKilometerstand (ObservableList<Fahrzeug> fahrzeugs) {	//


       	ObservableList<Fahrzeug> fahrzeugListe= FXCollections.observableArrayList();	// neue Liste initialisieren
       	fahrzeugListe.setAll(mainApp.getFahrzeugData());											// alte Liste in neue Liste kopieren
       	Fahrzeug Platzhalter= new Fahrzeug();											// neues Fahrzeug als Platzhalter erzeugen

       	mainApp.getFahrzeugData();


   		for (int i= 0; i < fahrzeugListe.size() - 1; i++) {
   			int minIndex= i;														// Annahme: aktueller Nachname = minimum; Index speichern
   			Platzhalter= fahrzeugListe.get(i);										// Annahme: aktueller Nachname = minimum; Fahrzeug speichern

   			for (int j= i + 1; j < fahrzeugListe.size(); j++) {

   				if (Platzhalter.getKilometerstand() > (fahrzeugListe.get(j).getKilometerstand())) {		// Vergleich: mit Rest der Liste (ab aktuell + 1)
   					minIndex= j;							//Fahrzeug an der Stelle j
   					Platzhalter= fahrzeugListe.get(j);		//neues minimum gespeichert
   				}
   			}
   			fahrzeugListe.set(minIndex, fahrzeugListe.get(i));
   			fahrzeugListe.set(i, Platzhalter);
   		}
   		return fahrzeugListe;
   	}






    /***************************************************************************

 	METHODENNAME:	sortFahrzeugLeistung

 	BESCHREIBUNG:   Sortiert eine ObservableList aus Fahrzeug nach Leistung

 	PARAMETER: 		ObservableList<Fahrzeug>

 	RETURN:			ObservableList<Fahrzeug>

 	***************************************************************************/


     public ObservableList<Fahrzeug> sortFahrzeugLeistung (ObservableList<Fahrzeug> fahrzeugs) {	//


     	ObservableList<Fahrzeug> fahrzeugListe= FXCollections.observableArrayList();
     	fahrzeugListe.setAll(mainApp.getFahrzeugData());
     	Fahrzeug Platzhalter= new Fahrzeug();

     	mainApp.getFahrzeugData();


 		for (int i= 0; i < fahrzeugListe.size() - 1; i++) {
 			int minIndex= i;
 			Platzhalter= fahrzeugListe.get(i);

 			for (int j= i + 1; j < fahrzeugListe.size(); j++) {

 				if (Platzhalter.getLeistung() > (fahrzeugListe.get(j).getLeistung())) {
 					minIndex= j;
 					Platzhalter= fahrzeugListe.get(j);
 				}
 			}
 			fahrzeugListe.set(minIndex, fahrzeugListe.get(i));
 			fahrzeugListe.set(i, Platzhalter);
 		}
 		return fahrzeugListe;
 	}

	/***************************************************************************

	METHODENNAME:	ShowSortFahrzeugAusleihende

	BESCHREIBUNG:

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void ShowSortFahrzeugAusleihende() {

        fahrzeugTable.setItems(sortFahrzeugAusleihende(mainApp.getFahrzeugData()));
    }



    /***************************************************************************

 	METHODENNAME:	sortFahrzeugAusleihende

 	BESCHREIBUNG:   Sortiert eine ObservableList aus Fahrzeug nach Ausleihende

 	PARAMETER: 		ObservableList<Buchung>

 	RETURN:			ObservableList<Buchung>

 	***************************************************************************/

     public ObservableList<Fahrzeug> sortFahrzeugAusleihende (ObservableList<Fahrzeug> fahrzeugs) {	//

     	ObservableList<Buchung> buchungListe= FXCollections.observableArrayList();
     	buchungListe.setAll(mainApp.getBuchungData());
     	Buchung Platzhalter= new Buchung();

     	mainApp.getBuchungData();

 		for (int i= 0; i < buchungListe.size() - 1; i++) {
 			int minIndex= i;
 			Platzhalter= buchungListe.get(i);

 			for (int j= i + 1; j < buchungListe.size(); j++) {
 				if (Platzhalter.getRueckgabedatum().compareTo(buchungListe.get(j).getRueckgabedatum()) > 0) {
 					minIndex= j;
 					Platzhalter= buchungListe.get(j);
 				}
 			}
 			buchungListe.set(minIndex, buchungListe.get(i));
 			buchungListe.set(i, Platzhalter);
 		}

 		ObservableList<Fahrzeug> fahrzeugeListe= FXCollections.observableArrayList();
 		for (int i= 0; i < buchungListe.size(); i++) {

 			for (int j = 0;  j < fahrzeugs.size(); j++)  {
 				if (buchungListe.get(i).getPersonID() == fahrzeugs.get(j).getFahrzeugID()) {

 					fahrzeugeListe.add(fahrzeugs.get(j));
 				}
 			}
 		}
 		return fahrzeugeListe;
 	}
}