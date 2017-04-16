/**************************************************************************************************/
/*! \file
  FILE         : $Source: BuchungEditDialogController.java $
  BESCHREIBUNG : Controller
                 Controller f�r das Dialogfeld zur Buchungsbearbeitung.
                 Hierzu z�hlt New, Edit, Delete.
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
import java.time.LocalDate;
import java.util.List;
import ch.makery.address.MainApp;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

/***************************************************************************
CLASS:	BuchungEditDialogController
*//*!
 Die Klasse BuchungEditDialogController hat nur einen Standardkonstruktor.

***************************************************************************/

public class BuchungEditDialogController {

	// Reference to the main application.
    private MainApp mainApp;

    ObservableList<String> personList= FXCollections.observableArrayList();									// ObservableList f�r die vorhandenen Personen im System
	ObservableList<String> personBeschreibungBoxList= FXCollections.observableArrayList();					// ObservableList f�r die Filterung bei Tastatureingabe
	ObservableList<String> filteredFahrzeugBeschreibungBoxList= FXCollections.observableArrayList();		// ObservableList f�r die Fileterung bei Tastatureingabe
	ObservableList<String> filteredFahrzeugTypBeschreibungBoxList= FXCollections.observableArrayList();		// ObservableList f�r die Fahrzeuge, abh�ngig von der Auswahl des Fahrzeugtypes
	ObservableList<String> filteredFahrzeugTypBoxList= FXCollections.observableArrayList();					// ObservableList f�r die Fahrzeugtypen, abh�ngig von der Auswahl der Person und derer Lizenz

    static private String filterPerson= "";		// String f�r die Speicherung der Tastatureingabe bei der personIDBox.
    static private String filterFahrzeug= "";	// String f�r die Speicherung der Tastatureingabe bei der fahrzeugIDBox.

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
    private ComboBox<String> fahrzeugtypBox;

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

    	personIDBox.setEditable(true);							// ComboBox bekomtm damit eine Textfield zur Eingabe
    	personIDBox.setItems(personBeschreibungBoxList);		// trage zur Auswahlliste alle Personenbeschreibungen aus der peronBeschreibungBoxList ein

    	fahrzeugtypBox.setItems(filteredFahrzeugTypBoxList);	// trage zur Auswahlliste alle Fahrzeugtypen aus der filteredFahrzeugTypBoxList ein
    	fahrzeugtypBox.setValue("Bitte Person ausw�hlen.");		// setze Anzeige auf "Bitte .."

