/**************************************************************************************************/
/*! \file
  FILE         : $Source: FahrzeugEditDialogController.java $
  BESCHREIBUNG : Controller
                 Controller für das Dialogfeld zur Fahrzeugbearbeitung.
                 Hierzu zählt New, Edit, Delete, Buchung
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import ch.makery.address.MainApp;
import ch.makery.address.model.Fahrzeug;

/***************************************************************************
CLASS:	FahrzeugEditDialogController
*//*!
 Die Klasse FahrzeugEditDialogController hat nur einen Standardkonstruktor

***************************************************************************/

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
    private ChoiceBox<String> fahrzeugtypBox;

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
    	fahrzeugtypBox.getItems().addAll("Motorrad", "Cityflitzer", "Langstrecke", "Kleintransporter", "LKW");
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
    METHODENNAME:	setFahrzeug
    *//*!
     Definiert die Fahrzeug, welche bearbeitet werden soll.

     \param   Fahrzeug

     \return  void

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
	 Neue Fahrzeugdaten werden gespeichert.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleOk() {
        if (isInputValid(mainApp.getFahrzeugData())) {

        	fahrzeug.setFahrzeugID(Integer.parseInt(fahrzeugIDField.getText()));
            fahrzeug.setHersteller(herstellerField.getText());
            fahrzeug.setMarke(markeField.getText());
            fahrzeug.setKraftstoff(kraftstoffField.getText());
            fahrzeug.setFahrzeugtyp(fahrzeugtypBox.getValue());
            fahrzeug.setLeistung(Integer.parseInt(leistungField.getText()));
            fahrzeug.setKilometerstand(Integer.parseInt(kilometerstandField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /***************************************************************************
    METHODENNAME:	handleCancel
    *//*!
     Handler für Cancel. Dieser handler wird ausgeführt, wenn [Cancel] geklickt
     wurde. Dialogfenster wird geschloßen, ohne neue Fahrzeugdaten zu speichern.

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

    private boolean isInputValid(List<Fahrzeug> fahrzeugs) {
        String errorMessage = "";

        if (fahrzeugIDField.getText() == null || fahrzeugIDField.getText().length() == 0 || Integer.parseInt(fahrzeugIDField.getText()) < 1) {
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

        // falls die Länge der Fehlermeldung 0 ist, so wird keine Fehlermeldung ausgegeben
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