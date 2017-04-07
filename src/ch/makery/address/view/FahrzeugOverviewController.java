/**************************************************************************************************/
/*! \file
  FILE         : $Source: FahrzeugOverviewController.java $
  BESCHREIBUNG : Controller
                 Controller f�r die Fahrzeug�bersicht
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Fahrzeug;

/***************************************************************************
CLASS:	FahrzeugOverviewController
*//*!
 Die Klasse FahrzeugOverviewController hat nur einen Standardkonstruktor.

***************************************************************************/

public class FahrzeugOverviewController {

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
    private Label fahrzeugIDLabel;
    @FXML
    private Label herstellerLabel;
    @FXML
    private Label markeLabel;
    @FXML
    private Label kraftstoffLabel;
    @FXML
    private Label leistungLabel;
    @FXML
    private Label kilometerstandLabel;
    @FXML
    private Label fahrzeugtypLabel;
    @FXML
    private Label ausgeliehenLabel;


	/**************************************************************************/
	/*                                                                        */
	/* Constructor                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktor. Muss vor dem Initializieren aufgerufen werden.   */

    public FahrzeugOverviewController() {
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

        // Clear Fahrzeug details.
        showFahrzeugDetails(null);

        // Listen for selection changes and show the Fahrzeug details when changed.
        fahrzeugTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFahrzeugDetails(newValue));
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

        // Add observable list data to the table
        fahrzeugTable.setItems(mainApp.getFahrzeugData());
    }

    /***************************************************************************
    METHODENNAME:	showFahrzeugDetails
    *//*!
     Zeigt die Details eines ausgew�hlten Fahrzeugs an. Ist kein Fahrzeug
     ausgew�hlt, so wird nichts angezeigt.

     \param   Fahrzeug. Ein Objekt der Klasse Fahrzeug, von welchem die
			  Details angezeigt werden sollen.

     \return  void

    ***************************************************************************/

    private void showFahrzeugDetails(Fahrzeug fahrzeug) {
        if (fahrzeug != null) {
        	fahrzeugIDLabel.setText(Integer.toString(fahrzeug.getFahrzeugID()));
            // Fill the labels with info from the Fahrzeug object.
            herstellerLabel.setText(fahrzeug.getHersteller());
            markeLabel.setText(fahrzeug.getMarke());
            kraftstoffLabel.setText(fahrzeug.getKraftstoff());
            fahrzeugtypLabel.setText(fahrzeug.getFahrzeugtyp());
            ausgeliehenLabel.setText(fahrzeug.getAusgeliehen());
            leistungLabel.setText(Integer.toString(fahrzeug.getLeistung()));
            kilometerstandLabel.setText(Integer.toString(fahrzeug.getKilometerstand()));
        } else {
            // Fahrzeug is null, remove all the text.
        	fahrzeugIDLabel.setText("");
        	herstellerLabel.setText("");
        	markeLabel.setText("");
        	kraftstoffLabel.setText("");
        	fahrzeugtypLabel.setText("");
            ausgeliehenLabel.setText("");
        	leistungLabel.setText("");
        	kilometerstandLabel.setText("");
        }
    }

    /***************************************************************************
    METHODENNAME:	handleDeleteFahrzeug
    *//*!
     Handler f�r den Delete Button. Wird Delete angeklickt, so wird das
     ausgew�hlte Objekt gel�scht.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleDeleteFahrzeug() {
        int selectedIndex = fahrzeugTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            fahrzeugTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Fahrzeug Selected");
            alert.setContentText("Please select a Fahrzeug in the table.");

            alert.showAndWait();
        }
    }


    /***************************************************************************
    METHODENNAME:	handleNewFahrzeug
    *//*!
     Handler f�r den New Button. Wird New angeklickt, so wird ein Dialogfeld
     aufgerufen, um ein neues Objekt von der Klasse Fahrzeug zu erstellen.
	 Hierbei k�nnen alle n�tigen Attribute eingegeben werden.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleNewFahrzeug() {
        Fahrzeug tempFahrzeug = new Fahrzeug();
        boolean okClicked = mainApp.showFahrzeugEditDialog(tempFahrzeug);
        if (okClicked) {
            mainApp.getFahrzeugData().add(tempFahrzeug);
        }
    }


    /***************************************************************************
    METHODENNAME:	handleFahrzeugBuchung
    *//*!
     Handler f�r den Buchungs Button. Hiermit wird eine neue Buchung angelegt
     und die Daten des ausgew�hlten Fahrzeuges geladen.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleFahrzeugBuchung() {
    	Fahrzeug selectedFahrzeug = fahrzeugTable.getSelectionModel().getSelectedItem();
    	Buchung tempBuchung= new Buchung();

        if (selectedFahrzeug != null) {
        	tempBuchung.setFahrzeugID(selectedFahrzeug.getFahrzeugID());

            boolean okClicked = mainApp.showBuchungEditDialog(tempBuchung);
            if (okClicked) {
            	mainApp.getBuchungData().add(tempBuchung);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Fahrzeug Selected");
            alert.setContentText("Please select a Fahrzeug in the table.");

            alert.showAndWait();
        }
    }

    /***************************************************************************
    METHODENNAME:	handleEditFahrzeug
    *//*!
    Handler f�r den Edit Button. Wird Edit angeklickt, so wird ein Dialogfeld
    aufgerufen, um die Attribute, des ausgew�hlten Fahrzeugs, ver�ndern zu k�nnen.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleEditFahrzeug() {
        Fahrzeug selectedFahrzeug = fahrzeugTable.getSelectionModel().getSelectedItem();
        if (selectedFahrzeug != null) {
            boolean okClicked = mainApp.showFahrzeugEditDialog(selectedFahrzeug);
            if (okClicked) {
                showFahrzeugDetails(selectedFahrzeug);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Fahrzeug Selected");
            alert.setContentText("Please select a Fahrzeug in the table.");

            alert.showAndWait();
        }
    }
}

/** @}*/ /*end of doxygen group*/