package ch.makery.address.view;



/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.util.DateUtil;

/**************************************************************************/
/*                                                                        */
/* Class BuchungEditDialogController                                      */
/*                                                                        */
/**************************************************************************/

public class BuchungEditDialogController {

	// Reference to the main application.
    private MainApp mainApp;

	ObservableList<Integer> filteredPersonIDBoxList= FXCollections.observableArrayList();
	ObservableList<Integer> filteredFahrzeugIDBoxList= FXCollections.observableArrayList();

    String filterPerson= "";
    String filterFahrzeug= "";

    int temp;

    Stage dialogStage;
    private Buchung buchung;
    private boolean okClicked = false;

	/**************************************************************************/
	/*                                                                        */
	/* FXML Field Section                                                     */
	/*                                                                        */
	/**************************************************************************/

	@FXML
    private Label buchungIDLabel;
    @FXML
    private TextField personIDField;
    @FXML
    private TextField fahrzeugIDField;
    @FXML
    private TextField ausleihdatumField;
    @FXML
    private TextField rueckgabedatumField;
    @FXML
    private TextField leihdauerField;

    @FXML
    private ComboBox<Integer> personIDBox;
    @FXML
    private ComboBox<Integer> fahrzeugIDBox;

	/**************************************************************************/
	/*                                                                        */
	/* Local Operation Section                                                */
	/*                                                                        */
	/**************************************************************************/

	/***************************************************************************

	METHODENNAME:	initialize

	BESCHREIBUNG:   Initialisiert die Controller Klasse. Diese Methode wird
					automatisch aufgerufen, nachdem die fxml Datei
					geladen wurde

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void initialize() {

    	personIDBox.setEditable(true);
    	//personIDBox.setValue(0);
    	personIDBox.setItems(filteredPersonIDBoxList);

    	fahrzeugIDBox.setEditable(true);
    	//fahrzeugIDBox.setValue(0);
    	fahrzeugIDBox.setItems(filteredFahrzeugIDBoxList);
    }

	/***************************************************************************

	METHODENNAME:	handlerComboBoxList

	BESCHREIBUNG:   Lädt die IDs der Personen und Fahrzeuge in
					die Observablelisten ein

	PARAMETER: 		dialogStage

	RETURN:			void

	***************************************************************************/

    public void handlerComboBoxList() {

	  	filteredPersonIDBoxList.setAll(mainApp.getPersonIDList());
	  	filteredFahrzeugIDBoxList.setAll(mainApp.getFahrzeugIDList());
    }

	/***************************************************************************

	METHODENNAME:	handleAutoCompletePerson

	BESCHREIBUNG:   Sortiert ComboBox nach Tastatureingabe aus

	PARAMETER: 		KeyEvent

	RETURN:			void

	***************************************************************************/

    public void handleAutoCompletePerson (KeyEvent event) {
    	personIDBox.show();

    	KeyCode code= event.getCode();

    	if((code == KeyCode.BACK_SPACE) && (filterPerson.length() > 0)) {
    		filterPerson= filterPerson.substring(0, filterPerson.length()-1);
    	} else {
    		filterPerson+= event.getText();
    	}

    	if (filterPerson.length() > 0) {
    		filteredPersonIDBoxList.clear();

			for(int i = mainApp.getPersonIDList().size()-1; i > -1 ; i--){
    			String vergleich= Integer.toString(mainApp.getPersonIDList().get(i));
    			vergleich= vergleich.substring(0, filterPerson.length());
        		if (vergleich.equals(filterPerson)) {
        			int temp= mainApp.getPersonIDList().get(i);
        			filteredPersonIDBoxList.add(temp);
        		}
    	  	}
		}

    	if (code == KeyCode.ESCAPE) {
    		filterPerson= "";
    		personIDBox.setValue(0);
    	}

    	if (code == KeyCode.ENTER) {
    		filterPerson= "";
    		personIDBox.setValue(0);
    	}

    	if(filterPerson.length()== 0) {
    		filteredPersonIDBoxList.setAll(mainApp.getPersonIDList());
    		personIDBox.hide();
    	}
    }

