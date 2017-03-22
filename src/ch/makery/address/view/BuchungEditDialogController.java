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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Person;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.util.DateUtil;

/**************************************************************************/
/*                                                                        */
/* Class BuchungEditDialogController                                      */
/*                                                                        */
/**************************************************************************/

public class BuchungEditDialogController {

	// Reference to the main application.
    private MainApp mainApp;

	ObservableList<Integer> personIDBoxList= FXCollections.observableArrayList();
	ObservableList<Integer> fahrzeugIDBoxList= FXCollections.observableArrayList();

    Stage dialogStage;
    private Buchung buchung;
    private boolean okClicked = false;

	/**************************************************************************/
	/*                                                                        */
	/* FXML Field Section                                                     */
	/*                                                                        */
	/**************************************************************************/

	@FXML
    private TextField buchungIDField;
    @FXML
    private TextField personIDField;
    @FXML
    private TextField fahrzeugIDField;
    @FXML
    private TextField ausleihdatumField;
    @FXML
    private TextField rueckgabedatumField;
    @FXML
    private TextField leihdauerField;

    @FXML
    private ComboBox<Integer> personIDBox;
    @FXML
    private ComboBox<Integer> fahrzeugIDBox;

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

    	personIDBox.setValue(1);
    	personIDBox.setItems(personIDBoxList);

    	fahrzeugIDBox.setValue(1);
    	fahrzeugIDBox.setItems(fahrzeugIDBoxList);

    }

	/***************************************************************************

	METHODENNAME:	handlerComboBoxList

	BESCHREIBUNG:   Lädt die IDs der Personen und Fahrzeuge in
					die Observablelisten ein

	PARAMETER: 		dialogStage

	RETURN:			void

	***************************************************************************/

    public void handlerComboBoxList() {

    	ObservableList<Person> personData = mainApp.getPersonData();
    	ObservableList<Fahrzeug> fahrzeugData = mainApp.getFahrzeugData();

	  	for(int i = 0; i<personData.size(); i++){
	  		personIDBoxList.add(personData.get(i).getPersonID());
	  	}

	  	for(int i = 0; i<fahrzeugData.size(); i++){
	  		fahrzeugIDBoxList.add(fahrzeugData.get(i).getFahrzeugID());
	  	}
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

	METHODENNAME:	setBuchung

	BESCHREIBUNG:   Definiert die Buchung, welche bearbeitet werden soll.

	PARAMETER: 		Buchung

	RETURN:			void

	***************************************************************************/

    public void setBuchung(Buchung buchung) {
        this.buchung = buchung;

        buchungIDField.setText(Integer.toString(buchung.getBuchungID()));
        personIDField.setText(Integer.toString(buchung.getPersonID()));
        fahrzeugIDField.setText(Integer.toString(buchung.getFahrzeugID()));
        leihdauerField.setText(Integer.toString(buchung.getLeihdauer()));

        ausleihdatumField.setText(DateUtil.format(buchung.getAusleihdatum()));
        ausleihdatumField.setPromptText("dd.mm.yyyy");
        rueckgabedatumField.setText(DateUtil.format(buchung.getRueckgabedatum()));
        rueckgabedatumField.setPromptText("dd.mm.yyyy");
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
					Neue Buchungsdaten werden gespeichert.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleOk() {
        if (isInputValid()) {

        	buchung.setBuchungID(Integer.parseInt(buchungIDField.getText()));
        	buchung.setPersonID(Integer.parseInt(personIDField.getText()));
        	buchung.setFahrzeugID(Integer.parseInt(fahrzeugIDField.getText()));
        	buchung.setLeihdauer(Integer.parseInt(leihdauerField.getText()));

            buchung.setAusleihdatum(DateUtil.parse(ausleihdatumField.getText()));
            buchung.setRueckgabedatum(DateUtil.parse(rueckgabedatumField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

	/***************************************************************************

	METHODENNAME:	handleCancel

	BESCHREIBUNG:   handler für Cancel.
					Dieser handler wird ausgeführt, wenn [Cancel] geklickt wurde.
					Dialogfenster wird geschloßen, ohne neue Buchungsdaten
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

        if (buchungIDField.getText() == null || buchungIDField.getText().length() == 0) {
            errorMessage += "No valid BuchungID!\n";
        } else {
            // try to parse the buchungID into an int.
            try {
                Integer.parseInt(buchungIDField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid buchungID (must be an integer)!\n";
            }
        }

        if (personIDField.getText() == null || personIDField.getText().length() == 0) {
            errorMessage += "No valid personID!\n";
        } else {
            // try to parse the personID into an int.
            try {
                Integer.parseInt(personIDField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid personID (must be an integer)!\n";
            }
        }

        if (fahrzeugIDField.getText() == null || fahrzeugIDField.getText().length() == 0) {
            errorMessage += "No valid fahrzeugID!\n";
        } else {
            // try to parse the fahrzeugID into an int.
            try {
                Integer.parseInt(fahrzeugIDField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid fahrzeugID (must be an integer)!\n";
            }
        }

        if (leihdauerField.getText() == null || leihdauerField.getText().length() == 0) {
            errorMessage += "No valid Leihdauer!\n";
        } else {
            // try to parse the leihdauer into an int.
            try {
                Integer.parseInt(leihdauerField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Leihdauer (must be an integer)!\n";
            }
        }

        if (ausleihdatumField.getText() == null || ausleihdatumField.getText().length() == 0) {
            errorMessage += "No valid Ausleihdatum!\n";
        } else {
            if (!DateUtil.validDate(ausleihdatumField.getText())) {
                errorMessage += "No valid Ausleihdatum. Use the format dd.mm.yyyy!\n";
            }
        }

        if (rueckgabedatumField.getText() == null || rueckgabedatumField.getText().length() == 0) {
            errorMessage += "No valid Rueckgabedatum!\n";
        } else {
            if (!DateUtil.validDate(rueckgabedatumField.getText())) {
                errorMessage += "No valid Rueckgabedatum. Use the format dd.mm.yyyy!\n";
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
}