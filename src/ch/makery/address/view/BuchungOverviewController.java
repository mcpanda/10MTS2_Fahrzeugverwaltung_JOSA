/**************************************************************************************************/
/*! \file
  FILE         : $Source: PersonOverviewController.java $
  BESCHREIBUNG : Controller
                 Controller für die Personenübersicht
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;
import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Person;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.util.DateUtil;

/***************************************************************************
CLASS:	BuchungOverviewController
*//*!
 Die Klasse BuchungOverviewController hat nur einen Standardkonstruktor.

***************************************************************************/

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
    private Label fahrzeugtypLabel;
    @FXML
    private Label ausleihdatumLabel;
    @FXML
    private Label rueckgabedatumLabel;
    @FXML
    private Label leihdauerLabel;

	/**************************************************************************/
	/*                                                                        */
	/* Constructor                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktor. Muss vor dem Initializieren aufgerufen werden.   */

    public BuchungOverviewController() {
    }

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
        // Initialize the Buchung table with the three columns.
    	buchungIDColumn.setCellValueFactory(cellData -> cellData.getValue().buchungIDProperty().asObject());
        personIDColumn.setCellValueFactory(cellData -> cellData.getValue().personIDProperty().asObject());
        fahrzeugIDColumn.setCellValueFactory(cellData -> cellData.getValue().fahrzeugIDProperty().asObject());

        // Clear Buchung details.
        showBuchungDetails(null, null, null);

        // Listen for selection changes and show the buchung details when changed.
        buchungTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBuchungDetails(newValue, mainApp.getPersonData(), mainApp.getFahrzeugData()));
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

        // Add observable list data to the table
        buchungTable.setItems(mainApp.getBuchungData());
    }

    /***************************************************************************
    METHODENNAME:	showbuchungDetails
    *//*!
     Zeigt die Details einer ausgewählten Buchung an. Ist keine Buchung
     ausgewählt, so wird nichts angezeigt.

     \param   Buchung. Ein Objekt der Klasse Buchung, von welchem die
			  Details angezeigt werden sollen.

     \return  void

    ***************************************************************************/

    private void showBuchungDetails(Buchung buchung, List<Person> persons, List<Fahrzeug> fahrzeugs) {
        if (buchung != null) {
        	buchungIDLabel.setText(Integer.toString(buchung.getBuchungID()));
            // Fill the labels with info from the buchung object.
        	personIDLabel.setText(Integer.toString(buchung.getPersonID()));
        	fahrzeugIDLabel.setText(Integer.toString(buchung.getFahrzeugID()));
        	leihdauerLabel.setText(Integer.toString(buchung.getLeihdauer()));

        	for (Person p : persons) {
        		if(buchung.getPersonID() == p.getPersonID()) {
        			vornameLabel.setText(p.getFirstName());
                	nachnameLabel.setText(p.getLastName());
        		}
        	}

        	for (Fahrzeug f : fahrzeugs) {
        		if(buchung.getFahrzeugID() == f.getFahrzeugID()) {
        			herstellerLabel.setText(f.getHersteller());
                	markeLabel.setText(f.getMarke());
                	fahrzeugtypLabel.setText(f.getFahrzeugtyp());
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
        	fahrzeugtypLabel.setText("");
        	ausleihdatumLabel.setText("");
        	rueckgabedatumLabel.setText("");
        }
    }

    /***************************************************************************
    METHODENNAME:	handleDeleteBuchung
    *//*!
     Handler für den Delete Button. Wird Delete angeklickt, so wird das
     ausgewählte Objekt gelöscht.

     \param   void

     \return  void

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
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Es wurde keine Buchung ausgewählt");
            alert.setContentText("Bitte wählen Sie eine Buchung aus der Tabelle aus.");

            alert.showAndWait();
        }
    }

    /***************************************************************************
    METHODENNAME:	handleNewBuchung
    *//*!
     Handler für den New Button. Wird New angeklickt, so wird ein Dialogfeld
     aufgerufen, um ein neues Objekt von der Klasse Buchung zu erstellen.
	 Hierbei können alle nötigen Attribute eingegeben werden.

     \param   void

     \return  void

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
    *//*!
    Handler für den Edit Button. Wird Edit angeklickt, so wird ein Dialogfeld
    aufgerufen, um die Attribute, der ausgewählten Buchung, verändern zu können.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleEditBuchung() {
        Buchung selectedBuchung = buchungTable.getSelectionModel().getSelectedItem();
        if (selectedBuchung != null) {
            boolean okClicked = mainApp.showBuchungEditDialog(selectedBuchung);
            if (okClicked) {
                showBuchungDetails(selectedBuchung, mainApp.getPersonData(), mainApp.getFahrzeugData());
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

/** @}*/ /*end of doxygen group*/