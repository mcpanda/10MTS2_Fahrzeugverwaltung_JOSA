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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

/**************************************************************************/
/*                                                                        */
/* Class PersonOverviewController                                         */
/*                                                                        */
/**************************************************************************/

public class PersonOverviewController {

    // Reference to the main application.
    private MainApp mainApp;
    private Stage primaryStage;

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
    private Label personIDLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

	/**************************************************************************/
	/*                                                                        */
	/* Constructur                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktur. Muss vor dem Initializieren aufgerufen werden.   */

    public PersonOverviewController() {
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

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
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
        personTable.setItems(mainApp.getPersonData());
    }

	/***************************************************************************

	METHODENNAME:	showPersonDetails

	BESCHREIBUNG:   Zeigt die Details einer ausgewählten Person an.
					Ist keine Person ausgewählt, so wird nichts angezeigt.

	PARAMETER: 		Person.
					Einn Objekt der Klasse Person, von welchem die
					Details angezeigt werden sollen.

	RETURN:			void

	***************************************************************************/

    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
        	personIDLabel.setText(Integer.toString(person.getPersonID()));
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
        	personIDLabel.setText("");
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

	/***************************************************************************

	METHODENNAME:	handleDeletePerson

	BESCHREIBUNG:   handler für den Delete Button.
					Wird Delete angeklickt, so wird das ausgewählte Objekt
					gelöscht.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

	/***************************************************************************

	METHODENNAME:	handleNewPerson

	BESCHREIBUNG:   handler für den New Button.
					Wird New angeklickt, so wird ein Dialogfeld aufgerufen, um
					ein neues Objekt von der Klasse Person zu erstellen.
					Hierbei können alle nötigen Attribute eingegeben werden.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

	/***************************************************************************

	METHODENNAME:	handleEditPerson

	BESCHREIBUNG:   handler für den Edit Button.
					Wird Edit angeklickt, so wird ein Dialogfeld aufgerufen, um
					die Attribute, der ausgewählten Person, verändern zu
					können.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }


    ///////////////////////////////////////////////////////////////////
    // Knopf(SuchenFenster)
 //////////////////////////////////////////////////////////////////
    @FXML
    private void SearchingResultDialog(){
	 	   try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/SuchenFenster.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();


	            	Stage SearchDialog = new Stage();
	            	SearchDialog.setTitle("Sie können jetzt die Ergebnisse des Suchen anschauen");
	            	SearchDialog.initModality(Modality.WINDOW_MODAL);
					SearchDialog.initOwner(primaryStage);
					Scene scene = new Scene(page);
	            	SearchDialog.setScene(scene);


	            	SearchDialog.showAndWait();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }
}