    /***************************************************************************

	METHODENNAME:	handleAutoCompleteFahrzeug

	BESCHREIBUNG:   Sortiert ComboBox nach Tastatureingabe aus

	PARAMETER: 		KeyEvent

	RETURN:			void

	***************************************************************************/

    public void handleAutoCompleteFahrzeug (KeyEvent event) {
    	fahrzeugIDBox.show();

    	KeyCode code= event.getCode();

    	if((code == KeyCode.BACK_SPACE) && (filterFahrzeug.length() > 0)) {
    		filterFahrzeug= filterFahrzeug.substring(0, filterFahrzeug.length()-1);
    	} else {
    		filterFahrzeug+= event.getText();
    	}

    	if (filterFahrzeug.length() > 0) {
    		filteredFahrzeugIDBoxList.clear();

			for(int i = mainApp.getFahrzeugIDList().size()-1; i > -1 ; i--){
    			String vergleich= Integer.toString(mainApp.getFahrzeugIDList().get(i));
    			vergleich= vergleich.substring(0, filterFahrzeug.length());
        		if (vergleich.equals(filterFahrzeug)) {
        			int temp= mainApp.getFahrzeugIDList().get(i);
        			filteredFahrzeugIDBoxList.add(temp);
        		}
    	  	}
		}

    	if (code == KeyCode.ESCAPE) {
    		filterFahrzeug= "";
    		fahrzeugIDBox.setValue(0);
    	}

    	if (code == KeyCode.ENTER) {
    		filterFahrzeug= "";
    		fahrzeugIDBox.setValue(0);
    	}

    	if(filterFahrzeug.length()== 0) {
    		filteredFahrzeugIDBoxList.setAll(mainApp.getFahrzeugIDList());
    		fahrzeugIDBox.hide();
    	}
    }

	/***************************************************************************

	METHODENNAME:	setDialogStage

	BESCHREIBUNG:   Gibt die Stage des Dialagfeldes an.

	PARAMETER: 		dialogStage

	RETURN:			void

	***************************************************************************/

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	/***************************************************************************

	METHODENNAME:	setBuchung

	BESCHREIBUNG:   Definiert die Buchung, welche bearbeitet werden soll.

	PARAMETER: 		Buchung

	RETURN:			void

	***************************************************************************/

    public void setBuchung(Buchung buchung) {
        this.buchung = buchung;

        if (buchung.getBuchungID() == 0) {
        	buchungIDLabel.setText(Integer.toString(MainApp.counterBuchung));
        } else {
        	buchungIDLabel.setText(Integer.toString(buchung.getBuchungID()));
        }

        System.out.println("get: "+ buchung.getBuchungID());

        personIDBox.setValue(buchung.getPersonID());
        fahrzeugIDBox.setValue(buchung.getFahrzeugID());
        leihdauerField.setText(Integer.toString(buchung.getLeihdauer()));

        ausleihdatumField.setText(DateUtil.format(buchung.getAusleihdatum()));
        ausleihdatumField.setPromptText("dd.mm.yyyy");
        rueckgabedatumField.setText(DateUtil.format(buchung.getRueckgabedatum()));
        rueckgabedatumField.setPromptText("dd.mm.yyyy");
    }

	/***************************************************************************

	METHODENNAME:	isOkClicked

	BESCHREIBUNG:   Gibt ein TRUE aus, wenn [OK] geklickt wurde.

	PARAMETER: 		void

	RETURN:			Boolean

	***************************************************************************/

    public boolean isOkClicked() {
        return okClicked;
    }

