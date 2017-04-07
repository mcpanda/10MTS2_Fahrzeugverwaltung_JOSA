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

	ObservableList<String> filteredPersonBeschreibungBoxList= FXCollections.observableArrayList();
	ObservableList<String> filteredPersonLizenzBeschreibungBoxList= FXCollections.observableArrayList();
	ObservableList<String> filteredFahrzeugBeschreibungBoxList= FXCollections.observableArrayList();
	ObservableList<String> filteredFahrzeugTypBeschreibungBoxList= FXCollections.observableArrayList();


    String filterPerson= "";
    String filterFahrzeug= "";

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
    private ComboBox<String> personIDBox;
    @FXML
    private ComboBox<String> fahrzeugIDBox;
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
    	personIDBox.setItems(filteredPersonBeschreibungBoxList);

    	fahrzeugIDBox.setEditable(true);
    	fahrzeugIDBox.setItems(filteredFahrzeugBeschreibungBoxList);

    	fahrzeugtypBox.getItems().addAll("Fahrzeugtyp", "Motorrad", "Cityflitzer", "Langstrecke", "Kleintransporter", "LKW");
    	fahrzeugtypBox.setValue("Fahrzeugtyp");
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

    	if(code.isDigitKey() || code.isLetterKey() || code == KeyCode.SHIFT) {
    		filterPerson+= event.getText();
    	}

    	if (filterPerson.length() > 0) {
    		filteredPersonBeschreibungBoxList.clear();

			for(int i= 0; i < filteredPersonLizenzBeschreibungBoxList.size(); i++){
    			String vergleich= filteredPersonLizenzBeschreibungBoxList.get(i).toLowerCase();
    			if (vergleich.indexOf(filterPerson) > -1) {
        			filteredPersonBeschreibungBoxList.add(filteredPersonLizenzBeschreibungBoxList.get(i));
    			}
    	  	}
		}

    	if (code == KeyCode.ESCAPE) {
    		filterPerson= "";
    		personIDBox.setValue("");
    	}

    	if (code == KeyCode.ENTER) {
    		filterPerson= "";
    		personIDBox.setValue("");
    	}

    	if(filterPerson.length()== 0) {
    		filteredPersonBeschreibungBoxList.setAll(filteredPersonLizenzBeschreibungBoxList);
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

    	if(code.isDigitKey() || code.isLetterKey() || code == KeyCode.SHIFT) {
    		filterFahrzeug+= event.getText();
    	}

    	if (filterFahrzeug.length() > 0) {
    		filteredFahrzeugBeschreibungBoxList.clear();

			for(int i= 0; i < filteredFahrzeugTypBeschreibungBoxList.size(); i++){
    			String vergleich= filteredFahrzeugTypBeschreibungBoxList.get(i).toLowerCase();
    			if (vergleich.indexOf(filterFahrzeug) > -1) {
    				filteredFahrzeugBeschreibungBoxList.add(filteredFahrzeugTypBeschreibungBoxList.get(i));
    			}
    	  	}
		}

    	if (code == KeyCode.ESCAPE) {
    		filterFahrzeug= "";
    		fahrzeugIDBox.setValue("");
    	}

    	if (code == KeyCode.ENTER) {
    		filterFahrzeug= "";
    		fahrzeugIDBox.setValue("");
    	}

    	if(filterFahrzeug.length()== 0) {
    		filteredFahrzeugBeschreibungBoxList.setAll(filteredFahrzeugTypBeschreibungBoxList);
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

    	if(fahrzeugtypBox.getValue().equals("Fahrzeugtyp") || fahrzeugtypBox.getValue().equals("")) {
    		filteredFahrzeugTypBeschreibungBoxList.clear();
    		filteredPersonLizenzBeschreibungBoxList.clear();

    		for(Fahrzeug f : fahrzeugs) {
    			filteredFahrzeugTypBeschreibungBoxList.add(f.getFahrzeugBeschreibung());
    		}
    		for(Person p : persons) {
    			filteredPersonLizenzBeschreibungBoxList.add(p.getPersonBeschreibung());
    		}
    	} else {
    		filteredFahrzeugTypBeschreibungBoxList.clear();
    		filteredPersonLizenzBeschreibungBoxList.clear();

    		for(Fahrzeug f : fahrzeugs){
    			String vergleich= f.getFahrzeugtyp();
        		if (vergleich.equals(fahrzeugtypBox.getValue())) {
        			filteredFahrzeugTypBeschreibungBoxList.add(f.getFahrzeugBeschreibung());
        		}
    	  	}
    		for(Person p : persons) {
    			if(fahrzeugtypBox.getValue().equals("Motorrad")) {
    				if(p.getLizenz().indexOf("A") > 0) {
        				filteredPersonLizenzBeschreibungBoxList.add(p.getPersonBeschreibung());
    				}
    			}

    			if(fahrzeugtypBox.getValue().equals("Cityflitzer") || fahrzeugtypBox.getValue().equals("Langstrecke")) {
    				if(p.getLizenz().indexOf("B") > 0) {
        				filteredPersonLizenzBeschreibungBoxList.add(p.getPersonBeschreibung());
    				}
    			}
    			if(fahrzeugtypBox.getValue().equals("Kleintransporter") || fahrzeugtypBox.getValue().equals("LKW")) {
    				if(p.getLizenz().indexOf("C") > 0) {
        				filteredPersonLizenzBeschreibungBoxList.add(p.getPersonBeschreibung());
    				}
    			}
    		}
    	}

    	filteredFahrzeugBeschreibungBoxList.setAll(filteredFahrzeugTypBeschreibungBoxList);
    	filteredPersonBeschreibungBoxList.setAll(filteredPersonLizenzBeschreibungBoxList);
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

    public void setBuchung(Buchung buchung, List<Person> persons,  List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {
        this.buchung = buchung;

        if (buchung.getBuchungID() == 0) {
        	int temp= buchungs.get(buchungs.size()-1).getBuchungID() + 1;
        	buchungIDLabel.setText(Integer.toString(temp));
        } else {
        	buchungIDLabel.setText(Integer.toString(buchung.getBuchungID()));
        }

        for (Person p : persons) {
        	if (buchung.getPersonID() == p.getPersonID()) {
        		personIDBox.setValue(p.getPersonBeschreibung());
        	}
        }

        for (Fahrzeug f : fahrzeugs) {
        	if (buchung.getFahrzeugID() == f.getFahrzeugID()) {
        		fahrzeugIDBox.setValue(f.getFahrzeugBeschreibung());
        		fahrzeugtypBox.setValue(f.getFahrzeugtyp());
        	}
        }

        if (fahrzeugtypBox.getValue() == "Fahrzeugtyp") {
        	for(Person p : persons) {
        		if(buchung.getPersonID() == p.getPersonID()) {
        			if(p.getLizenz().indexOf("A") > 0) {
        				fahrzeugtypBox.setValue("Motorrad");
        			} else {
        				if(p.getLizenz().indexOf("B") > 0) {
            				fahrzeugtypBox.setValue("Cityflitzer");
            			} else {
            				if(p.getLizenz().indexOf("C") > 0) {
                				fahrzeugtypBox.setValue("LKW");
                			}
            			}
        			}
        		}
        	}
        }
        for(Person p : persons) {
			if(fahrzeugtypBox.getValue().equals("Motorrad")) {
				if(p.getLizenz().indexOf("A") > 0) {
    				filteredPersonLizenzBeschreibungBoxList.add(p.getPersonBeschreibung());
				}
			}

			if(fahrzeugtypBox.getValue().equals("Cityflitzer") || fahrzeugtypBox.getValue().equals("Langstrecke")) {
				if(p.getLizenz().indexOf("B") > 0) {
    				filteredPersonLizenzBeschreibungBoxList.add(p.getPersonBeschreibung());
				}
			}
			if(fahrzeugtypBox.getValue().equals("Kleintransporter") || fahrzeugtypBox.getValue().equals("LKW")) {
				if(p.getLizenz().indexOf("C") > 0) {
    				filteredPersonLizenzBeschreibungBoxList.add(p.getPersonBeschreibung());
				}
			}
		}

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
        if (isInputValid(mainApp.getPersonData(), mainApp.getFahrzeugData())) {

        	buchung.setBuchungID(Integer.parseInt(buchungIDLabel.getText()));

        	buchung.setPersonID(getIDfromBeschreibung(personIDBox.getEditor().getText()));
        	buchung.setFahrzeugID(getIDfromBeschreibung(fahrzeugIDBox.getEditor().getText()));
        	buchung.setLeihdauer(Integer.parseInt(leihdauerLabel.getText()));

            buchung.setAusleihdatum(DateUtil.parse(ausleihdatumField.getText()));
            buchung.setRueckgabedatum(DateUtil.parse(rueckgabedatumField.getText()));

            setAusgeliehen(buchung.getPersonID(), buchung.getFahrzeugID(), buchung.getRueckgabedatum());

            okClicked = true;
            dialogStage.close();
        }
    }

	/***************************************************************************

	METHODENNAME:	getIDfromBeschreibung

	BESCHREIBUNG:   Die Zahl im String, welche vor dem "-", wird zur�ckgegeben

	PARAMETER: 		String

	RETURN:			int

	***************************************************************************/

    public int getIDfromBeschreibung(String temp) {
    	int pos= 0;

    	pos= temp.indexOf("-")-1;

    	if (pos == -2) {
    		pos= Integer.parseInt(temp);
    	} else {
    		temp=temp.substring(0, pos);
        	pos= Integer.parseInt(temp);
    	}

    	return pos;
    }

	/***************************************************************************

	METHODENNAME:	setAusgeliehen

	BESCHREIBUNG:   Beim Klick auf [OK] wird verglichen, ob das heutige Datum
					kleiner oder gleich ist dem R�ckgabedatum. Abh�ngig davon,
					wird der Ausleihzustand des jeweiligen Fahrzeugs und der
					Person auf "Ja" oder "Nein" gesetzt.

	PARAMETER: 		int, int, LocalDate

	RETURN:			void

	***************************************************************************/

    public void setAusgeliehen(int personID, int fahrID, LocalDate rueckgabedatum) {

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

    private boolean isInputValid(List<Person> persons, List<Fahrzeug> fahrzeugs) {
        String errorMessage = "";
        int tempP= 0;
        int tempF= 0;

        for (int i= 0; i < filteredPersonBeschreibungBoxList.size(); i++) {
        	if (personIDBox.getEditor().getText().equals(filteredPersonBeschreibungBoxList.get(i))) {
        		tempP= 1;
        	}
        }

        if (tempP == 0) {
        	errorMessage += "Bitte passende Person auswaehlen!\n";
        }

        for(Person p : persons) {
        	if (personIDBox.getEditor().getText().equals(p.getPersonBeschreibung())) {
        		if(p.getAusgeliehen().equals("Ja")) {
        			errorMessage += "Diese Person leit bereits schon ein Fahrzeug aus!\n";
        		}
        	}
        }

        for (int i= 0; i < filteredFahrzeugBeschreibungBoxList.size(); i++) {
        	if (fahrzeugIDBox.getEditor().getText().equals(filteredFahrzeugBeschreibungBoxList.get(i))) {
        		tempF= 1;
        	}
        }

        if (tempF == 0) {
        	errorMessage += "Bitte passendes Fahrzeug auswaehlen!\n";
        }

        for(Fahrzeug f : fahrzeugs) {
        	if (fahrzeugIDBox.getEditor().getText().equals(f.getFahrzeugBeschreibung())) {
        		if(f.getAusgeliehen().equals("Ja")) {
        			errorMessage += "Dieses Fahrzeug wird bereits verliehen!\n";
        		}
        	}
        }

        if (Integer.parseInt(leihdauerLabel.getText()) < 1) {
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

        if(fahrzeugtypBox.getValue().equals("Fahrzeugtyp")) {
        	errorMessage += "Bitte einen Fahrzeugtyp ausw�hlen!\n";
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
     	days= days + 365 * years + 1;

     	leihdauerLabel.setText(Integer.toString(days));
     }
}