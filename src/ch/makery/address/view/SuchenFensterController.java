/**************************************************************************************************/
/*! \file
  FILE         : $Source: SuchenFensterController.java $
  BESCHREIBUNG : Controller
                 Controller für das Suchfesnter mit binären Bäumen
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
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Node;
import ch.makery.address.model.Person;
import ch.makery.address.model.Tree;
import javafx.collections.ObservableList;

/***************************************************************************
CLASS:	SuchenFensterController
*//*!
 Die Klasse SuchenFensterController hat nur einen Standardkonstruktor.

***************************************************************************/

public class SuchenFensterController {

	private MainApp mainApp;
	private Stage dialogStage;

	/**************************************************************************/
	/*                                                                        */
	/* FXML Field Section                                                     */
	/*                                                                        */
	/**************************************************************************/

	@FXML
    private TextField firstNameField;

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
    private Label ZeitunterschiedLabel;

    /***************************************************************************
    METHODENNAME:	setDialogStage
    *//*!
     Gibt die Stage des Dialogfeldes an.

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
    METHODENNAME:	getPerson
    *//*!
     Liest die Eingabe aus dem Vornamentexfeld aus und gibt sie weiter.

     \param   void

     \return  String

    ***************************************************************************/

    @FXML
    private String getPerson()
    {
        return firstNameField.getText();
    }

    /***************************************************************************
    METHODENNAME:	find
    *//*!
     Sucht den eingegebenen String im binären Baum. Wird der passende Knoten
     im Baum gefunden, so wird dieser in die Anzeige weitergegeben.
     Ebenfalls wird hier der ZEitunterscheid zur linearen Suche ermittelt.

     \param   String

     \return  void

    ***************************************************************************/

	private void find (String temp) {
    	Tree theTree = new Tree();
    	Node tempNode= new Node(0, "firstName", "lastName", "street", 12345, "city");
    	ObservableList<Person> persons= mainApp.getPersonData();
    	for (Person p : persons) {
    		theTree.addNode(p.getPersonID(), p.getFirstName(), p.getLastName(), p.getStreet(), p.getPostalCode(), p.getCity());
    	}

    	long zeitLinear;
    	zeitLinear= -System.currentTimeMillis();
    	for (int i= 0; i < 10000; i++) {
    		for( Person p : persons) {
	    		if (temp.equals(p.getFirstName())) {
	    			showPersonDetails(tempNode);
	    			break;
	    		}
	    	}
    	}
    	zeitLinear= zeitLinear + System.currentTimeMillis();

    	long zeitBaum= -System.currentTimeMillis();
    	for (int i= 0; i < 10000; i++) {
    		showPersonDetails(theTree.findNode(temp));
    	}
    	zeitBaum= zeitBaum + System.currentTimeMillis();

    	ZeitunterschiedLabel.setText(Integer.toString((int)((zeitLinear - zeitBaum)))+ " [ms]");
    }

    /***************************************************************************
    METHODENNAME:	showPersonDetails
    *//*!
     Die Details eines Knotens werden angezeigt

     \param   Knoten (Node)

     \return  void

    ***************************************************************************/

    private void showPersonDetails(Node temp) {
        if (temp != null) {
            // Fill the labels with info from the person object.
        	personIDLabel.setText(Integer.toString(temp.getPersonID()));
            firstNameLabel.setText(temp.getFirstName());
            lastNameLabel.setText(temp.getLastName());
            streetLabel.setText(temp.getStreet());
            postalCodeLabel.setText(Integer.toString(temp.getPostalCode()));
            cityLabel.setText(temp.getCity());
        } else {
            // Person is null, remove all the text.
        	personIDLabel.setText("");
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
        }
    }

    /***************************************************************************
    METHODENNAME:	ResultKnopf
    *//*!
     eingegebene Person wird gesucht und wenn möglich angezeigt bzw. eine
     Fehlermeldung ausgegeben

	\param   Knoten (Node)

     \return  void

    ***************************************************************************/

    @FXML
    private void ResultKnopf(){
    	find(getPerson());
    	if (lastNameLabel.getText().length() == 0) {
    		// Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Fehler - unzulässige Eingabe");
            alert.setContentText("Gesuchte Person ist nicht vorhanden");

            alert.showAndWait();
    	}
    }

    /***************************************************************************
    METHODENNAME:	handleAbbrechen
    *//*!
     Schließt Suchfeld

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleAbbrechen() {
        dialogStage.close();
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
    	Buchung tempBuchung= new Buchung();
        if (personIDLabel.getText() != null && personIDLabel.getText().indexOf("Label") < 0) {
        	tempBuchung.setPersonID(Integer.parseInt(personIDLabel.getText()));

            boolean okClicked = mainApp.showBuchungEditDialog(tempBuchung);
            if (okClicked) {
            	mainApp.getBuchungData().add(tempBuchung);
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Person");
            alert.setHeaderText("No Person was found");
            alert.setContentText("Please find a Person first.");

            alert.showAndWait();
        }
    }

 }

/** @}*/ /*end of doxygen group*/