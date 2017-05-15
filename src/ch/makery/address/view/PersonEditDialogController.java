/**************************************************************************************************/
/*! \file
  FILE         : $Source: PersonEditController.java $
  BESCHREIBUNG : Controller
                 Controller für das Dialogfeld zur Personenbearbeitung.
                 Hierzu zählt New, Edit, Delete, Buchung, Suchen
***************************************************************************************************/

/** \addtogroup View
 *  @{
 */

package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

/***************************************************************************
CLASS:	PersonEditDialogController
*//*!
 Die Klasse PersonEditDialogController hat nur einen Standardkonstruktor

***************************************************************************/

public class PersonEditDialogController {

	private MainApp mainApp;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;
	ObservableList<String> filteredPersonIDBoxList= FXCollections.observableArrayList();

	/**************************************************************************/
	/*                                                                        */
	/* FXML Field Section                                                     */
	/*                                                                        */
	/**************************************************************************/

	@FXML
    private Label personIDLabel;
	@FXML
    private TextField personIDField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;
    @FXML
    private ChoiceBox<String> lizenzBox;

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
    	lizenzBox.getItems().addAll("Klasse A", "Klasse A + B", "Klasse A + B + C", "Klasse B", "Klasse B + C");
    }

    /***************************************************************************
    METHODENNAME:	setDialogStage
    *//*!
     Gibt die Stage des Dialagfeldes an.

     \param   dialogStage

     \return  void

    ***************************************************************************/

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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
    METHODENNAME:	setPerson
    *//*!
     Definiert die Person, welche bearbeitet werden soll.

     \param   Person

     \return  void

    ***************************************************************************/

    public void setPerson(Person person) {
        this.person = person;

        personIDField.setText(Integer.toString(person.getPersonID()));
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        lizenzBox.setValue(person.getLizenz());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    /***************************************************************************
    METHODENNAME:	isOkClicked
    *//*!
     Gibt ein TRUE aus, wenn [OK] geklickt wurde.

     \param   void

     \return  Boolean

    ***************************************************************************/

    public boolean isOkClicked() {
        return okClicked;
    }

    /***************************************************************************
    METHODENNAME:	handleOk
    *//*!
     Handler für OK. Dieser handler wird ausgeführt, wenn [OK] geklickt wurde.
	 Neue Personendaten werden gespeichert.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleOk() {
        if (isInputValid(mainApp.getPersonData())) {

        	person.setPersonID(Integer.parseInt(personIDField.getText()));
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setLizenz(lizenzBox.getValue());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /***************************************************************************
    METHODENNAME:	handleCancel
    *//*!
     Handler für Cancel. Dieser handler wird ausgeführt, wenn [Cancel] geklickt
     wurde. Dialogfenster wird geschloßen, ohne neue Personendaten zu speichern.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /***************************************************************************
    METHODENNAME:	isInputValid
    *//*!
     Überprüfung der eingegeben Daten. Sollten nicht konforme Daten vorhanden
     sein, so wird eine Fehlermeldung ausgegeben.

     \param   void

     \return  Boolean

    ***************************************************************************/

    private boolean isInputValid(List<Person> persons) {
        String errorMessage = "";

        if (personIDField.getText() == null || personIDField.getText().length() == 0 || Integer.parseInt(personIDField.getText()) < 1) {
            errorMessage += "Keine gültige PersonID!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(personIDField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Keine gültige PersonID (PersonID muss eine Zahl sein)!\n";
            }
        }

        if (Integer.parseInt(personIDField.getText()) != person.getPersonID()) {
        	for(Person p : persons) {
            	if (Integer.parseInt(personIDField.getText()) == p.getPersonID()) {
            		errorMessage += "PersonID existiert bereits schon";
            	}
            }
        }
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Kein gültiger Vorname!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Kein gültiger Nachnahme!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "Keine gültige Strasse!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() != 5) {
            errorMessage += "Keine gültige PLZ!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Keine gültige PLZ (PLZ muss eine Zahl sein)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "Keine gültige Stadt!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Kein gültiges Geburtsdatum!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "Kein gültiges Geburtsdatum. Bitte nutzen Sie das Format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
}

/** @}*/ /*end of doxygen group*/