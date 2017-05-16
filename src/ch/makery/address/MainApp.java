/**************************************************************************************************/
/*! \file
  FILE         : $Source: Person.java $
  BESCHREIBUNG : Personenmodel
                 Modellierung einer Person.
***************************************************************************************************/

/** \defgroup Controller MainApp
 *  @{
 */

package ch.makery.address;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import java.io.IOException;
import java.time.LocalDate;
import java.io.File;
import java.util.prefs.Preferences;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ch.makery.address.model.Person;
import ch.makery.address.model.AVLTree;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.BuchungListWrapper;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.model.Tree;
import ch.makery.address.model.FahrzeugListWrapper;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.FahrzeugEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.PersonStatisticController;
import ch.makery.address.view.FahrzeugOverviewController;
import ch.makery.address.view.FahrzeugStatisticController;
import ch.makery.address.view.FahrzeugTypStatisticController;
import ch.makery.address.view.RootLayoutController;
import ch.makery.address.view.SortedTableController;
import ch.makery.address.view.SortedFahrzeugTableController;
import ch.makery.address.view.BuchungEditDialogController;
import ch.makery.address.view.BuchungOverviewController;
import ch.makery.address.view.SuchenFensterController;


/***************************************************************************
CLASS:	MainApp
*//*!
 Die Hauptklasse des Projektes. Hier werden alle Fenster der GUI geöffnet,
 bzw. auf die jeweiligen *.fxml Dateien verwiesen und die jeweiligen
 Controller erzeugt.
 Ebenso werden hier die Daten für Personen, Fahrzeuge und Buchung gespeichert
 und geladen.

***************************************************************************/

public class MainApp extends Application {

	/**************************************************************************/
	/*                                                                        */
	/* Get JavaFX started                                                     */
	/*                                                                        */
	/**************************************************************************/

	private Stage primaryStage;
	private BorderPane rootLayout;

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Fuhrparkverwaltung");

		initRootLayout();

		showPerson();

