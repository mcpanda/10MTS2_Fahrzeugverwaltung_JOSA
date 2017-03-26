package ch.makery.address.view;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

/**************************************************************************/
/*                                                                        */
/* Class PersonEditDialogController                                     */
/*                                                                        */
/**************************************************************************/

public class PersonEditDialogController {

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

	/**************************************************************************/
	/*                                                                        */
	/* FXML Field Section                                                     */
	/*                                                                        */
	/**************************************************************************/

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

	BESCHREIBUNG:   Initialisiert die Controller Klasse. Diese Methode wird
					automatisch aufgerufen, nachdem die fxml Datei
					geladen wurde

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void initialize() {
    	lizenzBox.getItems().addAll("Klasse A", "Klasse B", "Klasse C");
    }

	/***************************************************************************

	METHODENNAME:	setDialogStage

	BESCHREIBUNG:   Gibt die Stage des Dialagfeldes an.

	PARAMETER: 		dialogStage

	RETURN:			void

	***************************************************************************/

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	/***************************************************************************

	METHODENNAME:	setPerson

	BESCHREIBUNG:   Definiert die Person, welche bearbeitet werden soll.

	PARAMETER: 		Person

	RETURN:			void

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

	BESCHREIBUNG:   Gibt ein TRUE aus, wenn [OK] geklickt wurde.

	PARAMETER: 		void

	RETURN:			Boolean

	***************************************************************************/

    public boolean isOkClicked() {
        return okClicked;
    }

	/***************************************************************************

	METHODENNAME:	handleOk

	BESCHREIBUNG:   handler für OK.
					Dieser handler wird ausgeführt, wenn [OK] geklickt wurde.
					Neue Personendaten werden gespeichert.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleOk() {
        if (isInputValid()) {

        	person.setPersonID(Integer.parseInt(personIDField.getText()));
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setLizenz(lizenzBox.getValue());
            person.setAusgeliehen("Nein");
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

	/***************************************************************************

	METHODENNAME:	handleCancel

	BESCHREIBUNG:   handler für Cancel.
					Dieser handler wird ausgeführt, wenn [Cancel] geklickt wurde.
					Dialogfenster wird geschloßen, ohne neue Personendaten
					zu speichern.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

	/***************************************************************************

	METHODENNAME:	isInputValid

	BESCHREIBUNG:   Überprüfung der eingegeben Daten. Sollten nicht konforme
					Daten vorhanden sein, so wird eine Fehlermeldung ausgegeben.

	PARAMETER: 		void

	RETURN:			Boolean

	***************************************************************************/

    private boolean isInputValid() {
        String errorMessage = "";

        if (personIDField.getText() == null || personIDField.getText().length() == 0) {
            errorMessage += "No valid PersonID!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(personIDField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid PersonID (must be an integer)!\n";
            }
        }

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
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