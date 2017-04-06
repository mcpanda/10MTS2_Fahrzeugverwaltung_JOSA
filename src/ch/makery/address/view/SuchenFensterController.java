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
import ch.makery.address.model.Node;
import ch.makery.address.model.Person;
import ch.makery.address.model.Tree;
import javafx.collections.ObservableList;

public class SuchenFensterController {

		private MainApp mainApp;
		private Stage dialogStage;
//	    private boolean AbbrechenClicked = false;

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

	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }
		public void setMainApp(MainApp mainApp) {
			this.mainApp = mainApp;
		}

	    @FXML
	    private String getPerson()
	    {
	        return firstNameField.getText();
	    }

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
	    @FXML
	    private void handleAbbrechen() {
	        dialogStage.close();
//	        AbbrechenClicked = true;
	    }

//	    public boolean isAbbrechenClicked() {
//	        return AbbrechenClicked;
//	    }

 }