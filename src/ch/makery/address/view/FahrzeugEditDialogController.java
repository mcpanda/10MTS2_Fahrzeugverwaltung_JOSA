package ch.makery.address.view;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import ch.makery.address.MainApp;
import ch.makery.address.model.Fahrzeug;
//import ch.makery.address.util.DateUtil;

/**************************************************************************/
/*                                                                        */
/* Class FahrzeugEditDialogController                                     */
/*                                                                        */
/**************************************************************************/

public class FahrzeugEditDialogController {

	private MainApp mainApp;

    private Stage dialogStage;
    private Fahrzeug fahrzeug;
    private boolean okClicked = false;

	/**************************************************************************/
	/*                                                                        */
	/* FXML Field Section                                                     */
	/*                                                                        */
	/**************************************************************************/

	@FXML
    private TextField fahrzeugIDField;
    @FXML
    private TextField herstellerField;
    @FXML
    private TextField markeField;
    @FXML
    private TextField kraftstoffField;
    @FXML
    private TextField leistungField;
    @FXML
    private TextField kilometerstandField;
    @FXML
    private Label ausgeliehenLabel;
    @FXML
    private ChoiceBox<String> fahrzeugtypBox;

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
    	fahrzeugtypBox.getItems().addAll("Motorrad", "Cityflitzer", "Langstrecke", "Kleintransporter", "LKW");
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

	METHODENNAME:	setFahrzeug

	BESCHREIBUNG:   Definiert das Fahrzeug, welches bearbeitet werden soll.

	PARAMETER: 		Fahrzeug

	RETURN:			void

	***************************************************************************/

    public void setFahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;

        fahrzeugIDField.setText(Integer.toString(fahrzeug.getFahrzeugID()));
        herstellerField.setText(fahrzeug.getHersteller());
        markeField.setText(fahrzeug.getMarke());
        kraftstoffField.setText(fahrzeug.getKraftstoff());
        fahrzeugtypBox.setValue(fahrzeug.getFahrzeugtyp());
        leistungField.setText(Integer.toString(fahrzeug.getLeistung()));
        kilometerstandField.setText(Integer.toString(fahrzeug.getKilometerstand()));
        ausgeliehenLabel.setText(fahrzeug.getAusgeliehen());
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
					Neue Fahrzeugdaten werden gespeichert.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleOk() {
        if (isInputValid(mainApp.getFahrzeugData())) {

        	fahrzeug.setFahrzeugID(Integer.parseInt(fahrzeugIDField.getText()));
            fahrzeug.setHersteller(herstellerField.getText());
            fahrzeug.setMarke(markeField.getText());
            fahrzeug.setKraftstoff(kraftstoffField.getText());
            fahrzeug.setFahrzeugtyp(fahrzeugtypBox.getValue());
            fahrzeug.setAusgeliehen(ausgeliehenLabel.getText());
            fahrzeug.setLeistung(Integer.parseInt(leistungField.getText()));
            fahrzeug.setKilometerstand(Integer.parseInt(kilometerstandField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

	/***************************************************************************

	METHODENNAME:	handleCancel

	BESCHREIBUNG:   handler für Cancel.
					Dieser handler wird ausgeführt, wenn [Cancel] geklickt wurde.
					Dialogfenster wird geschloßen, ohne neue Fahrzeugdaten
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

    private boolean isInputValid(List<Fahrzeug> fahrzeugs) {
        String errorMessage = "";

        System.out.println(fahrzeug.getFahrzeugID());

        if (fahrzeugIDField.getText() == null || fahrzeugIDField.getText().length() == 0) {
            errorMessage += "No valid FahrzeugID!\n";
        } else {
            // try to parse the fahrzeugID into an int.
            try {
                Integer.parseInt(fahrzeugIDField.getText());

            } catch (NumberFormatException e) {
                errorMessage += "No valid fahrzeugID (must be an integer)!\n";
            }
        }

        if (Integer.parseInt(fahrzeugIDField.getText()) != fahrzeug.getFahrzeugID()) {
        	for(Fahrzeug f : fahrzeugs) {
            	if (Integer.parseInt(fahrzeugIDField.getText()) == f.getFahrzeugID()) {
            		errorMessage += "FahrzeugID already exists";
            	}
            }
        }


        if (herstellerField.getText() == null || herstellerField.getText().length() == 0) {
            errorMessage += "No valid Hersteller!\n";
        }
        if (markeField.getText() == null || markeField.getText().length() == 0) {
            errorMessage += "No valid Marke!\n";
        }
        if (kraftstoffField.getText() == null || kraftstoffField.getText().length() == 0) {
            errorMessage += "No valid Kraftstoff!\n";
        }

        if (leistungField.getText() == null || leistungField.getText().length() == 0) {
            errorMessage += "No valid Leistung!\n";
        } else {
            // try to parse the leistung into an int.
            try {
                Integer.parseInt(leistungField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Leistung (must be an integer)!\n";
            }
        }

        if (kilometerstandField.getText() == null || kilometerstandField.getText().length() == 0) {
            errorMessage += "No valid Kilometerstand!\n";
        } else {
            // try to parse the kilometerstand into an int.
            try {
                Integer.parseInt(kilometerstandField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Kilometerstand (must be an integer)!\n";
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