		setAusgeliehen();
	}

	/**************************************************************************/
	/*                                                                        */
	/* Data as observable lists                                               */
	/*                                                                        */
	/**************************************************************************/

	private ObservableList<Person> personData = FXCollections.observableArrayList();
	private ObservableList<Fahrzeug> fahrzeugData = FXCollections.observableArrayList();
	private ObservableList<Buchung> buchungData = FXCollections.observableArrayList();
	private Tree treeData = new Tree();
	private AVLTree avlTreeData= new AVLTree();

	/***************************************************************************
    METHODENNAME:	MainApp
    *//*!
     Konstruktor der Klasse MainApp.
     Stehen keine Daten zum Laden bereit, so werden Beispieldaten in die
     ObservableLists geladen.

     \param   void

     \return  void

    ***************************************************************************/

	public MainApp() {
		/*Beispieldaten für Personen*/
		personData.add(new Person(1, "Roman", "Weidenfeller", "Strobelallee 50", "Klasse A", 44139, "Dortmund"));
		personData.add(new Person(3, "Jo Ho", "Park", "Strobelallee 50", "Klasse A + B + C", 44139, "Dortmund"));
		personData.add(new Person(5, "Marc", "Bartra", "Strobelallee 50", "Klasse B + C", 44139, "Dortmund"));
		personData.add(new Person(6, "Sven", "Bender", "Strobelallee 50", "Klasse A", 44139, "Dortmund"));
		personData.add(new Person(7, "Ousmane", "Dembele", "Strobelallee 50", "Klasse A + B", 44139, "Dortmund"));
		personData.add(new Person(8, "Nuri", "Sahin", "Strobelallee 50", "Klasse A + B + C", 44139, "Dortmund"));
		personData.add(new Person(9, "Emre", "Mor", "Strobelallee 50", "Klasse B", 44139, "Dortmund"));
		personData.add(new Person(10, "Mario", "Götze", "Strobelallee 50", "Klasse B + C", 44139, "Dortmund"));
		personData.add(new Person(11, "Marco", "Reus", "Strobelallee 50", "Klasse A", 44139, "Dortmund"));
		personData.add(new Person(13, "Raphael", "Guerreiro", "Strobelallee 50", "Klasse A + B + C", 44139, "Dortmund"));
		personData.add(new Person(14, "Alexander", "Isak", "Strobelallee 50", "Klasse B", 44139, "Dortmund"));
		personData.add(new Person(17, "Pierre-Emerick", "Aubameyang", "Strobelallee 50", "Klasse A + B", 44139, "Dortmund"));
		personData.add(new Person(18, "Sebastian", "Rode", "Strobelallee 50", "Klasse A + B + C", 44139, "Dortmund"));
		personData.add(new Person(21, "Andre", "Schürrle", "Strobelallee 50", "Klasse A", 44139, "Dortmund"));
		personData.add(new Person(22, "Christian", "Pulisic", "Strobelallee 50", "Klasse A + B", 44139, "Dortmund"));
		personData.add(new Person(23, "Shinji", "Kagawa", "Strobelallee 50", "Klasse A +B + C", 44139, "Dortmund"));
		personData.add(new Person(24, "Mikel", "Merino", "Strobelallee 50", "Klasse B", 44139, "Dortmund"));
		personData.add(new Person(25, "Sokratis", "Papastathopoulos", "Strobelallee 50", "Klasse B + C", 44139, "Dortmund"));
		personData.add(new Person(26, "Lukas", "Piszcek", "Strobelallee 50", "Klasse A", 44139, "Dortmund"));
		personData.add(new Person(27, "Gonzalo", "Castro", "Strobelallee 50", "Klasse A + B", 44139, "Dortmund"));
		personData.add(new Person(28, "Matthias", "Ginter", "Strobelallee 50", "Klasse A + B + C", 44139, "Dortmund"));
		personData.add(new Person(29, "Marcel", "Schmelzer", "Strobelallee 50", "Klasse B", 44139, "Dortmund"));
		personData.add(new Person(30, "Felix", "Passlack", "Strobelallee 50", "Klasse B + C", 44139, "Dortmund"));
		personData.add(new Person(33, "Julian", "Weigel", "Strobelallee 50", "Klasse A + B + C", 44139, "Dortmund"));
		personData.add(new Person(37, "Erik", "Durm", "Strobelallee 50", "Klasse A + B", 44139, "Dortmund"));
		personData.add(new Person(38, "Roman", "Bürki", "Strobelallee 50", "Klasse A + B + C", 44139, "Dortmund"));
		personData.add(new Person(39, "Hendrik", "Bonmann", "Strobelallee 50", "Klasse B", 44139, "Dortmund"));

		/* fill the Trees with persons */
//    	for (Person p : personData) {
//    		treeData.addNode(p);
//    		avlTreeData.root= avlTreeData.addNodeAVL(avlTreeData.root, p);
//    		avlTreeData.addNodeAVL(p);
//    	}
//
//    	avlTreeData.levelOrder(avlTreeData.root);

		/*Beispieldaten für Fahrzeuge*/
		fahrzeugData.add(new Fahrzeug(1, "BMW", "525d", "Diesel", "Langstrecke", 190, 85948));
		fahrzeugData.add(new Fahrzeug(2, "Audi", "A6 TDI", "Diesel", "Langstrecke", 190, 65948));
		fahrzeugData.add(new Fahrzeug(3, "MB", "E 220 CDI", "Diesel", "Langstrecke", 190,585948));
		fahrzeugData.add(new Fahrzeug(4, "Smart", "ForTwo", "Super", "Cityflitzer", 65, 15948));
		fahrzeugData.add(new Fahrzeug(5, "Toyota", "Aygo", "Super", "Cityflitzer", 65, 25948));
		fahrzeugData.add(new Fahrzeug(6, "VW", "Up", "Super", "Cityflitzer", 65, 15948));
		fahrzeugData.add(new Fahrzeug(7, "Volvo", "FH", "Diesel", "LKW", 250, 65948));
		fahrzeugData.add(new Fahrzeug(8, "MAN", "TGX", "Diesel", "LKW", 250, 45948));
		fahrzeugData.add(new Fahrzeug(9, "VW", "Crafter", "Diesel", "Kleintransporter", 110, 35948));
		fahrzeugData.add(new Fahrzeug(10, "MB", "Sprinter", "Diesel", "Kleintransporter", 110, 55948));
		fahrzeugData.add(new Fahrzeug(11, "Peugeot", "Partner", "Diesel", "Kleintransporter", 110, 45948));
		fahrzeugData.add(new Fahrzeug(12, "BMW", "F 800 R", "Super", "Motorrad", 90, 15948));
		fahrzeugData.add(new Fahrzeug(13, "Honda", "Partner", "Diesel", "Motorrad", 80, 25948));

		/*Beispieldaten für Buchung*/
		buchungData.add(new Buchung(1, 1, 12, 1));
		buchungData.add(new Buchung(2, 3, 2, 1));
		buchungData.add(new Buchung(3, 5, 3, 1));
		buchungData.add(new Buchung(4, 6, 13, 1));
		buchungData.add(new Buchung(5, 7, 5, 1));
		buchungData.add(new Buchung(6, 8, 6, 1));
	}

	/**************************************************************************/
	/*                                                                        */
	/* Local Operation Section                                                */
	/*                                                                        */
	/**************************************************************************/

    /***************************************************************************
    METHODENNAME:	getPersonData
    *//*!
     Gibt die Daten der Personen als eine observable list wieder

     \param   void

     \return  ObservableList

    ***************************************************************************/

	public ObservableList<Person> getPersonData() {
		return personData;
	}

    /***************************************************************************
    METHODENNAME:	getFahrzeugData
    *//*!
     Gibt die Daten der Fahrzeuge als eine observable list wieder

     \param   void

     \return  ObservableList

    ***************************************************************************/

	public ObservableList<Fahrzeug> getFahrzeugData() {
		return fahrzeugData;
	}

    /***************************************************************************
    METHODENNAME:	getBuchungData
    *//*!
     Gibt die Daten der Buchungen als eine observable list wieder

     \param   void

     \return  ObservableList

    ***************************************************************************/

	public ObservableList<Buchung> getBuchungData() {
		return buchungData;
	}

    /***************************************************************************
    METHODENNAME:	getAVLTreeData
    *//*!
     Gibt den AVLBaum zurück

     \param   void

     \return  AVLTree

    ***************************************************************************/

	public AVLTree getAVLTreeData() {
		return avlTreeData;
	}

    /***************************************************************************
    METHODENNAME:	getTreeData
    *//*!
     Gibt den binären Baum zurück

     \param   void

     \return  Tree

    ***************************************************************************/

	public Tree getTreeData() {
		return treeData;
	}


    /***************************************************************************
    METHODENNAME:	initRootLayout
    *//*!
     Initialisiert das root Layout und versucht die letzten Personen- und
     Fahrzeugdaten zu laden

     \param   void

     \return  void

    ***************************************************************************/

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Try to load last opened person, fahrzeug and buchung file.
		File file = getPersonFilePath();
		File fileFahrzeug = getFahrzeugFilePath();
		File fileBuchung = getBuchungFilePath();
		if (file != null) {
			loadPersonDataFromFile(file);
			loadFahrzeugDataFromFile(fileFahrzeug);
			loadBuchungDataFromFile(fileBuchung);
		}
	}

    /***************************************************************************
    METHODENNAME:	getPrimaryStage
    *//*!
     Gibt die PrimaryStage zurück

     \param   void

     \return  Stage

    ***************************************************************************/

	public Stage getPrimaryStage() {
		return primaryStage;
	}

    /***************************************************************************
    METHODENNAME:	main
    *//*!
     Für JavaFX ist eigentlich keine public static void main Methode notwendig.
     Jedoch ist muss man diesen Fall auffangen.

     \param   String[] args

     \return  void

    ***************************************************************************/

	public static void main(String[] args) {
		launch(args);
	}


	/**************************************************************************/
	/*                                                                        */
	/* Save / Load Section                                                    */
	/*                                                                        */
	/**************************************************************************/

    /***************************************************************************
    METHODENNAME:	setPersonFilePath
    *//*!
     Setzt den Dateipfad der aktuell geladenen Datei. Der Pfad verharrt in dem
     betriebssysemspezifischen Register. Fügt der Programmüberschrift den
     Dateinamen hinzu.
     Es wird nur der Dateipfad für die Datei der Personen in den Präferenzen
     gespeichert bzw. geladen. Die Dateipfade für Fahrzeuge und Buchungen werden
     anhand des Persondateipfades hergeleitet.

     \param   File. Datei, von welcher der Dateiname übernommen wird.

     \return  void

    ***************************************************************************/

	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());								// speichert aktuellen Dateipfad in die betriebssystemspezifischen Präferenzen

			// Update the stage title.
			primaryStage.setTitle("Fuhrparkverwaltung - " + file.getName());	// Änderung des Programtitels
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("Fuhrparkverwaltung");
		}
	}

    /***************************************************************************
    METHODENNAME:	getPersonFilePath
    *//*!
     Gibt die Dateipräferenz zurück, z.B. die letzte geöffnete Datei. Die
     Präferenzen werden aus dem Betriebssystemspezifischen Register gelesen.
     Falls keine solche Präferenz gefunden wird, so wird NULL zurückgegeben.

     \param   void

     \return  File

    ***************************************************************************/

	public File getPersonFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);	// lädt hinterlegte Präferenzen
		String filePath = prefs.get("filePath", null);						// lädt filePath
		if (filePath != null) {
			System.out.println(filePath);
			return new File(filePath);										// gibt die Datei aus dem Dateipfad zurück
		} else {
			return null;
		}
	}

    /***************************************************************************
    METHODENNAME:	getFahrzeugFilePath
    *//*!
     Anhand der Dateipräferenz bzw. der Personendatei wird der Dateipfad der
     Fahrzeugliste ermittelt.

     \param   void

     \return  File

    ***************************************************************************/

	public File getFahrzeugFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {												// Herleitung des Dateipfades für Fahrzeuge
			int pos = filePath.lastIndexOf("."); 							// pos des Punktes im Dateipfad
			filePath = filePath.substring(0, pos); 							// Kürzung bis zum Punkt
			String filePathFahrzeug = filePath + "_Fahrzeug.xml";			// Erweiterung des Dateipfades für die Fahrzeuge
			return new File(filePathFahrzeug);
		} else {
			return null;
		}
	}

    /***************************************************************************
    METHODENNAME:	getBuchungFilePath
    *//*!
     Anhand der Dateipräferenz bzw. der Personendatei wird der Dateipfad der
     Buchungsliste ermittelt.

     \param   void

     \return  File

    ***************************************************************************/

	public File getBuchungFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {												// Herleitung des Dateipfades für Buchungen
			int pos = filePath.lastIndexOf("."); 							// pos des Punktes im Dateipfad
			filePath = filePath.substring(0, pos); 							// Kürzung bis zum Punkt
			String filePathBuchung = filePath + "_Buchung.xml";				// Erweiterung des Dateipfades für die Buchungen
			return new File(filePathBuchung);
		} else {
			return null;
		}
	}

    /***************************************************************************
    METHODENNAME:	loadPersonDataFromFile
    *//*!
     Lädt die Personendaten von einer xml-Datei. Hierfür wird der ListWrapper
     genutzt. Die aktuellen Personendaten werden erstetzt.

     \param   File. Die Datei, aus welcher die Personendaten gelesen werden.

     \return  void

    ***************************************************************************/

	public void loadPersonDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

			personData.clear();
			personData.addAll(wrapper.getPersons());

