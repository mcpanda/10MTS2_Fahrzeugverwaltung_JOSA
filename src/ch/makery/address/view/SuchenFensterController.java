

package ch.makery.address.view;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;





public class SuchenFensterController {
		private MainApp mainApp;
		private Stage primaryStage;

		@FXML
	    private TextField firstNameField;
	    @FXML
	    private TextField lastNameField;
	    @FXML
	    private TextField personIDField;

	    private Stage dialogStage;
	    private Person person;


	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }

	// neue Objekt cozdat' 4tobi potom ego cravnit'
	    public void setPerson(Person person)
	    {
	    	Person pers = new Person();
	    	pers = person;
	        this.person = person;
	        firstNameField.setText(person.getFirstName());
	        lastNameField.setText(person.getLastName());
	        personIDField.setText(Integer.toString(person.getPersonID()));
	    }

//// funktioniert noch nicht =((
	    @FXML
	    private void handleAbrechnen() {
	        dialogStage.close();
	    }
//////////////////


	    @FXML
	    private void ResultKnopf(){
		 	   try {
		            // Load person overview.
		            FXMLLoader loader = new FXMLLoader();
		            loader.setLocation(MainApp.class.getResource("view/ErgebniseVonSuchen.fxml"));
		            AnchorPane page = (AnchorPane) loader.load();


		            	Stage SearchDialog = new Stage();
		            	SearchDialog.setTitle("Sie können jetzt die Ergebnisse des Suchen anschauen");
		            	SearchDialog.initModality(Modality.WINDOW_MODAL);
						SearchDialog.initOwner(primaryStage);
						Scene scene = new Scene(page);
		            	SearchDialog.setScene(scene);


		            	SearchDialog.showAndWait();

		        } catch (IOException e) {
		            e.printStackTrace();
		        }
	    }

}