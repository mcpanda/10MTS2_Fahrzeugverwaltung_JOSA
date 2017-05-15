/**************************************************************************************************/
/*! \file
  FILE         : $Source: SortedFahrzeugTableController.java $
  BESCHREIBUNG : Controller
                 Controller für die Fahrzeugsortierung
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
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Fahrzeug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/***************************************************************************
CLASS:	SortedFahrzeugTableController
*//*!
 Die Klasse BuchungEditDialogController hat nur einen Standardkonstruktor.

***************************************************************************/

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

	/**************************************************************************/
	/*                                                                        */
	/* Constructor                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktor. Muss vor dem Initialisieren aufgerufen werden.   */

	public void Sort() {

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
        // Initialize the Fahrzeug table with the two columns.
    	fahrzeugIDColumn.setCellValueFactory(cellData -> cellData.getValue().fahrzeugIDProperty().asObject());
        herstellerColumn.setCellValueFactory(cellData -> cellData.getValue().herstellerProperty());
        markeColumn.setCellValueFactory(cellData -> cellData.getValue().markeProperty());
        leistungColumn.setCellValueFactory(cellData -> cellData.getValue().leistungProperty().asObject());
        kilometerstandColumn.setCellValueFactory(cellData -> cellData.getValue().kilometerstandProperty().asObject());
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
    METHODENNAME:	ShowSortFahrzeugHersteller
    *//*!
     Anzeige der Fahrzeuge, sortiert nach Hersteller.

     \param   void

     \return  void

    ***************************************************************************/

    public void ShowSortFahrzeugHersteller() {

        fahrzeugTable.setItems(sortFahrzeugHersteller(mainApp.getFahrzeugData()));
    }

    /***************************************************************************
    METHODENNAME:	ShowSortFahrzeugMarke
    *//*!
     Anzeige der Fahrzeuge, sortiert nach Marke.

     \param   void

     \return  void

    ***************************************************************************/

    public void ShowSortFahrzeugMarke() {

        fahrzeugTable.setItems(sortFahrzeugMarke(mainApp.getFahrzeugData()));
    }

    /***************************************************************************
    METHODENNAME:	ShowSortFahrzeugKilometerstand
    *//*!
     Anzeige der Fahrzeuge, sortiert nach Kilometerstand.

     \param   void

     \return  void

    ***************************************************************************/

    public void ShowSortFahrzeugKilometerstand() {

        fahrzeugTable.setItems(sortFahrzeugKilometerstand(mainApp.getFahrzeugData()));
    }

    /***************************************************************************
    METHODENNAME:	ShowSortFahrzeugLeistung
    *//*!
     Anzeige der Fahrzeuge, sortiert nach Leistung.

     \param   void

     \return  void

    ***************************************************************************/

    public void ShowSortFahrzeugLeistung() {

        fahrzeugTable.setItems(sortFahrzeugLeistung(mainApp.getFahrzeugData()));
    }

    /***************************************************************************
    METHODENNAME:	ShowSortFahrzeugAusleihende
    *//*!
     Anzeige der Fahrzeuge, sortiert nach Rückgabedatum

     \param   void

     \return  void

    ***************************************************************************/

    public void ShowSortFahrzeugAusleihende() {

       fahrzeugTable.setItems(sortFahrzeugAusleihende(mainApp.getFahrzeugData()));
    }

    /***************************************************************************
    METHODENNAME:	sortFahrzeugHersteller
    *//*!
     Sortiert eine ObservableList aus Fahrzeug nach Hersteller

     \param   ObservableList<Fahrzeug>

     \return  ObservableList<Fahrzeug>

    ***************************************************************************/

	public ObservableList<Fahrzeug> sortFahrzeugHersteller(ObservableList<Fahrzeug> fahrzeugs) { //

		ObservableList<Fahrzeug> fahrzeugListe = FXCollections.observableArrayList();			// neue Liste initialisieren
		fahrzeugListe.setAll(mainApp.getFahrzeugData()); 										// alte Liste in neue Liste kopieren
		Fahrzeug Platzhalter = new Fahrzeug();													// neues Fahrzeug als Platzhalter erzeugen

		for (int i = 0; i < fahrzeugListe.size() - 1; i++) {
			int minIndex = i; 																	// Annahme: aktuelles Fahrzeug = minimum; Index speichern
			Platzhalter = fahrzeugListe.get(i);													// Annahme: aktuelles Fahrzeug = minimum; Fahrzeug speichern

			for (int j = i + 1; j < fahrzeugListe.size(); j++) {

				if (Platzhalter.getHersteller().compareTo(fahrzeugListe.get(j).getHersteller()) > 0) { 		// Vergleich: mit Rest der Liste (ab aktuell +1)

					minIndex = j; 																			// Fahrzeug an der Stelle j
					Platzhalter = fahrzeugListe.get(j);														// neues minimum gespeichert
				}

				if (Platzhalter.getHersteller().compareTo(fahrzeugListe.get(j).getHersteller()) == 0) {		// Vergleich der Marke bei gleichem Hersteller
					if (Platzhalter.getMarke().compareTo(fahrzeugListe.get(j).getMarke()) > 0) {
						minIndex = j; 																		// Fahrzeug an der Stelle j
						Platzhalter = fahrzeugListe.get(j);													// neues minimum gespeichert

					}
				}
			}
			fahrzeugListe.set(minIndex, fahrzeugListe.get(i));												// an der Position mit dem neuen Minimum wird das i-te Element eingetragen
			fahrzeugListe.set(i, Platzhalter);																// an der Position i wird das gefundene Minimum eingetragen
		}
		return fahrzeugListe;
	}

	/***************************************************************************
    METHODENNAME:	sortFahrzeugMarke
    *//*!
     Sortiert eine ObservableList aus Fahrzeug nach Marke

     \param   ObservableList<Fahrzeug>

     \return  ObservableList<Fahrzeug>

    ***************************************************************************/

	public ObservableList<Fahrzeug> sortFahrzeugMarke (ObservableList<Fahrzeug> fahrzeugs) {

		ObservableList<Fahrzeug> fahrzeugListe= FXCollections.observableArrayList();		// neue Liste initialisieren
      	fahrzeugListe.setAll(mainApp.getFahrzeugData());									// alte Liste in neue Liste kopieren
      	Fahrzeug Platzhalter= new Fahrzeug();												// neues Fahrzeug als Platzhalter erzeugen

  		for (int i= 0; i < fahrzeugListe.size() - 1; i++) {
  			int minIndex= i;																// Annahme: aktuelles Fahrzeug = minimum; Index speichern
  			Platzhalter= fahrzeugListe.get(i);												// Annahme: aktuelles Fahrzeug = minimum; Fahrzeug speichern

  			for (int j= i + 1; j < fahrzeugListe.size(); j++) {

  				if (Platzhalter.getMarke().compareTo(fahrzeugListe.get(j).getMarke()) > 0) {				// Vergleich: mit Rest der Liste (ab aktuell + 1, nacheinander)
  					minIndex= j;																			// Fahrzeug an der Stelle j
  					Platzhalter= fahrzeugListe.get(j);														// neues minimum gespeichert
  				}
  			}

  			fahrzeugListe.set(minIndex, fahrzeugListe.get(i));												// an der Position mit dem neuen Minimum wird das i-te Element eingetragen
  			fahrzeugListe.set(i, Platzhalter);																// an der Position i wird das gefundene Minimum eingetragen
		}
  		return fahrzeugListe;
	}

	/***************************************************************************
    METHODENNAME:	sortFahrzeugKilometerstand
    *//*!
     Sortiert eine ObservableList aus Fahrzeug nach Kilometerstand

     \param   ObservableList<Fahrzeug>

     \return  ObservableList<Fahrzeug>

    ***************************************************************************/

	public ObservableList<Fahrzeug> sortFahrzeugKilometerstand (ObservableList<Fahrzeug> fahrzeugs) {

       	ObservableList<Fahrzeug> fahrzeugListe= FXCollections.observableArrayList();		// neue Liste initialisieren
       	fahrzeugListe.setAll(mainApp.getFahrzeugData());									// alte Liste in neue Liste kopieren
       	Fahrzeug Platzhalter= new Fahrzeug();												// neues Fahrzeug als Platzhalter erzeugen

   		for (int i= 0; i < fahrzeugListe.size() - 1; i++) {
   			int minIndex= i;																// Annahme: aktuelles Fahrzeug = minimum; Index speichern
   			Platzhalter= fahrzeugListe.get(i);												// Annahme: aktuelles Fahrzeug = minimum; Fahrzeug speichern

   			for (int j= i + 1; j < fahrzeugListe.size(); j++) {

   				if (Platzhalter.getKilometerstand() > (fahrzeugListe.get(j).getKilometerstand())) {			// Vergleich: mit Rest der Liste (ab aktuell + 1)
   					minIndex= j;																			// Fahrzeug an der Stelle j
   					Platzhalter= fahrzeugListe.get(j);														// neues minimum gespeichert
   				}
   			}
   			fahrzeugListe.set(minIndex, fahrzeugListe.get(i));												// an der Position mit dem neuen Minimum wird das i-te Element eingetragen
   			fahrzeugListe.set(i, Platzhalter);																// an der Position i wird das gefundene Minimum eingetragen
   		}
   		return fahrzeugListe;
   	}

	/***************************************************************************
    METHODENNAME:	sortFahrzeugLeistung
    *//*!
     Sortiert eine ObservableList aus Fahrzeug nach Leistung

     \param   ObservableList<Fahrzeug>

     \return  ObservableList<Fahrzeug>

    ***************************************************************************/

	public ObservableList<Fahrzeug> sortFahrzeugLeistung (ObservableList<Fahrzeug> fahrzeugs) {

     	ObservableList<Fahrzeug> fahrzeugListe= FXCollections.observableArrayList();		// neue Liste initialisieren
     	fahrzeugListe.setAll(mainApp.getFahrzeugData());									// alte Liste in neue Liste kopieren
     	Fahrzeug Platzhalter= new Fahrzeug();												// neues Fahrzeug als Platzhalter erzeugen
     																						// notwendig, für das spaetere tauschen innerhalb der Liste


     	for (int i= 0; i < fahrzeugListe.size() - 1; i++) {
 			int minIndex= i;																// Annahme: aktuelles Fahrzeug = Minimum; Index speichern
 			Platzhalter= fahrzeugListe.get(i);												// Annahme: aktuelles Fahrzeug = Minimum; Fahrzeug speichern

 			for (int j= i + 1; j < fahrzeugListe.size(); j++) {

 				if (Platzhalter.getLeistung() > (fahrzeugListe.get(j).getLeistung())) {						// Vergleich: mit Rest der Liste (ab aktuell + 1)
 					minIndex= j;																			// Fahrzeug an der Stelle j
 					Platzhalter= fahrzeugListe.get(j);														// neues Minimum gespeichert
 				}
 			}
 			fahrzeugListe.set(minIndex, fahrzeugListe.get(i));												// an der Position mit dem neuen Minimum wird das i-te Element eingetragen
 			fahrzeugListe.set(i, Platzhalter);																// an der Position i wird das gefundene Minimum eingetragen
 		}
 		return fahrzeugListe;
 	}


    /***************************************************************************
    METHODENNAME:	sortFahrzeugAusleihende
    *//*!
     Sortiert eine ObservableList aus Fahrzeug nach Rückgabedatum

     \param   ObservableList<Fahrzeug>

     \return  ObservableList<Fahrzeug>

    ***************************************************************************/

	public ObservableList<Fahrzeug> sortFahrzeugAusleihende (ObservableList<Fahrzeug> fahrzeugs) {

     	ObservableList<Buchung> buchungListe= FXCollections.observableArrayList();				// neue Liste initialisieren
     	buchungListe.setAll(mainApp.getBuchungData());											// alte Liste in neue Liste kopieren
     	Buchung Platzhalter= new Buchung();														// neues Fahrzeug als Platzhalter erzeugen

 		for (int i= 0; i < buchungListe.size() - 1; i++) {
 			int minIndex= i;																	// Annahme: aktuelles Fahrzeug = Minimum; Index speichern
 			Platzhalter= buchungListe.get(i);													// Annahme: aktuelles Fahrzeug = Minimum; Fahrzeug speichern

 			for (int j= i + 1; j < buchungListe.size(); j++) {
 				if (Platzhalter.getRueckgabedatum().compareTo(buchungListe.get(j).getRueckgabedatum()) > 0) {	// Vergleich: mit Rest der Liste (ab aktuell + 1)
 					minIndex= j;																				// Fahrzeug an der Stelle j
 					Platzhalter= buchungListe.get(j);															// neues Minimum gespeichert
 				}
 			}
 			buchungListe.set(minIndex, buchungListe.get(i));
 			buchungListe.set(i, Platzhalter);
 		}

 		ObservableList<Fahrzeug> fahrzeugeListe= FXCollections.observableArrayList();
 		for (int i= 0; i < buchungListe.size(); i++) {											// gehe die nach Datum sortierte buchungListe durch

 			for (int j = 0;  j < fahrzeugs.size(); j++)  {										// gehe die Fahrzeugliste durch,
 				if (buchungListe.get(i).getFahrzeugID() == fahrzeugs.get(j).getFahrzeugID()) {	// um das entsprechende Fahrzeug anhand der ID zu finden

 					fahrzeugeListe.add(fahrzeugs.get(j));										// fuege das Fahrzeug zur sortierten Fahrzeugliste hinzu
 				}
 			}
 		}
 		return fahrzeugeListe;
 	}
}

/** @}*/ /*end of doxygen group*/