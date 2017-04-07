/**************************************************************************************************/
/*! \file
  FILE         : $Source: RootLayoutController.java $
  BESCHREIBUNG : Controller
                 Controller f�r die Men�leiste
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

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import ch.makery.address.MainApp;

/***************************************************************************
CLASS:	RootLayoutController
*//*!
 Die Klasse RootLayoutController hat nur einen Standardkonstruktor.

***************************************************************************/

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
    *//*!
     Is called by the main application to give a reference back to itself.

     \param   mainApp

     \return  void

    ***************************************************************************/

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /***************************************************************************
    METHODENNAME:	handleNew
    *//*!
     handler f�r den New Men�punkt.
	 Wird New angeklickt, so wird ein neuer Fuhrpark angelegt.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.getFahrzeugData().clear();
        mainApp.getBuchungData().clear();
        mainApp.setPersonFilePath(null);
    }

    /***************************************************************************
    METHODENNAME:	handleOpen
    *//*!
     handler f�r den Open Men�punkt. Wird Open angeklickt, so wird ein
     Dialogfeld ge�ffnet, zum Laden einer xml-Datei.

     \param   void

     \return  void

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
        	filePath= filePath.substring(0, pos);	// K�rzung bis zum Punkt
        	String filePathFahrzeug= filePath + "_Fahrzeug.xml";
        	String filePathBuchung= filePath + "_Buchung.xml";
        	File Fahrzeugfile= new File(filePathFahrzeug);
        	File Buchungfile= new File(filePathBuchung);

        	mainApp.loadFahrzeugDataFromFile(Fahrzeugfile);
        	mainApp.loadBuchungDataFromFile(Buchungfile);
        }
    }

    /***************************************************************************
    METHODENNAME:	handleSave
    *//*!
     handler f�r den Save Men�punkt. Wird Save angeklickt, so werden die
     aktuellen Daten gespeichert. Wurde zu Beginn keine Datei geladen, so wird
	 das Save as Dialogfeld ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        File fahrzeugFile= mainApp.getFahrzeugFilePath();
        File buchungFile= mainApp.getBuchungFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
            mainApp.saveFahrzeugDataToFile(fahrzeugFile);
            mainApp.saveBuchungDataToFile(buchungFile);
        } else {
            handleSaveAs();
        }
    }

    /***************************************************************************
    METHODENNAME:	handleSaveAs
    *//*!
     handler f�r den SaveAs Men�punkt. Wird SaveAs angeklickt, so wird ein
     Dialogfeld aufgerufen, in welchem man den Dateinamen f�r dei xml-Datei
     eingeben kann.

     \param   void

     \return  void

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
        String filePath= file.getAbsolutePath();
        int pos= filePath.lastIndexOf(".");		// pos des Punktes im Dateipfad
    	filePath= filePath.substring(0, pos);	// K�rzung bis zum Punkt
        File fahrzeugfile= new File(filePath + "_Fahrzeug.xml");
        File buchungfile= new File(filePath + "_Buchung.xml");

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
            mainApp.saveFahrzeugDataToFile(fahrzeugfile);
            mainApp.saveBuchungDataToFile(buchungfile);
        }
    }

    /***************************************************************************
    METHODENNAME:	handleAbout
    *//*!
     handler f�r den About Men�punkt. Wird About angeklickt, so wird ein
     Dialogfeld aufgerufen, in welchem eine Beschreibung des Programmes
     hinterlegt ist

     \param   void

     \return  void

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
    *//*!
     handler f�r den Exit Men�punkt. Wird Exit angeklickt, so wird das
     Programm beendet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    /***************************************************************************
    METHODENNAME:	handleShowBirthdayStatistics
    *//*!
     handler f�r den ShowBirthdayStatistics Men�punkt. Wird
     ShowBirthdayStatistics angeklickt, so wird die Geburtstagstatistik in
     der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowBirthdayStatistics() {
      mainApp.showBirthdayStatistics();
    }

    /***************************************************************************
    METHODENNAME:	handleShowPersonStatistic
    *//*!
     handler f�r den ShowPersonStatistic Men�punkt. Wird
     ShowPersonStatistic angeklickt, so wird die Personstatistik in
     der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowPersonStatistic() {
      mainApp.showPersonStatistics();
    }

    /***************************************************************************
    METHODENNAME:	handleShowFahrzeugStatistic
    *//*!
     handler f�r den ShowFahrzeugStatistic Men�punkt. Wird
     ShowFahrzeugStatistic angeklickt, so wird die Fahrzeugstatistik in
     der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowFahrzeugStatistic() {
      mainApp.showFahrzeugStatistics();
    }

    /***************************************************************************
    METHODENNAME:	handleShowFahrzeugTypStatistic
    *//*!
     handler f�r den ShowFahrzeugTypStatistic Men�punkt. Wird
     ShowFahrzeugTypStatistic angeklickt, so wird die Fahrzeugtypstatistik
     in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowFahrzeugTypStatistic() {
      mainApp.showFahrzeugTypStatistics();
    }

    /***************************************************************************
    METHODENNAME:	handleShowFahrzeugTageStatistic
    *//*!
     handler f�r den ShowFahrzeugTageStatistic Men�punkt. Wird
     ShowFahrzeugTageStatistic angeklickt, so wird die FahrzeugTagestatistik
     in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowFahrzeugTypTageStatistic() {
      mainApp.showFahrzeugTypTageStatistics();
    }

    /***************************************************************************
    METHODENNAME:	handleShowFahrzeugTageStatistic
    *//*!
     handler f�r den ShowFahrzeugTageStatistic Men�punkt. Wird
     ShowFahrzeugTageStatistic angeklickt, so wird die FahrzeugTagestatistik
     in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

     @FXML
     private void handleShowFahrzeugTageStatistic() {
       mainApp.showFahrzeugTageStatistics();
     }

     /***************************************************************************
     METHODENNAME:	handleShowPerson
     *//*!
      handler f�r den ShowPerson Men�punkt. Wird ShowPerson angeklickt, so wird
	  die PersonOverview in der MainApp ge�ffnet.

      \param   void

      \return  void

     ***************************************************************************/

     @FXML
     private void handleShowPerson() {
    	 mainApp.showPerson();
     }

     /***************************************************************************
     METHODENNAME:	handleShowFahrzeug
     *//*!
      handler f�r den ShowFahrzeug Men�punkt. Wird ShowPerson angeklickt, so wird
	  die FahrzeugOverview in der MainApp ge�ffnet.

      \param   void

      \return  void

     ***************************************************************************/

     @FXML
     private void handleShowFahrzeug() {
    	 mainApp.showFahrzeug();
     }

     /***************************************************************************
     METHODENNAME:	handleShowBuchung
     *//*!
      handler f�r den ShowBuchung Men�punkt. Wird ShowBuchung angeklickt, so wird
 	 die BuchungOverview in der MainApp ge�ffnet.

      \param   void

      \return  void

     ***************************************************************************/

     @FXML
     private void handleShowBuchung() {
       mainApp.showBuchung();
     }

    /***************************************************************************
    METHODENNAME:	handleShowSortedTableNachname
    *//*!
     handler f�r den Personen nach Nachnamen Men�punkt. Wird
     Personen nach Nachnamen angeklickt, so wird die showSortedTableNachname
     in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowSortedTableNachname() {
      mainApp.showSortedTableNachname();
    }

    /***************************************************************************
    METHODENNAME:	handleShowSortedTableVorname
    *//*!
     handler f�r den Personen nach Nachnamen Men�punkt. Wird
     Personen nach Nachnamen angeklickt, so wird die showSortedTableVorname
     in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowSortedTableVorname() {
      mainApp.showSortedTableVorname();
    }

    /***************************************************************************
    METHODENNAME:	handleShowSortedTableStadt
    *//*!
     handler f�r den Personen nach Stadt Men�punkt. Wird Personen nach Stadt
     angeklickt, so wird die showSortedTableStadt in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowSortedTableStadt() {
      mainApp.showSortedTableStadt();
    }

    /***************************************************************************
    METHODENNAME:	handleShowSortedTableAusleihende
    *//*!
     handler f�r den Personen nach Ausleihende Men�punkt. Wird
     Personen nach Ausleihende angeklickt, so wird die showSortedTableAusleihende
     in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowSortedTableAusleihende() {
      mainApp.showSortedTableAusleihende();
    }

    /***************************************************************************
    METHODENNAME:	handleShowSortedFahrzeugTableHersteller
    *//*!
     handler f�r den Fahrzeug nach Hersteller Men�punkt. Wird
     Fahrzeug nach Hersteller angeklickt, so wird die
     showSortedFahrzeugTableHersteller in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowSortedFahrzeugTableHersteller() {
      mainApp.showSortedFahrzeugTableHersteller();
    }

    /***************************************************************************
    METHODENNAME:	handleShowSortedFahrzeugTableMarke
    *//*!
     handler f�r den Fahrzeug nach Marke Men�punkt. Wird
     Fahrzeug nach Marke angeklickt, so wird die showSortedFahrzeugTableMarke in
     der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowSortedFahrzeugTableMarke() {
      mainApp.showSortedFahrzeugTableMarke();
    }

    /***************************************************************************
    METHODENNAME:	handleShowSortedFahrzeugTableKilometerstand
    *//*!
     handler f�r den Fahrzeug nach Kilometerstand Men�punkt. Wird
     Fahrzeug nach Kilometerstand angeklickt, so wird die
     showSortedFahrzeugTableKilometerstand in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowSortedFahrzeugTableKilometerstand() {
      mainApp.showSortedFahrzeugTableKilometerstand();
    }

    /***************************************************************************
    METHODENNAME:	handleShowSortedFahrzeugTableLeistung
    *//*!
     handler f�r den Fahrzeug nach Leistung Men�punkt. Wird
     Fahrzeug nach Leistung angeklickt, so wird die
     showSortedFahrzeugTableLeistung in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/


    @FXML
    private void handleShowSortedFahrzeugTableLeistung() {
      mainApp.showSortedFahrzeugTableLeistung();
    }

    /***************************************************************************
    METHODENNAME:	handleShowSortedFahrzeugTableAusleihende
    *//*!
     handler f�r den Fahrzeug nach Ausleihende Men�punkt. Wird
     Fahrzeug nach Ausleihende angeklickt, so wird die
     handleShowSortedFahrzeugTableAusleihende in der MainApp ge�ffnet.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void handleShowSortedFahrzeugTableAusleihende() {
      mainApp.showSortedFahrzeugTableAusleihende();
    }
}

/** @}*/ /*end of doxygen group*/