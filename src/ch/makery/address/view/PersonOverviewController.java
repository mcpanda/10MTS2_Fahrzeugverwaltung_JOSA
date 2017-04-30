/**************************************************************************************************/
/*! \file
  FILE         : $Source: PersonOverviewController.java $
  BESCHREIBUNG : Controller
                 Controller für die Personenübersicht
***************************************************************************************************/

/** \defgroup View View
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
import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

/***************************************************************************
CLASS:	PersonOverviewController
*//*!
 Die Klasse PersonOverviewController hat nur einen Standardkonstruktor.

***************************************************************************/

public class PersonOverviewController {

    // Reference to the main application.
    private MainApp mainApp;

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
    @FXML
    private Label lizenzLabel;
    @FXML
    private Label ausgeliehenLabel;

	/**************************************************************************/
	/*                                                                        */
	/* Constructor                                                            */
	/*                                                                        */
	/**************************************************************************/

    /* Standard Konstruktor. Muss vor dem Initializieren aufgerufen werden.   */

    public PersonOverviewController() {
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
        // Initialize the person table with the three columns.
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
    *//*!
     Is called by the main application to give a reference back to itself.

     \param   mainApp

     \return  void

    ***************************************************************************/

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }

    /***************************************************************************
    METHODENNAME:	showPersonDetails
    *//*!
     Zeigt die Details einer ausgewählten Person an. Ist keine Person
     ausgewählt, so wird nichts angezeigt.

     \param   Person. Ein Objekt der Klasse Person, von welchem die
			  Details angezeigt werden sollen.

     \return  void

    ***************************************************************************/

    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
        	personIDLabel.setText(Integer.toString(person.getPersonID()));
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            lizenzLabel.setText(person.getLizenz());
            ausgeliehenLabel.setText(person.getAusgeliehen());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
        	personIDLabel.setText("");
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            lizenzLabel.setText("");
            ausgeliehenLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    /***************************************************************************
    METHODENNAME:	handleDeletePerson
    *//*!
     Handler für den Delete Button. Wird Delete angeklickt, so wird das
     ausgewählte Objekt gelöscht.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Person deletePerson= personTable.getItems().remove(selectedIndex);

            mainApp.getTreeData().delete(deletePerson);
            mainApp.getTreeData().levelOrder(mainApp.getTreeData().root);

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
    *//*!
     Handler für den New Button. Wird New angeklickt, so wird ein Dialogfeld
     aufgerufen, um ein neues Objekt von der Klasse Person zu erstellen.
	 Hierbei können alle nötigen Attribute eingegeben werden.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();								// ein Objekt der Klasse wird neu erzeugt
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);	// und an PersonEdit übergeben
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);					// falls PersonEdit mit OK bestätigt wird, so wird die neue Person in die Personenliste aufgenommen
        }
    }

    /***************************************************************************
    METHODENNAME:	handlePersonBuchung
    *//*!
     Handler für den Buchungs Button. Hiermit wird eine neue Buchung angelegt
     und die Daten der ausgewählten Person geladen.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handlePersonBuchung() {
    	Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
    	Buchung tempBuchung= new Buchung();

        if (selectedPerson != null) {
        	tempBuchung.setPersonID(selectedPerson.getPersonID());

            boolean okClicked = mainApp.showBuchungEditDialog(tempBuchung);
            if (okClicked) {
            	mainApp.getBuchungData().add(tempBuchung);
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a Person in the table.");

            alert.showAndWait();
        }
    }

    /***************************************************************************
    METHODENNAME:	handleEditPerson
    *//*!
    Handler für den Edit Button. Wird Edit angeklickt, so wird ein Dialogfeld
    aufgerufen, um die Attribute, der ausgewählten Person, verändern zu können.

     \param   void

     \return  void

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

    /***************************************************************************
    METHODENNAME:	SearchingResultDialog
    *//*!
    Öffnet ein Suchdialfeld, zur Eingabe eines Vornames, welches im binärem
    Baum gesucht und falls gefunden auch mit Details angezeigt wird.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void SearchingResultDialog(){
    boolean okClicked = mainApp.showSuchenFenster();
    if(okClicked)
    	{
    		mainApp.showSuchenFenster();
    	}
   }
}

/** @}*/ /*end of doxygen group*/