	/***************************************************************************

	METHODENNAME:	handleOk

	BESCHREIBUNG:   handler für OK.
					Dieser handler wird ausgeführt, wenn [OK] geklickt wurde.
					Neue Buchungsdaten werden gespeichert.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleOk() {
        if (isInputValid()) {

        	buchung.setBuchungID(Integer.parseInt(buchungIDLabel.getText()));
        	buchung.setPersonID(Integer.parseInt(personIDBox.getEditor().getText()));
        	buchung.setFahrzeugID(Integer.parseInt(fahrzeugIDBox.getEditor().getText()));
        	buchung.setLeihdauer(Integer.parseInt(leihdauerField.getText()));

            buchung.setAusleihdatum(DateUtil.parse(ausleihdatumField.getText()));
            buchung.setRueckgabedatum(DateUtil.parse(rueckgabedatumField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

	/***************************************************************************

	METHODENNAME:	handleCancel

	BESCHREIBUNG:   handler für Cancel.
					Dieser handler wird ausgeführt, wenn [Cancel] geklickt wurde.
					Dialogfenster wird geschloßen, ohne neue Buchungsdaten
					zu speichern.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

	/***************************************************************************

	METHODENNAME:	isInputValid

	BESCHREIBUNG:   Überprüfung der eingegeben Daten. Sollten nicht konforme
					Daten vorhanden sein, so wird eine Fehlermeldung ausgegeben.

	PARAMETER: 		void

	RETURN:			Boolean

	***************************************************************************/

    private boolean isInputValid() {
        String errorMessage = "";

//        if (buchungIDField.getText() == null || buchungIDField.getText().length() == 0) {
//            errorMessage += "No valid BuchungID!\n";
//        } else {
//            // try to parse the buchungID into an int.
//            try {
//                Integer.parseInt(buchungIDField.getText());
//            } catch (NumberFormatException e) {
//                errorMessage += "No valid buchungID (must be an integer)!\n";
//            }
//        }

        if (personIDBox.getEditor().getText() == null || personIDBox.getEditor().getText().length() == 0) {
            errorMessage += "No valid personID!\n";
        } else {
            // try to parse the personID into an int.
            try {
                Integer.parseInt(personIDBox.getEditor().getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid personID (must be an integer)!\n";
            }
        }

        if (fahrzeugIDBox.getEditor().getText() == null || fahrzeugIDBox.getEditor().getText().length() == 0) {
            errorMessage += "No valid fahrzeugID!\n";
        } else {
            // try to parse the fahrzeugID into an int.
            try {
                Integer.parseInt(fahrzeugIDBox.getEditor().getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid fahrzeugID (must be an integer)!\n";
            }
        }

        if (leihdauerField.getText() == null || leihdauerField.getText().length() == 0) {
            errorMessage += "No valid Leihdauer!\n";
        } else {
            // try to parse the leihdauer into an int.
            try {
                Integer.parseInt(leihdauerField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Leihdauer (must be an integer)!\n";
            }
        }

        if (ausleihdatumField.getText() == null || ausleihdatumField.getText().length() == 0) {
            errorMessage += "No valid Ausleihdatum!\n";
        } else {
            if (!DateUtil.validDate(ausleihdatumField.getText())) {
                errorMessage += "No valid Ausleihdatum. Use the format dd.mm.yyyy!\n";
            }
        }

        if (rueckgabedatumField.getText() == null || rueckgabedatumField.getText().length() == 0) {
            errorMessage += "No valid Rueckgabedatum!\n";
        } else {
            if (!DateUtil.validDate(rueckgabedatumField.getText())) {
                errorMessage += "No valid Rueckgabedatum. Use the format dd.mm.yyyy!\n";
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

	/***************************************************************************

	METHODENNAME:	setMainApp

	BESCHREIBUNG:   Is called by the main application to give a reference back
					to itself.

	PARAMETER: 		mainApp

	RETURN:			void

	***************************************************************************/

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}

/*
-------

public List<? extends Comparable> sortieren(List<? extends Comparable> list) {




}*/