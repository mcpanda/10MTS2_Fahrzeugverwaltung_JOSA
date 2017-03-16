package ch.makery.address.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

import ch.makery.address.MainApp;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.util.DateUtil;


public class FahrzeugOverviewController {
    @FXML
    private TableView<Fahrzeug> fahrzeugTable;
    @FXML
    private TableColumn<Fahrzeug, String> herstellerColumn;
    @FXML
    private TableColumn<Fahrzeug, String> markeColumn;

    @FXML
    private Label herstellerLabel;
    @FXML
    private Label markeLabel;
    @FXML
    private Label kraftstoffLabel;
    @FXML
    private Label leistungLabel;
    @FXML
    private Label fahrzeugidentifikationsnummerLabel;
    @FXML
    private Label aenderungsdatumLabel;
    @FXML
    private Label ausleihzustandLabel;
    @FXML
    private Label automatikLabel;
    @FXML
    private Label kilometerstandLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public FahrzeugOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the Fahrzeug table with the two columns.
        herstellerColumn.setCellValueFactory(cellData -> cellData.getValue().herstellerProperty());
        markeColumn.setCellValueFactory(cellData -> cellData.getValue().markeProperty());

        // Clear Fahrzeug details.
        showFahrzeugDetails(null);

        // Listen for selection changes and show the Fahrzeug details when changed.
        fahrzeugTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFahrzeugDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        fahrzeugTable.setItems(mainApp.getFahrzeugData());
    }

    /**
     * Fills all text fields to show details about the Fahrzeug.
     * If the specified Fahrzeug is null, all text fields are cleared.
     *
     * @param Fahrzeug the Fahrzeug or null
     */
    private void showFahrzeugDetails(Fahrzeug fahrzeug) {
        if (fahrzeug != null) {
            // Fill the labels with info from the Fahrzeug object.
            herstellerLabel.setText(fahrzeug.getHersteller());
            markeLabel.setText(fahrzeug.getMarke());
            kraftstoffLabel.setText(fahrzeug.getKraftstoff());
            leistungLabel.setText(Integer.toString(fahrzeug.getLeistung()));
            fahrzeugidentifikationsnummerLabel.setText(fahrzeug.getFahrzeugidentifikationsnummer());
            aenderungsdatumLabel.setText(DateUtil.format(fahrzeug.getAenderungsdatum()));
            ausleihzustandLabel.setText(fahrzeug.getAusleihzustand());
            automatikLabel.setText(fahrzeug.getAutomatik());
            kilometerstandLabel.setText(Integer.toString(fahrzeug.getKilometerstand()));

        } else {
            // Fahrzeug is null, remove all the text.
        	herstellerLabel.setText("");
        	markeLabel.setText("");
        	kraftstoffLabel.setText("");
        	leistungLabel.setText("");
        	fahrzeugidentifikationsnummerLabel.setText("");
        	aenderungsdatumLabel.setText("");
        	ausleihzustandLabel.setText("");
        	automatikLabel.setText("");
        	kilometerstandLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
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

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new Fahrzeug.
     */
    @FXML
    private void handleNewFahrzeug() {
        Fahrzeug tempFahrzeug = new Fahrzeug();
        boolean okClicked = mainApp.showFahrzeugEditDialog(tempFahrzeug);
        if (okClicked) {
            mainApp.getFahrzeugData().add(tempFahrzeug);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected Fahrzeug.
     */
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