    	// f�ge einen Listener hinzu, welcher die Fahrzeugtypen anpasst, abh�ngig von der Auswahl der Person
    	personIDBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setFahrzeugTypList(getIDfromBeschreibung(newValue)));

    	fahrzeugIDBox.setEditable(true);					// ComboBox bekomtm damit eine Textfield zur Eingabe
    	fahrzeugIDBox.setItems(filteredFahrzeugBeschreibungBoxList);	// trage zur Auswahlliste alle Fahrzeugbeschreibungen aus der filteredFahrzeugBeschreibungBoxList ein

    	// f�ge einen Listener hinzu, welcher die Fahrzeugbeschreibungen anpasst, abh�ngig von der Auswahl des Fahrzeugtypes
    	fahrzeugtypBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> AutoCompleteFahrzeug(newValue));
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
    METHODENNAME:	setPersonList
    *//*!
     Setzt die Personenliste gem�� aller Personen im System

     \param   List<Person>

     \return  void

    ***************************************************************************/

    public void setPersonList(List<Person> persons) {

    	personList.clear();		// zuerst werden alle Personeneintr�ge gel�scht, um etwaige mehrfachaufnahme zu verhindern

    	for(Person p : persons) {
    		personList.add(p.getPersonBeschreibung());		// f�r alle Personen aus der ObservableList persons, werden die Personenbeschreibung aufgenommen
		}

    	personBeschreibungBoxList.setAll(personList);		// kopiere die personList
    }

    /***************************************************************************
    METHODENNAME:	setFahrzeugTypList
    *//*!
     Erzeugt die Eintr�ge in der ComboBox fahrzuegtypBox, abh�ngig von einer
     PersonenID

     \param   int

     \return  void

    ***************************************************************************/

    public void setFahrzeugTypList(int personID) {

    	ObservableList<Person> persons= FXCollections.observableArrayList();	// erstelle eine ObservableList von der Klasse Person
    	persons.setAll(mainApp.getPersonData());								// Kopiere alle Personen im System

    	if(personID == 0) {														// Falls die PersonenID 0 ist, so werden die Eintr�ge in der FahrzeugListe und der FahrzeugtypListe geleert und zur Auswahl einer Person aufgefordert
    		fahrzeugtypBox.setValue("Bitte Person ausw�hlen");
    		filteredFahrzeugTypBoxList.clear();
    		filteredFahrzeugTypBeschreibungBoxList.clear();
    	} else {																// andernfalls
    		filteredFahrzeugTypBeschreibungBoxList.clear();						// l�sche alle EIntr�ge in den Listen, um eine etwaige mehrfachaufnahme zu verhindern
    		filteredFahrzeugTypBoxList.clear();
    		for(Person p : persons) {											// gehe alle Personen durch
    			if(p.getPersonID() == personID) {								// wenn die personenID mit dem �bergabeparameter �bereinstimmt
    				if(p.getLizenz().indexOf("A") > 0) {						// wenn in der Lizenz der Person ein A vorkommt, dann:
    					filteredFahrzeugTypBoxList.add("Motorrad");				// nimm Motorrad in die FahrzeugTypListe auf
    					fahrzeugtypBox.setValue("Bitte Fahrzeugtyp ausw�hlen");	// fordere zur Fahrzeugtypauswahl auf
    				}
    				if(p.getLizenz().indexOf("B") > 0) {						// wenn in der Lizenz der Person ein B vorkommt, dann:
    					filteredFahrzeugTypBoxList.add("Cityflitzer");			// nimm Cityflitzer in die FahrzeugTypListe auf
    					filteredFahrzeugTypBoxList.add("Langstrecke");			// nimm Langstrecke in die FahrzeugTypListe auf
    					fahrzeugtypBox.setValue("Bitte Fahrzeugtyp ausw�hlen");	// fordere zur Fahrzeugtypauswahl auf
    				}
    				if(p.getLizenz().indexOf("C") > 0) {						// wenn in der Lizenz der Person ein C vorkommt, dann:
    					filteredFahrzeugTypBoxList.add("Kleintransporter");		// nimm Kleintransporter in die FahrzeugTypListe auf
    					filteredFahrzeugTypBoxList.add("LKW");					// nimm LKW in die FahrzeugTypListe auf
    					fahrzeugtypBox.setValue("Bitte Fahrzeugtyp ausw�hlen");	// fordere zur Fahrzeugtypauswahl auf
    				}
    			}
    		}
    	}
    }

    /***************************************************************************
    METHODENNAME:	AutoCompleteFahrzeug
    *//*!
     Erzeugt die Eintr�ge in der ComboBox fahrzuegIDBox, abh�ngig von einem
     fahrzeugtyp

     \param   String

     \return  void

    ***************************************************************************/

    public void AutoCompleteFahrzeug(String fahrzeugtyp) {

    	ObservableList<Fahrzeug> fahrzeugs= FXCollections.observableArrayList();		// erstelle eine ObservableList
    	fahrzeugs.setAll(mainApp.getFahrzeugData());									// kopiere alle vorhanden Fahrzeuge im System

    	if (fahrzeugtyp != null) {									// wenn der fahrzeugtyp nicht null ist, dann
    		if(fahrzeugtyp.indexOf("Bitte") > 0) {					// wenn der fahrzeugtyp ein Bitte enth�kt, dann:
        		filteredFahrzeugTypBeschreibungBoxList.clear();		// l�sche alle Eintr�ge in der filteredFahrzeugTypBeschreibungBoxList
        	} else {												// andernfalls:
        		filteredFahrzeugTypBeschreibungBoxList.clear();		// l�sche alle Eintr�ge in der filteredFahrzeugTypBeschreibungBoxList, um etwaige mehrfachaufnahme zu verhindern
        		fahrzeugIDBox.setValue("");
        		for(Fahrzeug f : fahrzeugs){						// gehe alle Fahrzeuge durch
        			String vergleich= f.getFahrzeugtyp();
            		if (vergleich.equals(fahrzeugtyp)) {			// wenn der FahrzeugTyp eines Fahrzeuges mit dem �bergabeparameter �bereinstimmt, dann:
            			filteredFahrzeugTypBeschreibungBoxList.add(f.getFahrzeugBeschreibung());		// nehme die Fahrzeugbeschriebung dieses Fahrzeuges in die filteredFahrzeugTypBeschreibungBoxList auf
            		}
        	  	}
        	}
    	} else {
    		filteredFahrzeugTypBeschreibungBoxList.clear();
    	}


    	filteredFahrzeugBeschreibungBoxList.setAll(filteredFahrzeugTypBeschreibungBoxList);		// kopiere die filteredFahrzeugTypBeschreibungBoxList in die filteredFahrzeugBeschreibungBoxList
    }

    /***************************************************************************
    METHODENNAME:	handleAutoCompletePerson
    *//*!
     Filtert ComboBox nach Tastatureingabe.

     \param   KeyEvent

     \return  void

    ***************************************************************************/

    public void handleAutoCompletePerson (KeyEvent event) {

    	personIDBox.show();																// oeffne die ComboBoxauswahl

    	KeyCode code= event.getCode();													// Keyevent code registriert alle Tastatureingaben

    	if((code == KeyCode.BACK_SPACE) && (filterPerson.length() > 0)) {				// wenn die Tastatureingabe ein Backspace war und die L�nge des Strings filerPerson gr��er 0 ist, dann:
    		filterPerson= filterPerson.substring(0, filterPerson.length()-1);			// l�sche den letzten Char in dem String filerPerson
    	}

    	if(code.isDigitKey() || code.isLetterKey() || code == KeyCode.SHIFT) {			// wenn die Tastatureingabe eine Zahl oder ein Buchstabe oder die Tate SHIFT war, dann:
    		filterPerson+= event.getText();												// speichere diesen Char in filterPerson
    	}

    	if (filterPerson.length() > 0) {												// wenn filterPerson gr��er 0 ist, dann:
    		personBeschreibungBoxList.clear();											// l�sche die personBeschreibungBoxList, um etwaige mehrfachaufnahme zu verhindern

			for(int i= 0; i < personList.size(); i++){									// gehe alle Eintr�ge der personList durch
    			String vergleich= personList.get(i).toLowerCase();						// speichere Eintrag in der personList mit Kleinbuchstaben zwischen, da bei SHIFT+Buchstabe nur der Buchstabe bei filterPerson aufgenohmen wird
    			if (vergleich.indexOf(filterPerson) > -1) {								// wenn die Tastatureingabe ein Substring einer Personenbeschreibung ist, dann
        			personBeschreibungBoxList.add(personList.get(i));					// nimm die Personenbeschreibung in personBeschreibungBoxList auf
    			}
    	  	}
		}

    	if (code == KeyCode.ESCAPE) {		// wenn ESC gedr�ckt wurde, dann:
    		filterPerson= "";				// wird filterPerson geleert
    		personIDBox.setValue("");
    	}

    	if (code == KeyCode.ENTER) {		// wenn ENTER gedr�ckt wurde, dann:
    		filterPerson= "";				// wird filterPerson geleert
    		personIDBox.setValue("");
    	}

    	if(filterPerson.length()== 0) {		// wenn filterPerson leer ist, dann:
    		personBeschreibungBoxList.setAll(personList);	// wird die personList in die personBeschreibungBoxList kopiert
    	}
    }

    /***************************************************************************
    METHODENNAME:	handleAutoCompleteFahrzeug
    *//*!
     Filtert ComboBox nach Tastatureingabe
     Kommentare siehe handleAutoCompletePerson

     \param   KeyEvent

     \return  void

    ***************************************************************************/

    public void handleAutoCompleteFahrzeug (KeyEvent event) {

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
    METHODENNAME:	setBuchung
    *//*!
     Definiert die Buchung, welche bearbeitet werden soll. Hierf�r sind
     s�mtliche Daten im System relevant

     \param   Buchung, List<Person>, List<Fahrzeug>, List<Buchung>

     \return  void

    ***************************************************************************/

    public void setBuchung(Buchung buchung, List<Person> persons,  List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {
        this.buchung = buchung;

        if (buchung.getBuchungID() == 0) {										// falls die Buchung mit BuchungsID 0 �bergeben wird, dann ist dies eine neue Buchung
        	if (0 < buchungs.size()){											// wenn wir einen Eintrag in der BuchungsListe haben, dann:
        		int temp= buchungs.get(buchungs.size()-1).getBuchungID() + 1;	// wird die letzte BuchungsID um 1 erh�ht
        		buchungIDLabel.setText(Integer.toString(temp));					// und als buchungIDLabel angezeigt
        	} else {
        		buchungIDLabel.setText(Integer.toString(1));					// andernfalls, haben wir keine Buchung bisher in der Liste und erstellen die erste Buchugn mit ID 1
        	}

        } else {
        	buchungIDLabel.setText(Integer.toString(buchung.getBuchungID()));	// andernfalls, haben wir keine neue Buchung und wollen eine ausgew�hlte Buchung �ndern. Hierf�r wird die zugeh�rige buchungID geladen und angezeigt
        }

        for (Person p : persons) {									// gehe alle Personen durch
        	if (buchung.getPersonID() == p.getPersonID()) {			// wenn die personID aus der Buchung mit einer Person �bereinstimmt
        		personIDBox.setValue(p.getPersonBeschreibung());	// setzte in der personIDBox im TextField deren Beschreibung ein
        	}
        }

        for (Fahrzeug f : fahrzeugs) {									// gehe alle Fahrzeuge durch
        	if (buchung.getFahrzeugID() == f.getFahrzeugID()) {			// wenn die fahrzeugID aus der Buchung mit einem Fahrzeug �bereinstimmt
        		fahrzeugtypBox.setValue(f.getFahrzeugtyp());			// setze in der fahrzeugtypBox dessen Typ
        		fahrzeugIDBox.setValue(f.getFahrzeugBeschreibung());	// sowie in der fahrzeugIDbox im TextField dessen Beschreibung ein
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
    *//*!
     Gibt ein TRUE aus, wenn [OK] geklickt wurde.

     \param   void

     \return  Boolean

    ***************************************************************************/

    public boolean isOkClicked() {
        return okClicked;
    }

    /***************************************************************************
    METHODENNAME:	handleOk
    *//*!
     handler f�r OK. Dieser handler wird ausgef�hrt, wenn [OK] geklickt wurde.
	 Neue Buchungsdaten werden gespeichert.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleOk() {
        if (isInputValid(mainApp.getPersonData(), mainApp.getFahrzeugData())) {						// pr�fe ob alle Eingaben korrekt sind, dann:

        	buchung.setBuchungID(Integer.parseInt(buchungIDLabel.getText()));						// �bernimm alle Daten in die Buchung

        	buchung.setPersonID(getIDfromBeschreibung(personIDBox.getEditor().getText()));
        	buchung.setFahrzeugID(getIDfromBeschreibung(fahrzeugIDBox.getEditor().getText()));
        	buchung.setLeihdauer(Integer.parseInt(leihdauerLabel.getText()));

            buchung.setAusleihdatum(DateUtil.parse(ausleihdatumField.getText()));
            buchung.setRueckgabedatum(DateUtil.parse(rueckgabedatumField.getText()));

            setAusgeliehen();

            okClicked = true;
            dialogStage.close();	// und schlie�e die BuchungEditStage
        }
    }

    /***************************************************************************
    METHODENNAME:	handleCancel
    *//*!
     handler f�r Cancel. Dieser handler wird ausgef�hrt, wenn [Cancel] geklickt
     wurde. Dialogfenster wird geschlo�en, ohne neue Buchungsdaten zu speichern.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /***************************************************************************
    METHODENNAME:	isInputValid
    *//*!
     �berpr�fung der eingegeben Daten. Sollten nicht konforme Daten vorhanden
     sein, so wird eine Fehlermeldung ausgegeben.

     \param   void

     \return  Boolean

    ***************************************************************************/

    private boolean isInputValid(List<Person> persons, List<Fahrzeug> fahrzeugs) {
        String errorMessage = ""; 			// Text f�r Fehlermeldung; anf�nglich leer
        int tempP= 0;						// Hilfsvariablen
        int tempF= 0;

        // �berpr�fe ob die Personeneingabe mit einer verf�gbaren Person aus der Liste �bereinstimmt
        for (int i= 0; i < personList.size(); i++) {
        	if (personIDBox.getEditor().getText().equals(personList.get(i))) {
        		tempP= 1;
        	}
        }
        if (tempP == 0) {
        	errorMessage += "Bitte passende Person ausw�hlen!\n";
        }

        // �berpr�fe ob die augew�hlte Person bereits etwas ausgeliehen hat
        for(Person p : persons) {
        	if (personIDBox.getEditor().getText().equals(p.getPersonBeschreibung())) {
        		if(p.getAusgeliehen().equals("Ja")) {
        			errorMessage += "Diese Person leiht bereits schon ein Fahrzeug aus!\n";
        		}
        	}
        }

     // �berpr�fe ob die Fahrzeugeingabe mit einem verf�gbarem Fahrzeug aus der Liste �bereinstimmt
        for (int i= 0; i < filteredFahrzeugBeschreibungBoxList.size(); i++) {
        	if (fahrzeugIDBox.getEditor().getText().equals(filteredFahrzeugBeschreibungBoxList.get(i))) {
        		tempF= 1;
        	}
        }
        if (tempF == 0) {
        	errorMessage += "Bitte passendes Fahrzeug ausw�hlen!\n";
        }

        // �berpr�fe ob das augew�hlte Fahrzeug bereits verliehen ist
        for(Fahrzeug f : fahrzeugs) {
        	if (fahrzeugIDBox.getEditor().getText().equals(f.getFahrzeugBeschreibung())) {
        		if(f.getAusgeliehen().equals("Ja")) {
        			errorMessage += "Dieses Fahrzeug wird bereits verliehen!\n";
        		}
        	}
        }

        // �berpr�fe ob R�ckgabedatum vor dem Ausleihdatum liegt
        if (Integer.parseInt(leihdauerLabel.getText()) < 1) {
            errorMessage += "Keine g�ltige Leihdauer!\n";
        }

        // �berpr�fe ob als Datum g�ltige Werte benutzt werden
        if (ausleihdatumField.getText() == null || ausleihdatumField.getText().length() == 0) {
            errorMessage += "Kein g�ltiges Ausleihdatum!\n";
        } else {
            if (!DateUtil.validDate(ausleihdatumField.getText())) {
                errorMessage += "Kein g�ltiges Ausleihdatum. Bitte nutzen Sie das Format dd.mm.yyyy!\n";
            }
        }

     // �berpr�fe ob als Datum g�ltige Werte benutzt werden
        if (rueckgabedatumField.getText() == null || rueckgabedatumField.getText().length() == 0) {
            errorMessage += "Kein g�ltiges R�ckgabedatum!\n";
        } else {
            if (!DateUtil.validDate(rueckgabedatumField.getText())) {
                errorMessage += "Kein g�ltiges Rueckgabedatum. Bitte nutzen Sie das Format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {				// wenn die Fehlermeldung leer ist, dann:
            return true;								// wird true zur�ckgegeben, dh alle Eingaben sind korrekt
        } else {										// andernfalls, wird eine Fehlermeldung ausgegeben
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ung�ltige EIngabe");
            alert.setHeaderText("Bitte �ndern Sie die ung�ltigen Eingaben");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    /***************************************************************************
    METHODENNAME:	AusleihdauerBerechnung
    *//*!
     Berechnung der Ausleihdauer

     \param   LocalDate, LocalDate

     \return  int

    ***************************************************************************/

     public void AusleihdauerBerechnung() {

     	LocalDate datum1= DateUtil.parse(ausleihdatumField.getText());		// ermittelt das Datum aus dem Textfield
     	LocalDate datum2= DateUtil.parse(rueckgabedatumField.getText());	// ermittelt das Datum aus dem Textfield

     	int years= datum2.getYear() - datum1.getYear();						// berechnet Jahresunterschied
     	int days= datum2.getDayOfYear() - datum1.getDayOfYear();			// berechnet Tagesunterschied
     	days= days + 365 * years + 1;										// berechnet Gesamtuntershied

     	leihdauerLabel.setText(Integer.toString(days));
     }

     /***************************************************************************
     METHODENNAME:	getIDfromBeschreibung
     *//*!
      Die Zahl im String, welche vor dem "-", wird zur�ckgegeben

      \param   String

      \return  int

     ***************************************************************************/

     public int getIDfromBeschreibung(String temp) {
     	int pos= 0;

     	pos= temp.indexOf("-")-1;				// ermittle Position von "-" im String temp und ziehe 1 ab

     	if (pos == -2) {						// wenn kein "-" vorkommt, so ist pos = -1-1 = -2
     		//pos= Integer.parseInt(temp);
     		return 0;							// in diesem Fall gebe 0 zur�ck
     	} else {								// andernfalls:
     		temp=temp.substring(0, pos);		// �berschreibe temp mit substring vor "-"
         	pos= Integer.parseInt(temp);		// �bersetze String zu Integer
     	}

     	return pos;
     }

     /***************************************************************************
     METHODENNAME:	setAusgeliehen
     *//*!
      Es wird verglichen, ob das heutige Datum kleiner oder
      gleich ist dem R�ckgabedatum aller Buchungen ist. Abh�ngig davon, wird der Ausleihzustand des
      jeweiligen Fahrzeugs und der Person auf "Ja" oder "Nein" gesetzt.

      \param   void

      \return  void

     ***************************************************************************/

     public void setAusgeliehen() {

     	ObservableList<Person> persons = mainApp.getPersonData();
     	ObservableList<Fahrzeug> fahrzeugs = mainApp.getFahrzeugData();
     	ObservableList<Buchung> buchungs = mainApp.getBuchungData();

     	for (Buchung b : buchungs) {

     		if (LocalDate.now().compareTo(b.getRueckgabedatum()) < 1) {
     			for (Person p : persons) {
     				if (p.getPersonID() == b.getPersonID()) {
     					p.setAusgeliehen("Ja");
     				} else {
     					p.setAusgeliehen("Nein");
     				}
     			}
     			for (Fahrzeug f : fahrzeugs) {
     				if (f.getFahrzeugID() == b.getFahrzeugID()) {
     					f.setAusgeliehen("Ja");
     				} else {
     					f.setAusgeliehen("Nein");
     				}
     			}
     		}
     	}

     }
}

/** @}*/ /*end of doxygen group*/