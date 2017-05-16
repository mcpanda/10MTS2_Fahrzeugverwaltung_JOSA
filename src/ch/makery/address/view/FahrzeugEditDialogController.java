/**************************************************************************************************/
/*! \file
  FILE         : $Source: FahrzeugEditDialogController.java $
  BESCHREIBUNG : Controller
                 Controller fuer das Dialogfeld zur Fahrzeugbearbeitung.
                 Hierzu zaehlt New, Edit, Delete, Buchung
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
     Handler fuer OK. Dieser handler wird ausgefuehrt, wenn [OK] geklickt wurde.
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
     Handler fuer Cancel. Dieser handler wird ausgefuehrt, wenn [Cancel] geklickt
     wurde. Dialogfenster wird geschlossen, ohne neue Fahrzeugdaten zu speichern.

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
     Ueberpruefung der eingegeben Daten. Sollten nicht konforme Daten vorhanden
     sein, so wird eine Fehlermeldung ausgegeben.

     \param   void

     \return  Boolean

    ***************************************************************************/

    private boolean isInputValid(List<Fahrzeug> fahrzeugs) {
        String errorMessage = "";

        if (fahrzeugIDField.getText() == null || fahrzeugIDField.getText().length() == 0 || Integer.parseInt(fahrzeugIDField.getText()) < 1) {
            errorMessage += "Keine gültige FahrzeugID!\n";
        } else {
            // try to parse the fahrzeugID into an int.
            try {
                Integer.parseInt(fahrzeugIDField.getText());

            } catch (NumberFormatException e) {
                errorMessage += "Keine gültige fahrzeugID (fahrzeugID muss eine Zahl sein)!\n";
            }
        }

        /* pruefe ob FahrzeugID bereits vorhanden ist */
        if (Integer.parseInt(fahrzeugIDField.getText()) != fahrzeug.getFahrzeugID()) {
        	for(Fahrzeug f : fahrzeugs) {
            	if (Integer.parseInt(fahrzeugIDField.getText()) == f.getFahrzeugID()) {
            		errorMessage += "FahrzeugID existiert bereits";
            	}
            }
        }

        if (herstellerField.getText() == null || herstellerField.getText().length() == 0) {
            errorMessage += "Kein gültiger Hersteller!\n";
        }
        if (markeField.getText() == null || markeField.getText().length() == 0) {
            errorMessage += "Keine gültige Marke!\n";
        }
        if (kraftstoffField.getText() == null || kraftstoffField.getText().length() == 0) {
            errorMessage += "Kein gültiger Kraftstoff!\n";
        }
        if (fahrzeugtypBox.getValue() == null || fahrzeugtypBox.getValue().length() == 0) {
            errorMessage += "Bitte Fahrzeugtyp auswählen!\n";
        }

        if (leistungField.getText() == null || leistungField.getText().length() == 0) {
            errorMessage += "Keine gültige Leistung!\n";
        } else {
            // try to parse the leistung into an int.
            try {
                Integer.parseInt(leistungField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Keine gültige Leistung (Leistung muss eine Zahl sein)!\n";
            }
        }

        if (kilometerstandField.getText() == null || kilometerstandField.getText().length() == 0) {
            errorMessage += "Kein gültiger Kilometerstand!\n";
        } else {
            // try to parse the kilometerstand into an int.
            try {
                Integer.parseInt(kilometerstandField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Kein gültiger Kilometerstand (Kilometerstand muss eine Zahl sein)!\n";
            }
        }

        // falls die Laenge der Fehlermeldung 0 ist, so wird keine Fehlermeldung ausgegeben
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ungültige Eingabe");
            alert.setHeaderText("Bitte korrigieren Sie die ungültigen Eingaben");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}

/** @}*/ /*end of doxygen group*/