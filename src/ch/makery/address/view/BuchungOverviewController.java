package ch.makery.address.view;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Person;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.util.DateUtil;
import javafx.collections.ObservableList;

/**************************************************************************/
/*                                                                        */
/* Class BuchungOverviewController                                       */
/*                                                                        */
/**************************************************************************/

public class BuchungOverviewController {

    // Reference to the main application.
    private MainApp mainApp;
    
	/**************************************************************************/
	/*                                                                        */
	/* FXML Column/Labeln Section                                             */
	/*                                                                        */
	/**************************************************************************/

    @FXML
    private TableView<Buchung> buchungTable;
    @FXML
    private TableColumn<Buchung, Integer> buchungIDColumn;
    @FXML
    private TableColumn<Buchung, Integer> personIDColumn;
    @FXML
    private TableColumn<Buchung, Integer> fahrzeugIDColumn;

    @FXML
    private Label buchungIDLabel;
    @FXML
    private Label personIDLabel;
    @FXML
    private Label vornameLabel;
    @FXML
    private Label nachnameLabel;
    @FXML
    private Label fahrzeugIDLabel;
    @FXML
    private Label herstellerLabel;
    @FXML
    private Label markeLabel;
    @FXML
    private Label ausleihdatumLabel;
    @FXML
    private Label rueckgabedatumLabel;
    @FXML
    private Label leihdauerLabel;

	/**************************************************************************/
	/*                                                                        */
	/* Constructur                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktur. Muss vor dem Initializieren aufgerufen werden.   */

    public BuchungOverviewController() {
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
        // Initialize the Buchung table with the two columns.
    	buchungIDColumn.setCellValueFactory(cellData -> cellData.getValue().buchungIDProperty().asObject());
        personIDColumn.setCellValueFactory(cellData -> cellData.getValue().personIDProperty().asObject());
        fahrzeugIDColumn.setCellValueFactory(cellData -> cellData.getValue().fahrzeugIDProperty().asObject());

        // Clear Buchung details.
        showBuchungDetails(null);

        // Listen for selection changes and show the buchung details when changed.
        buchungTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBuchungDetails(newValue));
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
        buchungTable.setItems(mainApp.getBuchungData());
    }

	/***************************************************************************

	METHODENNAME:	showbuchungDetails

	BESCHREIBUNG:   Zeigt die Details einer ausgewählten Buchungs an.
					Ist kein Buchung ausgewählt, so wird nichts angezeigt.

	PARAMETER: 		Buchung.
					Ein Objekt der Klasse Buchung, von welchem die
					Details angezeigt werden sollen.

	RETURN:			void

	***************************************************************************/

    private void showBuchungDetails(Buchung buchung) {
        if (buchung != null) {
        	buchungIDLabel.setText(Integer.toString(buchung.getBuchungID()));
            // Fill the labels with info from the buchung object.
        	personIDLabel.setText(Integer.toString(buchung.getPersonID()));
        	fahrzeugIDLabel.setText(Integer.toString(buchung.getFahrzeugID()));
        	leihdauerLabel.setText(Integer.toString(buchung.getLeihdauer()));

        	ObservableList<Person> personData = mainApp.getPersonData();
        	for(int i = 0; i<personData.size(); i++){
        		if(buchung.getPersonID() == personData.get(i).getPersonID()) {
        			vornameLabel.setText(personData.get(i).getFirstName());
                	nachnameLabel.setText(personData.get(i).getLastName());
        		}
        	}

        	ObservableList<Fahrzeug> fahrzeugData = mainApp.getFahrzeugData();
        	for(int i = 0; i < fahrzeugData.size(); i++){
        		if(buchung.getFahrzeugID() == fahrzeugData.get(i).getFahrzeugID()) {
        			herstellerLabel.setText(fahrzeugData.get(i).getHersteller());
                	markeLabel.setText(fahrzeugData.get(i).getMarke());
        		}
        	}

            ausleihdatumLabel.setText(DateUtil.format(buchung.getAusleihdatum()));
            rueckgabedatumLabel.setText(DateUtil.format(buchung.getRueckgabedatum()));
        } else {
            // buchung is null, remove all the text.
        	buchungIDLabel.setText("");
        	personIDLabel.setText("");
        	fahrzeugIDLabel.setText("");
        	leihdauerLabel.setText("");
        	ausleihdatumLabel.setText("");
        	rueckgabedatumLabel.setText("");
        }
    }

	/***************************************************************************

	METHODENNAME:	handleDeleteBuchung

	BESCHREIBUNG:   handler für den Delete Button.
					Wird Delete angeklickt, so wird das ausgewählte Objekt
					gelöscht.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleDeleteBuchung() {
        int selectedIndex = buchungTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            buchungTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Buchung Selected");
            alert.setContentText("Please select a Buchung in the table.");

            alert.showAndWait();
        }
    }

	/***************************************************************************

	METHODENNAME:	handleNewBuchung

	BESCHREIBUNG:   handler für den New Button.
					Wird New angeklickt, so wird ein Dialogfeld aufgerufen, um
					ein neues Objekt von der Klasse Buchung zu erstellen.
					Hierbei können alle nötigen Attribute eingegeben werden.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleNewBuchung() {
        Buchung tempBuchung = new Buchung();
        boolean okClicked = mainApp.showBuchungEditDialog(tempBuchung);
        if (okClicked) {
            mainApp.getBuchungData().add(tempBuchung);
        }
    }

	/***************************************************************************

	METHODENNAME:	handleEditBuchung

	BESCHREIBUNG:   handler für den Edit Button.
					Wird Edit angeklickt, so wird ein Dialogfeld aufgerufen, um
					die Attribute, der ausgewählten Buchung, verändern zu
					können.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleEditBuchung() {
        Buchung selectedBuchung = buchungTable.getSelectionModel().getSelectedItem();
        if (selectedBuchung != null) {
            boolean okClicked = mainApp.showBuchungEditDialog(selectedBuchung);
            if (okClicked) {
                showBuchungDetails(selectedBuchung);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Buchung Selected");
            alert.setContentText("Please select a Buchung in the table.");

            alert.showAndWait();
        }
    }

    
}