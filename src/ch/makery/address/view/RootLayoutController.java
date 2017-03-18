package ch.makery.address.view;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import ch.makery.address.MainApp;

/**************************************************************************/
/*                                                                        */
/* Class RootLayoutController                                             */
/*                                                                        */
/**************************************************************************/

public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

	/**************************************************************************/
	/*                                                                        */
	/* Local Operation Section                                                */
	/*                                                                        */
	/**************************************************************************/

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

	METHODENNAME:	handleNew

	BESCHREIBUNG:   handler für den New Menüpunkt.
					Wird New angeklickt, so wird ein neuer Fuhrpark angelegt.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.getFahrzeugData().clear();
        mainApp.setPersonFilePath(null);
    }

	/***************************************************************************

	METHODENNAME:	handleOpen

	BESCHREIBUNG:   handler für den Open Menüpunkt.
					Wird Open angeklickt, so wird ein Dialogfeld geöffnet, zum
					Laden einer xml-Datei.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);

            String filePath= file.getAbsolutePath();
            int pos= filePath.lastIndexOf(".");		// pos des Punktes im Dateipfad
        	filePath= filePath.substring(0, pos);	// Kürzung bis zum Punkt
        	String filePathFahrzeug= filePath + "_Fahrzeug.xml";
        	File Fahrzeugfile= new File(filePathFahrzeug);

        	mainApp.loadFahrzeugDataFromFile(Fahrzeugfile);
        }
    }

	/***************************************************************************

	METHODENNAME:	handleSave

	BESCHREIBUNG:   handler für den Save Menüpunkt.
					Wird Save angeklickt, so werden die aktuellen Daten
					gespeichert. Wurde zu Beginn keine Datei geladen, so wird
					das Save as Dialogfeld geöffnet.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        File fahrzeugFile= mainApp.getFahrzeugFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
            mainApp.saveFahrzeugDataToFile(fahrzeugFile);
        } else {
            handleSaveAs();
        }
    }

	/***************************************************************************

	METHODENNAME:	handleSaveAs

	BESCHREIBUNG:   handler für den SaveAs Menüpunkt.
					Wird SaveAs angeklickt, so wird ein Dialogfeld aufgerufen,
					in welchem man den Dateinamen für dei xml-Datei eingeben
					kann.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        File fileFahrzeug= null;

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
                fileFahrzeug= new File(file.getPath() +"_Fahrzeug.xml");
            }
            mainApp.savePersonDataToFile(file);
            mainApp.saveFahrzeugDataToFile(fileFahrzeug);
        }
    }

	/***************************************************************************

	METHODENNAME:	handleAbout

	BESCHREIBUNG:   handler für den About Menüpunkt.
					Wird About angeklickt, so wird ein Dialogfeld aufgerufen,
					in welchem eine Beschreibung des Programmes hinterlegt ist.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Fuhrpark");
    	alert.setHeaderText("Projektarbeit: Fuhrpark_JOSA");
    	alert.setContentText("Autoren: Julia, Olga, Simon, Alex");

    	alert.showAndWait();
    }

	/***************************************************************************

	METHODENNAME:	handleExit

	BESCHREIBUNG:   handler für den Exit Menüpunkt.
					Wird Exit angeklickt, so wird das Programm beendet.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleExit() {
        System.exit(0);
    }

	/***************************************************************************

	METHODENNAME:	handleShowBirthdayStatistics

	BESCHREIBUNG:   handler für den ShowBirthdayStatistics Menüpunkt.
					Wird ShowBirthdayStatistics angeklickt, so wird
					die Geburtstagstatistik in der MainApp geöffnet.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleShowBirthdayStatistics() {
      mainApp.showBirthdayStatistics();
    }

	/***************************************************************************

	METHODENNAME:	handleShowPerson

	BESCHREIBUNG:   handler für den ShowPerson Menüpunkt.
					Wird ShowPerson angeklickt, so wird
					die PersonOverview in der MainApp geöffnet.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleShowPerson() {
      mainApp.showPerson();
    }

	/***************************************************************************

	METHODENNAME:	handleShowFahrzeug

	BESCHREIBUNG:   handler für den ShowFahrzeug Menüpunkt.
					Wird ShowFahrzeug angeklickt, so wird
					die FahrzeugOverview in der MainApp geöffnet.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    @FXML
    private void handleShowFahrzeug() {
      mainApp.showFahrzeug();
    }
}