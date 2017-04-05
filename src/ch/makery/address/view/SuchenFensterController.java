

package ch.makery.address.view;

import javafx.collections.ObservableList;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ch.makery.address.MainApp;
import ch.makery.address.model.Node;
import ch.makery.address.model.Person;
import ch.makery.address.model.Tree;

public class SuchenFensterController {

		private MainApp mainApp;
		private Stage dialogStage;
//	    private boolean okClicked = false;


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


	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }




	// neue Objekt cozdat' 4tobi potom ego cravnit'
	    /* 	    private void setPerson(String temp)
	    {
  	Person pers = new Person();
	    	pers = temp;
	        this.person = temp;
*/
	    @FXML
	    private String setPerson()
	    {
	        return firstNameField.getText();
	    }


		private void find (String temp) {
	    	Tree theTree = new Tree();
	    	ObservableList<Person> persons= mainApp.getPersonData();
	    	for (Person p : persons) {
	    		theTree.addNode(p.getPersonID(), p.getFirstName(), p.getLastName(), p.getStreet(), p.getPostalCode(), p.getCity());
	    	}

	    	showPersonDetails(theTree.findNode(temp));
//	    			setPerson(temp)));

	    }

//// funktioniert noch nicht =((
	    @FXML
	    private void handleAbrechnen() {
	        dialogStage.close();
	    }
//////////////////

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
	    	find(setPerson());

	    }




		public void setMainApp(MainApp mainApp) {
			// TODO Auto-generated method stub
			this.mainApp = mainApp;
		}


 }