//			treeData.clear();
//			avlTreeData.clear();
//			for (Person p : personData) {
//				treeData.addNode(p);
//				avlTreeData.addNodeAVL(avlTreeData.root, p);
//			}
//			treeData.levelOrder(treeData.root);
//			avlTreeData.levelOrder(avlTreeData.root);

			// Save the file path to the registry.
			setPersonFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}

		treeData.clear();
		avlTreeData.clear();
		for (Person p : personData) {
			treeData.addNode(p);
			avlTreeData.addNodeAVL(p);
			avlTreeData.levelOrder(avlTreeData.root);
			System.out.println("");
		}
//		treeData.levelOrder(treeData.root);
//		System.out.println(" ");
//		avlTreeData.levelOrder(avlTreeData.root);
	}

    /***************************************************************************
    METHODENNAME:	loadFahrzeugDataFromFile
    *//*!
     Lädt die Fahrzeugdaten von einer xml-Datei. Hierfür wird der ListWrapper
     genutzt. Die aktuellen Fahrzeugdaten werden erstetzt.

     \param   File. Die Datei, aus welcher die Fahrzeugdaten gelesen werden.

     \return  void

    ***************************************************************************/

	public void loadFahrzeugDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(FahrzeugListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			FahrzeugListWrapper wrapper = (FahrzeugListWrapper) um.unmarshal(file);

			fahrzeugData.clear();
			fahrzeugData.addAll(wrapper.getFahrzeugs());

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

    /***************************************************************************
    METHODENNAME:	loadBuchungDataFromFile
    *//*!
     Lädt die Buchungsdaten von einer xml-Datei. Hierfür wird der ListWrapper
     genutzt. Die aktuellen Buchungsdaten werden erstetzt.

     \param   File. Die Datei, aus welcher die Buchungsdaten gelesen werden.

     \return  void

    ***************************************************************************/

	public void loadBuchungDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(BuchungListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			BuchungListWrapper wrapper = (BuchungListWrapper) um.unmarshal(file);

			buchungData.clear();
			buchungData.addAll(wrapper.getBuchungs());

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

    /***************************************************************************
    METHODENNAME:	savePersonDataToFile
    *//*!
     Speichert die aktuellen Personendaten in eine xml-Datei. Hierfür wird
     der ListWrapper genutzt.

     \param   File. Die Datei, in welcher die Personendaten gespeichert
					werden sollen.

     \return  void

    ***************************************************************************/

	public void savePersonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our person data.
			PersonListWrapper wrapper = new PersonListWrapper();
			wrapper.setPersons(personData);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

			// Save the file path to the registry.
			setPersonFilePath(file);
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

    /***************************************************************************
    METHODENNAME:	saveFahrzeugDataToFile
    *//*!
     Speichert die aktuellen Fahrzeugdaten in eine xml-Datei. Hierfür wird
     der ListWrapper genutzt.

     \param   File. Die Datei, in welcher die Fahrzeugdaten gespeichert
					werden sollen.

     \return  void

    ***************************************************************************/

	public void saveFahrzeugDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(FahrzeugListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our fahrzeug data.
			FahrzeugListWrapper wrapper = new FahrzeugListWrapper();
			wrapper.setFahrzeugs(fahrzeugData);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

		} catch (Exception e) { // catches ANY exception
			System.out.println("Fehler_Speichern_Fahrzeug");
		}
	}

    /***************************************************************************
    METHODENNAME:	saveBuchungDataToFile
    *//*!
     Speichert die aktuellen Buchungsdaten in eine xml-Datei. Hierfür wird
     der ListWrapper genutzt.

     \param   File. Die Datei, in welcher die Buchungsdaten gespeichert
					werden sollen.

     \return  void

    ***************************************************************************/

	public void saveBuchungDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(BuchungListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our fahrzeug data.
			BuchungListWrapper wrapper = new BuchungListWrapper();
			wrapper.setBuchungs(buchungData);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

		} catch (Exception e) { // catches ANY exception
			System.out.println("Fehler_Speichern_Buchung");
		}
	}

	/**************************************************************************/
	/*                                                                        */
	/* Set View and Controller Section                                        */
	/*                                                                        */
	/**************************************************************************/

    /***************************************************************************
    METHODENNAME:	showPerson
    *//*!
     Zeigt die Personenübersicht (PersonenOverview) innerhalb des root Layout

     \param   void

     \return  void

    ***************************************************************************/

	public void showPerson() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(personOverview);

			// Give the controller access to the main app.
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /***************************************************************************
    METHODENNAME:	showFahrzeug
    *//*!
     Zeigt die Fahrzeugübersicht (FahrzeugOverview) innerhalb des root Layout

     \param   void

     \return  void

    ***************************************************************************/

	public void showFahrzeug() {
		try {
			// Load fahrzeug overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/FahrzeugOverview.fxml"));
			AnchorPane fahrzeugOverview = (AnchorPane) loader.load();

			// Set fahrzeug overview into the center of root layout.
			rootLayout.setCenter(fahrzeugOverview);

			// Give the controller access to the main app.
			FahrzeugOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /***************************************************************************
    METHODENNAME:	showBuchung
    *//*!
     Zeigt die Buchungsübersicht (BuchungOverview) innerhalb des root Layout

     \param   void

     \return  void

    ***************************************************************************/

	public void showBuchung() {
		try {
			// Load buchung overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BuchungOverview.fxml"));
			AnchorPane buchungOverview = (AnchorPane) loader.load();

			// Set buchung overview into the center of root layout.
			rootLayout.setCenter(buchungOverview);

			// Give the controller access to the main app.
			BuchungOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /***************************************************************************
    METHODENNAME:	showSortedTableNachname
    *//*!
     Zeigt eine sortierte Tabelle aller Personen nach Nachname

     \param   void

     \return  void

    ***************************************************************************/

	public void showSortedTableNachname() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SortedTable.fxml"));
			AnchorPane sortedTable = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sortedTable);

			// Give the controller access to the main app.
			SortedTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.ShowSortNachname();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /***************************************************************************
    METHODENNAME:	showSortedTableVorname
    *//*!
     Zeigt eine sortierte Tabelle aller Personen nach Vorname

     \param   void

     \return  void

    ***************************************************************************/

	public void showSortedTableVorname() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SortedTable.fxml"));
			AnchorPane sortedTable = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sortedTable);

			// Give the controller access to the main app.
			SortedTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.ShowSortVorname();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /***************************************************************************
    METHODENNAME:	showSortedTableStadt
    *//*!
     Zeigt eine sortierte Tabelle aller Personen nach Stadt

     \param   void

     \return  void

    ***************************************************************************/

	public void showSortedTableStadt() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SortedTable.fxml"));
			AnchorPane sortedTable = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sortedTable);

			// Give the controller access to the main app.
			SortedTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.ShowSortStadt();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /***************************************************************************
    METHODENNAME:	showSortedTableAusleihende
    *//*!
     Zeigt eine sortierte Tabelle aller Personen nach Ausleihende

     \param   void

     \return  void

    ***************************************************************************/

	public void showSortedTableAusleihende() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SortedTable.fxml"));
			AnchorPane sortedTable = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sortedTable);

			// Give the controller access to the main app.
			SortedTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.ShowSortAusleihende();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /***************************************************************************
    METHODENNAME:	showSortedFahrzeugTableAusleihende
    *//*!
     Zeigt eine sortierte Tabelle aller Fahrzeuge nach Ausleihende

     \param   void

     \return  void

    ***************************************************************************/

	public void showSortedFahrzeugTableAusleihende() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SortedFahrzeugTable.fxml"));
			AnchorPane sortedTable = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sortedTable);

			// Give the controller access to the main app.
			SortedFahrzeugTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.ShowSortFahrzeugAusleihende();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /***************************************************************************
    METHODENNAME:	showSortedFahrzeugTableHersteller
    *//*!
     Zeigt eine sortierte Tabelle aller Fahrzeuge nach Hersteller

     \param   void

     \return  void

    ***************************************************************************/

	public void showSortedFahrzeugTableHersteller() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SortedFahrzeugTable.fxml"));
			AnchorPane sortedTable = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sortedTable);

			// Give the controller access to the main app.
			SortedFahrzeugTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.ShowSortFahrzeugHersteller();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /***************************************************************************
    METHODENNAME:	showSortedFahrzeugTableMarke
    *//*!
     Zeigt eine sortierte Tabelle aller Fahrzeuge nach Marke

     \param   void

     \return  void

    ***************************************************************************/

	public void showSortedFahrzeugTableMarke() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SortedFahrzeugTable.fxml"));
			AnchorPane sortedTable = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sortedTable);

			// Give the controller access to the main app.
			SortedFahrzeugTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.ShowSortFahrzeugMarke();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /***************************************************************************
    METHODENNAME:	showSortedFahrzeugTableKilometerstand
    *//*!
     Zeigt eine sortierte Tabelle aller Fahrzeuge nach Kilometerstand

     \param   void

     \return  void

    ***************************************************************************/

	public void showSortedFahrzeugTableKilometerstand() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SortedFahrzeugTable.fxml"));
			AnchorPane sortedTable = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sortedTable);

			// Give the controller access to the main app.
			SortedFahrzeugTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.ShowSortFahrzeugKilometerstand();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    /***************************************************************************
    METHODENNAME:	showSortedFahrzeugTableLeistung
    *//*!
     Zeigt eine sortierte Tabelle aller Fahrzeuge nach Leistung

     \param   void

     \return  void

    ***************************************************************************/

	public void showSortedFahrzeugTableLeistung() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SortedFahrzeugTable.fxml"));
			AnchorPane sortedTable = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sortedTable);

			// Give the controller access to the main app.
			SortedFahrzeugTableController controller = loader.getController();
			controller.setMainApp(this);
			controller.ShowSortFahrzeugLeistung();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /***************************************************************************
    METHODENNAME:	showPersonEditDialog
    *//*!
     Öffnet ein Dialogfeld zum Bearbeiten von Personendaten. Wenn man [OK]
     klickt, so werden die Personendaten überschrieben und das Boolean
     TRUE wiedergegeben.

     \param   Person. Ein Objekt der Klasse Person, welche bearbeitet
			  werden soll.

     \return  Boolean

    ***************************************************************************/

	public boolean showPersonEditDialog(Person person) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			PersonEditDialogController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

    /***************************************************************************
    METHODENNAME:	showSuchenFenster
    *//*!
     Öffnet Fenster zur Eingabe eines Vornames für die Suche im binärem Baum

     \param   void

     \return  void

    ***************************************************************************/

	public boolean showSuchenFenster()
	{
		try {
			// Load buchung overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SuchenFenster.fxml"));
			AnchorPane Suchen = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Suchen");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(Suchen);
			dialogStage.setScene(scene);

			// Give the controller access to the main app.
			SuchenFensterController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it
         	dialogStage.showAndWait();

         	return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

    /***************************************************************************
    METHODENNAME:	showFahrzeugEditDialog
    *//*!
     Öffnet ein Dialogfeld zum Bearbeiten von Fahrzeugdaten. Wenn man [OK]
     klickt, so werden die Fahrzeugdaten überschrieben und das Boolean
     TRUE wiedergegeben.

     \param   Fahrzeug. Ein Objekt der Klasse Fahrzeug, welche bearbeitet
			  werden soll.

     \return  Boolean

    ***************************************************************************/

	public boolean showFahrzeugEditDialog(Fahrzeug fahrzeug) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/FahrzeugEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Fahrzeug");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the fahrzeug into the controller.
			FahrzeugEditDialogController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
			controller.setFahrzeug(fahrzeug);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

    /***************************************************************************
    METHODENNAME:	showBuchungEditDialog
    *//*!
     Öffnet ein Dialogfeld zum Bearbeiten von Buchungsdaten. Wenn man [OK]
     klickt, so werden die Buchungsdaten überschrieben und das Boolean
     TRUE wiedergegeben.

     \param   Buchung. Ein Objekt der Klasse Buchung, welche bearbeitet
			  werden soll.

     \return  Boolean

    ***************************************************************************/

	public boolean showBuchungEditDialog(Buchung buchung) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BuchungEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Buchung");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the buchung into the controller.
			BuchungEditDialogController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
			controller.setBuchung(buchung, personData, fahrzeugData, buchungData);
			controller.setPersonList(personData);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

    /***************************************************************************
    METHODENNAME:	showPersonStatistics
    *//*!
     Öffnet die PersonStatistik im root Layout

     \param   void

     \return  void

    ***************************************************************************/

	public void showPersonStatistics() {
		try {
			// Load the fxml file and create a new stage for the popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonStatistic.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			rootLayout.setCenter(page);

			PersonStatisticController controller = loader.getController();
			controller.setPersonStatistic(personData, buchungData);

			// dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /***************************************************************************
    METHODENNAME:	showFahrzeugStatistics
    *//*!
     Öffnet die FahrzeugStatistik im root Layout

     \param   void

     \return  void

    ***************************************************************************/

	public void showFahrzeugStatistics() {
		try {
			// Load the fxml file and create a new stage for the popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/FahrzeugStatistic.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			rootLayout.setCenter(page);

			FahrzeugStatisticController controller = loader.getController();
			controller.setFahrzeugStatistic(fahrzeugData, buchungData);

			// dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /***************************************************************************
    METHODENNAME:	showFahrzeugTypStatistics
    *//*!
     Öffnet die FahrzeugTypStatistik im root Layout

     \param   void

     \return  void

    ***************************************************************************/

	public void showFahrzeugTypStatistics() {
		try {
			// Load the fxml file and create a new stage for the popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/FahrzeugTypStatistic.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			rootLayout.setCenter(page);

			FahrzeugTypStatisticController controller = loader.getController();
			controller.setFahrzeugTypStatistic(fahrzeugData, buchungData);

			// dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /***************************************************************************
    METHODENNAME:	showFahrzeugTageStatistics
    *//*!
     Öffnet die FahrzeugTageStatistik im root Layout

     \param   void

     \return  void

    ***************************************************************************/

	public void showFahrzeugTageStatistics() {
		try {
			// Load the fxml file and create a new stage for the popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/FahrzeugStatistic.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			rootLayout.setCenter(page);

			FahrzeugStatisticController controller = loader.getController();
			controller.setFahrzeugTageStatistic(fahrzeugData, buchungData);

			// dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /***************************************************************************
    METHODENNAME:	showFahrzeugTypTageStatistics
    *//*!
     Öffnet die FahrzeugTypTageStatistik im root Layout

     \param   void

     \return  void

    ***************************************************************************/

	public void showFahrzeugTypTageStatistics() {
		try {
			// Load the fxml file and create a new stage for the popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/FahrzeugTypStatistic.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			rootLayout.setCenter(page);

			FahrzeugTypStatisticController controller = loader.getController();
			controller.setFahrzeugTypTageStatistic(fahrzeugData, buchungData);

			// dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /***************************************************************************
    METHODENNAME:	setAusgeliehen
    *//*!
     Es wird verglichen, ob das heutige Datum kleiner oder
     gleich ist dem Rückgabedatum aller Buchungen ist. Abhängig davon, wird der Ausleihzustand des
     jeweiligen Fahrzeugs und der Person auf "Ja" oder "Nein" gesetzt.

     \param   void

     \return  void

    ***************************************************************************/

    public void setAusgeliehen() {

    	for (Person p : personData) {		// für alle Personen
    		p.setAusgeliehen("Nein");		// setze erstmal den Ausleihzustand auf Nein
    	}

    	for (Fahrzeug f : fahrzeugData) {	// für alle Fahrzeuge
    		f.setAusgeliehen("Nein");		// setze den Ausleihzustand erstmal auf Nein
    	}

    	for (Buchung b : buchungData) {										// gehe alle Buchungen durch
    		if (LocalDate.now().compareTo(b.getRueckgabedatum()) < 1) {		// wenn das heutige Datum kleiner/gleich ist dem Rückgabedatum, dann
    			for (Person p : personData) {
    				if (p.getPersonID() == b.getPersonID()) {				// finde zugehörige Person zur personID
    					p.setAusgeliehen("Ja");								// setze Ausleihzustand auf Ja
    				}
    			}
    			for (Fahrzeug f : fahrzeugData) {
    				if (f.getFahrzeugID() == b.getFahrzeugID()) {			// finde zugehöriges Fahrzeug zur fahrzeugID
    					f.setAusgeliehen("Ja");								// setze Ausleihzustand auf Ja
    				}
    			}
    		}
    	}
    }
}

/** @}*/ /*end of doxygen group*/