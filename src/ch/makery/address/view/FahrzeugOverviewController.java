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
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.util.DateUtil;

/**************************************************************************/
/*                                                                        */
/* Class FahrzeugOverviewController                                       */
/*                                                                        */
/**************************************************************************/

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
    private Label aenderungsdatumLabel;
    @FXML
    private Label kilometerstandLabel;

	/**************************************************************************/
	/*                                                                        */
	/* Constructur                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktur. Muss vor dem Initializieren aufgerufen werden.   */

    public FahrzeugOverviewController() {
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

        // Clear Fahrzeug details.
        showFahrzeugDetails(null);

        // Listen for selection changes and show the Fahrzeug details when changed.
        fahrzeugTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFahrzeugDetails(newValue));
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
        fahrzeugTable.setItems(mainApp.getFahrzeugData());
    }

	/***************************************************************************

	METHODENNAME:	showFahrzeugDetails

	BESCHREIBUNG:   Zeigt die Details eines ausgewählten Fahrzeugs an.
					Ist kein Fahrzeug ausgewählt, so wird nichts angezeigt.

	PARAMETER: 		Fahrzeug.
					Einn Objekt der Klasse Fahrzeug, von welchem die
					Details angezeigt werden sollen.

	RETURN:			void

	***************************************************************************/

    private void showFahrzeugDetails(Fahrzeug fahrzeug) {
        if (fahrzeug != null) {
        	fahrzeugIDLabel.setText(Integer.toString(fahrzeug.getLeistung()));
            // Fill the labels with info from the Fahrzeug object.
            herstellerLabel.setText(fahrzeug.getHersteller());
            markeLabel.setText(fahrzeug.getMarke());
            kraftstoffLabel.setText(fahrzeug.getKraftstoff());
            leistungLabel.setText(Integer.toString(fahrzeug.getLeistung()));
            aenderungsdatumLabel.setText(DateUtil.format(fahrzeug.getAenderungsdatum()));
            kilometerstandLabel.setText(Integer.toString(fahrzeug.getKilometerstand()));
        } else {
            // Fahrzeug is null, remove all the text.
        	fahrzeugIDLabel.setText("");
        	herstellerLabel.setText("");
        	markeLabel.setText("");
        	kraftstoffLabel.setText("");
        	leistungLabel.setText("");
        	aenderungsdatumLabel.setText("");
        	kilometerstandLabel.setText("");
        }
    }

	/***************************************************************************

	METHODENNAME:	handleDeleteFahrzeug

	BESCHREIBUNG:   handler für den Delete Button.
					Wird Delete angeklickt, so wird das ausgewählte Objekt
					gelöscht.

	PARAMETER: 		void

	RETURN:			void

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

	BESCHREIBUNG:   handler für den New Button.
					Wird New angeklickt, so wird ein Dialogfeld aufgerufen, um
					ein neues Objekt von der Klasse Fahrzeug zu erstellen.
					Hierbei können alle nötigen Attribute eingegeben werden.

	PARAMETER: 		void

	RETURN:			void

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

	METHODENNAME:	handleEditFahrzeug

	BESCHREIBUNG:   handler für den Edit Button.
					Wird Edit angeklickt, so wird ein Dialogfeld aufgerufen, um
					die Attribute, des ausgewählten Fahrzeugs, verändern zu
					können.

	PARAMETER: 		void

	RETURN:			void

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