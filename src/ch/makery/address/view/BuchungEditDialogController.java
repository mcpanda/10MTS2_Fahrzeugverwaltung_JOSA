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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.time.LocalDate;
import java.util.List;

import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.model.Person;
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
	ObservableList<Integer> filteredPersonLizenzIDBoxList= FXCollections.observableArrayList();
	ObservableList<Integer> filteredFahrzeugIDBoxList= FXCollections.observableArrayList();
	ObservableList<Integer> filteredFahrzeugTypIDBoxList= FXCollections.observableArrayList();

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
    private Label lastnameLabel;
	@FXML
    private Label herstellerLabel;
    @FXML
    private TextField personIDField;
    @FXML
    private TextField fahrzeugIDField;
    @FXML
    private TextField ausleihdatumField;
    @FXML
    private TextField rueckgabedatumField;
    @FXML
    private Label leihdauerLabel;

    @FXML
    private ComboBox<Integer> personIDBox;
    @FXML
    private ComboBox<Integer> fahrzeugIDBox;
    @FXML
    private ChoiceBox<String> fahrzeugtypBox;

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

    	fahrzeugtypBox.getItems().addAll("Alle", "Motorrad", "Cityflitzer", "Langstrecke", "Kleintransporter", "LKW");
    	fahrzeugtypBox.setValue("Alle");
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

	/***************************************************************************

	METHODENNAME:	handlerComboBoxList

	BESCHREIBUNG:   L�dt die IDs der Personen und Fahrzeuge in
					die Observablelisten ein

	PARAMETER: 		dialogStage

	RETURN:			void

	***************************************************************************/

    public void handlerComboBoxList() {

	  	filteredPersonIDBoxList.setAll(mainApp.getPersonIDList());
	  	filteredFahrzeugIDBoxList.setAll(mainApp.getFahrzeugIDList());
//	  	fahrzeugtypBox.getItems().addAll("Motorrad", "Cityflitzer", "Langstrecke", "Kleintransporter", "LKW");
    }

    /***************************************************************************

	METHODENNAME:	AusleihdauerBerechnung

	BESCHREIBUNG:   Berechnung der Ausleihdauer

	PARAMETER: 		LocalDate, LocalDate

	RETURN:			int

	***************************************************************************/

    public void AusleihdauerBerechnung() {

    	LocalDate datum1= DateUtil.parse(ausleihdatumField.getText());
    	LocalDate datum2= DateUtil.parse(rueckgabedatumField.getText());

    	int years= datum2.getYear() - datum1.getYear();
    	int days= datum2.getDayOfYear() - datum1.getDayOfYear();
    	days= days + 365 * years;

	    leihdauerLabel.setText(Integer.toString(days));
    }


	/***************************************************************************

	METHODENNAME:	handleAutoCompletePerson

	BESCHREIBUNG:   Sortiert ComboBox nach Tastatureingabe aus

	PARAMETER: 		KeyEvent

	RETURN:			void

	***************************************************************************/

    public void handleAutoCompletePerson (KeyEvent event) {

    	AutoCompleteFahrzeugtyp(mainApp.getPersonData(), mainApp.getFahrzeugData());

    	personIDBox.show();

    	KeyCode code= event.getCode();

    	if((code == KeyCode.BACK_SPACE) && (filterPerson.length() > 0)) {
    		filterPerson= filterPerson.substring(0, filterPerson.length()-1);
    	}

    	if(code.isDigitKey()) {
    		filterPerson+= event.getText();
    	}

    	if (filterPerson.length() > 0) {
    		filteredPersonIDBoxList.clear();

			for(int i= 0; i < filteredPersonLizenzIDBoxList.size(); i++){
    			String vergleich= Integer.toString(filteredPersonLizenzIDBoxList.get(i));
    			if(vergleich.length() < filterPerson.length()) {

    			} else {
    				vergleich= vergleich.substring(0, filterPerson.length());
            		if (vergleich.equals(filterPerson)) {
            			int temp= filteredPersonLizenzIDBoxList.get(i);
            			filteredPersonIDBoxList.add(temp);
            		}
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
    		filteredPersonIDBoxList.setAll(filteredPersonLizenzIDBoxList);
//    		personIDBox.hide();
    	}
    }

    /***************************************************************************

	METHODENNAME:	handleAutoCompleteFahrzeug

	BESCHREIBUNG:   Sortiert ComboBox nach Tastatureingabe aus

	PARAMETER: 		KeyEvent

	RETURN:			void

	***************************************************************************/

    public void handleAutoCompleteFahrzeug (KeyEvent event) {

    	AutoCompleteFahrzeugtyp(mainApp.getPersonData(), mainApp.getFahrzeugData());

    	fahrzeugIDBox.show();

    	KeyCode code= event.getCode();

    	if((code == KeyCode.BACK_SPACE) && (filterFahrzeug.length() > 0)) {
    		filterFahrzeug= filterFahrzeug.substring(0, filterFahrzeug.length()-1);
    	}

    	if(code.isDigitKey()) {
    		filterFahrzeug+= event.getText();
    	}

    	if (filterFahrzeug.length() > 0) {
    		filteredFahrzeugIDBoxList.clear();

			for(int i= 0; i < filteredFahrzeugTypIDBoxList.size(); i++){
    			String vergleich= Integer.toString(filteredFahrzeugTypIDBoxList.get(i));
    			if (vergleich.length() < filterFahrzeug.length()) {

    			} else {
    				vergleich= vergleich.substring(0, filterFahrzeug.length());
    				if (vergleich.equals(filterFahrzeug)) {
            			int temp= filteredFahrzeugTypIDBoxList.get(i);
            			filteredFahrzeugIDBoxList.add(temp);
            		}
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
    		filteredFahrzeugIDBoxList.setAll(filteredFahrzeugTypIDBoxList);
    	}
    }

    /***************************************************************************

	METHODENNAME:	AutoCompleteFahrzeugtyp

	BESCHREIBUNG:   Filtert FahrzeugComboBox nach Fahrzeugtypauswahl und
					PersonenComboBox nach Fahrerlaubnis aus

	PARAMETER: 		List<Person>, List<Fahrzeug>

	RETURN:			void

	***************************************************************************/

    public void AutoCompleteFahrzeugtyp(List<Person> persons, List<Fahrzeug> fahrzeugs) {

    	if(fahrzeugtypBox.getValue().equals("Alle") || fahrzeugtypBox.getValue().equals("")) {
    		filteredFahrzeugTypIDBoxList.clear();
    		filteredPersonLizenzIDBoxList.clear();

    		for(Fahrzeug f : fahrzeugs) {
    			filteredFahrzeugTypIDBoxList.add(f.getFahrzeugID());
    		}
    		for(Person p : persons) {
    			filteredPersonLizenzIDBoxList.add(p.getPersonID());
    		}
    	} else {
    		filteredFahrzeugTypIDBoxList.clear();
    		filteredPersonLizenzIDBoxList.clear();

    		for(Fahrzeug f : fahrzeugs){
    			String vergleich= f.getFahrzeugtyp();
        		if (vergleich.equals(fahrzeugtypBox.getValue())) {
        			int temp= f.getFahrzeugID();
        			filteredFahrzeugTypIDBoxList.add(temp);
        		}
    	  	}
    		for(Person p : persons) {
    			if(fahrzeugtypBox.getValue().equals("Motorrad")) {
    				int temp= p.getPersonID();
    				filteredPersonLizenzIDBoxList.add(temp);
    			}

    			if(fahrzeugtypBox.getValue().equals("Cityflitzer") || fahrzeugtypBox.getValue().equals("Langstrecke")) {
    				if(p.getLizenz().equals("Klasse B") || p.getLizenz().equals("Klasse C")) {
    					int temp= p.getPersonID();
    					filteredPersonLizenzIDBoxList.add(temp);
    				}
    			}
    			if(fahrzeugtypBox.getValue().equals("Kleintransporter") || fahrzeugtypBox.getValue().equals("LKW")) {
    				if(p.getLizenz().equals("Klasse C")) {
    					int temp= p.getPersonID();
    					filteredPersonLizenzIDBoxList.add(temp);
    				}
    			}
    		}
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

    public void setBuchung(Buchung buchung, List<Person> persons,  List<Fahrzeug> fahrzeugs) {
        this.buchung = buchung;

        if (buchung.getBuchungID() == 0) {
        	buchungIDLabel.setText(Integer.toString(MainApp.counterBuchung));
        } else {
        	buchungIDLabel.setText(Integer.toString(buchung.getBuchungID()));
        }

        personIDBox.setValue(buchung.getPersonID());
        fahrzeugIDBox.setValue(buchung.getFahrzeugID());
        fahrzeugtypBox.setValue(buchung.getFahrzeugtyp());

        ausleihdatumField.setText(DateUtil.format(buchung.getAusleihdatum()));
        ausleihdatumField.setPromptText("dd.mm.yyyy");
        rueckgabedatumField.setText(DateUtil.format(buchung.getRueckgabedatum()));
        rueckgabedatumField.setPromptText("dd.mm.yyyy");

        AusleihdauerBerechnung();
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

	BESCHREIBUNG:   handler f�r OK.
					Dieser handler wird ausgef�hrt, wenn [OK] geklickt wurde.
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
        	buchung.setLeihdauer(Integer.parseInt(leihdauerLabel.getText()));
        	buchung.setFahrzeugtyp(fahrzeugtypBox.getValue());

            buchung.setAusleihdatum(DateUtil.parse(ausleihdatumField.getText()));
            buchung.setRueckgabedatum(DateUtil.parse(rueckgabedatumField.getText()));

            setAusgeliehen(buchung.getPersonID(), buchung.getFahrzeugID(), buchung.getRueckgabedatum());

            okClicked = true;
            dialogStage.close();
        }
    }

	/***************************************************************************

	METHODENNAME:	setAusgeliehen

	BESCHREIBUNG:   Beim Klick auf [OK] wird verglichen, ob das heutige Datum
					kleiner oder gleich ist dem R�ckgabedatum. Abh�ngig davon,
					wird der AUsleihzustand des jeweiligen Fahrzeugs und der
					Person auf "Ja" oder "Nein" gesetzt.

	PARAMETER: 		int, int, LocalDate

	RETURN:			void

	***************************************************************************/

    public void setAusgeliehen(int personID, int fahrID, LocalDate rueckgabedatum) {

    	if (LocalDate.now().compareTo(rueckgabedatum) < 1) {

    	}

    	ObservableList<Person> personData = mainApp.getPersonData();
    	for(int i = 0; i < personData.size(); i++){
    		if(personID == personData.get(i).getPersonID()) {
    			if (LocalDate.now().compareTo(rueckgabedatum) < 1) {
    				personData.get(i).setAusgeliehen("Ja");
    	    	} else {
    	    		personData.get(i).setAusgeliehen("Nein");
    	    	}

    		}
    	}

    	ObservableList<Fahrzeug> fahrzeugData = mainApp.getFahrzeugData();
    	for(int i = 0; i < fahrzeugData.size(); i++){
    		if(fahrID == fahrzeugData.get(i).getFahrzeugID()) {
    			if (LocalDate.now().compareTo(rueckgabedatum) < 1) {
    				fahrzeugData.get(i).setAusgeliehen("Ja");
    			} else {
    				fahrzeugData.get(i).setAusgeliehen("Nein");
    			}

    		}
    	}

    }

	/***************************************************************************

	METHODENNAME:	handleCancel

	BESCHREIBUNG:   handler f�r Cancel.
					Dieser handler wird ausgef�hrt, wenn [Cancel] geklickt wurde.
					Dialogfenster wird geschlo�en, ohne neue Buchungsdaten
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

	BESCHREIBUNG:   �berpr�fung der eingegeben Daten. Sollten nicht konforme
					Daten vorhanden sein, so wird eine Fehlermeldung ausgegeben.

	PARAMETER: 		void

	RETURN:			Boolean

	***************************************************************************/

    private boolean isInputValid() {
        String errorMessage = "";

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

        if (Integer.parseInt(leihdauerLabel.getText()) < 0) {
            errorMessage += "No valid Leihdauer!\n";
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
}

/*
-------

public List<? extends Comparable> sortieren(List<? extends Comparable> list) {




}*/