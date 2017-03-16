package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.util.DateUtil;

/**
 * Dialog to edit details of a fahrzeug.
 *
 * @author Marco Jakob
 */
public class FahrzeugEditDialogController {

    @FXML
    private TextField herstellerField;
    @FXML
    private TextField markeField;
    @FXML
    private TextField kraftstoffField;
    @FXML
    private TextField leistungField;
    @FXML
    private TextField fahrzeugidentifikationsnummerField;
    @FXML
    private TextField aenderungsdatumField;
    @FXML
    private TextField ausleihzustandField;
    @FXML
    private TextField automatikField;
    @FXML
    private TextField kilometerstandField;


    private Stage dialogStage;
    private Fahrzeug fahrzeug;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the fahrzeug to be edited in the dialog.
     *
     * @param fahrzeug
     */
    public void setFahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;

        herstellerField.setText(fahrzeug.getHersteller());
        markeField.setText(fahrzeug.getMarke());
        kraftstoffField.setText(fahrzeug.getKraftstoff());
        leistungField.setText(Integer.toString(fahrzeug.getLeistung()));
        fahrzeugidentifikationsnummerField.setText(fahrzeug.getFahrzeugidentifikationsnummer());
        aenderungsdatumField.setText(DateUtil.format(fahrzeug.getAenderungsdatum()));
        ausleihzustandField.setText(fahrzeug.getAusleihzustand());
        automatikField.setText(fahrzeug.getAutomatik());
        kilometerstandField.setText(Integer.toString(fahrzeug.getKilometerstand()));
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            fahrzeug.setHersteller(herstellerField.getText());
            fahrzeug.setMarke(markeField.getText());
            fahrzeug.setKraftstoff(kraftstoffField.getText());
            fahrzeug.setLeistung(Integer.parseInt(leistungField.getText()));
            fahrzeug.setFahrzeugidentifikationsnummer(fahrzeugidentifikationsnummerField.getText());
            fahrzeug.setAenderungsdatum(DateUtil.parse(aenderungsdatumField.getText()));
            fahrzeug.setAusleihzustand(ausleihzustandField.getText());
            fahrzeug.setAutomatik(automatikField.getText());
            fahrzeug.setKilometerstand(Integer.parseInt(kilometerstandField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

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
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(leistungField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Leistung (must be an integer)!\n";
            }
        }

        if (fahrzeugidentifikationsnummerField.getText() == null || fahrzeugidentifikationsnummerField.getText().length() == 0) {
            errorMessage += "No valid Fahrzeugidentifikationsnummer!\n";
        }

        if (aenderungsdatumField.getText() == null || aenderungsdatumField.getText().length() == 0) {
            errorMessage += "No valid Aenderungsdatum!\n";
        } else {
            if (!DateUtil.validDate(aenderungsdatumField.getText())) {
                errorMessage += "No valid Aenderungsdatum. Use the format dd.mm.yyyy!\n";
            }
        }

        if (ausleihzustandField.getText() == null || ausleihzustandField.getText().length() == 0) {
            errorMessage += "No valid Ausleihzustandseingabe!\n";
        }

        if (automatikField.getText() == null || automatikField.getText().length() == 0) {
            errorMessage += "No valid Automatikeingabe!\n";
        }

        if (kilometerstandField.getText() == null || kilometerstandField.getText().length() == 0) {
            errorMessage += "No valid Kilometerstand!\n";
        } else {
            // try to parse the postal code into an int.
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