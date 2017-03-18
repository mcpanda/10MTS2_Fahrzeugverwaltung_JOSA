package ch.makery.address;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import java.io.IOException;
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
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.model.FahrzeugListWrapper;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.FahrzeugEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.FahrzeugOverviewController;
import ch.makery.address.view.RootLayoutController;
import ch.makery.address.view.BirthdayStatisticsController;

/**************************************************************************/
/*                                                                        */
/* Class MainApp                                                          */
/*                                                                        */
/**************************************************************************/

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
	}

	/**************************************************************************/
	/*                                                                        */
	/* Data as observable list                                                */
	/*                                                                        */
	/**************************************************************************/

	private ObservableList<Person> personData = FXCollections.observableArrayList();
	private ObservableList<Fahrzeug> fahrzeugData = FXCollections.observableArrayList();

	/**************************************************************************/
	/*                                                                        */
	/* Constructur                                                            */
	/*                                                                        */
	/**************************************************************************/

	public MainApp() {
		personData.add(new Person(1, "Roman", "Bürki", "Strobelallee 50", 44139, "Dortmund"));
		personData.add(new Person(2, "Marc", "Bartra", "Strobelallee 50", 44139, "Dortmund"));
		personData.add(new Person(3, "Sven", "Bender", "Strobelallee 50", 44139, "Dortmund"));

		fahrzeugData.add(new Fahrzeug(1, "BMW", "525d", "Diesel", 190, 85948));
		fahrzeugData.add(new Fahrzeug(2, "Audi", "A6", "Diesel", 190, 85948));
	}

	/**************************************************************************/
	/*                                                                        */
	/* Local Operation Section                                                */
	/*                                                                        */
	/**************************************************************************/

	/***************************************************************************

	METHODENNAME:	getPersonData

	BESCHREIBUNG:   Gibt die Daten der Personen als eine observable list wieder

	PARAMETER: 		void

	RETURN:			ObservableList

	***************************************************************************/

	public ObservableList<Person> getPersonData() {
		return personData;
	}

	/***************************************************************************

	METHODENNAME:	getFahrzeugData

	BESCHREIBUNG:   Gibt die Daten der Fahrzeuge als eine observable list wieder

	PARAMETER: 		void

	RETURN:			ObservableList

	***************************************************************************/

	public ObservableList<Fahrzeug> getFahrzeugData() {
		return fahrzeugData;
	}

	/***************************************************************************

	METHODENNAME:	initRootLayout

	BESCHREIBUNG:   Initialisiert das root Layout und versucht
					die letzten Personen- und Fahrzeugdaten zu laden

	PARAMETER: 		void

	RETURN:			void

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

		// Try to load last opened person and fahrzeug file.
		File file = getPersonFilePath();
		File fileFahrzeug = getFahrzeugFilePath();
		if (file != null) {
			loadPersonDataFromFile(file);
			loadFahrzeugDataFromFile(fileFahrzeug);
		}
	}

	/***************************************************************************

	METHODENNAME:	showPerson

	BESCHREIBUNG:   Zeigt die Personenübersicht (PersonenOverview)
					innerhalb des root Layout

	PARAMETER: 		void

	RETURN:			void

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

	BESCHREIBUNG:   Zeigt die Fahrzeugübersicht (FahrzeugOverview)
					innerhalb des root Layout

	PARAMETER: 		void

	RETURN:			void

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

	METHODENNAME:	getPrimaryStage

	BESCHREIBUNG:   Gibt die PrimaryStage zurück

	PARAMETER: 		void

	RETURN:			Stage

	***************************************************************************/

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/***************************************************************************

	METHODENNAME:	main^^

	BESCHREIBUNG:   Für JavaFX ist eigentlich keine public static void main
					Methode notwendig. Jedoch ist muss man diesen Fall auffangen.

	PARAMETER: 		String[] args

	RETURN:			void

	***************************************************************************/

	public static void main(String[] args) {
		launch(args);
	}

	/***************************************************************************

	METHODENNAME:	getPersonFilePath

	BESCHREIBUNG:   Gibt die Dateipräferenz zurück, z.B. die letzte geöffnete
					Datei. Die Präferenzen werden aus dem betriebssystem-
					spezifischen Register gelesen. Falls keine solche
					Präferenz gefunden wird, so wird NULL zurückgegeben.

	PARAMETER: 		void

	RETURN:			File

	***************************************************************************/

	public File getPersonFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			System.out.println(filePath);
			return new File(filePath);
		} else {
			return null;
		}
	}

	/***************************************************************************

	METHODENNAME:	getFahrzeugFilePath

	BESCHREIBUNG:   Anhand der Dateipräferenz, wie z.B. die letzte geöffnete
					Datei, wird der Dateiname der Fahrzeugliste ermittelt.

	PARAMETER: 		void

	RETURN:			File

	***************************************************************************/

	public File getFahrzeugFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			int pos = filePath.lastIndexOf("."); // pos des Punktes im Dateipfad
			filePath = filePath.substring(0, pos); // Kürzung bis zum Punkt
			String filePathFahrzeug = filePath + "_Fahrzeug.xml";
			return new File(filePathFahrzeug);
		} else {
			return null;
		}
	}

	/***************************************************************************

	METHODENNAME:	setPersonFilePath

	BESCHREIBUNG:   Setzt den Dateipfad der aktuell geladenen Datei. Der Pfad
					verharrt in dem betribsspezifischen Register.
					Fügt der Programmüberschrift den Dateinamen hinzu.

	PARAMETER: 		File
					Datei, von welcher der Dateiname übernommen wird.

	RETURN:			void

	***************************************************************************/

	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			// Update the stage title.
			primaryStage.setTitle("Fuhrparkverwaltung - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("Fuhrparkverwaltung");
		}
	}

	/***************************************************************************

	METHODENNAME:	showPersonEditDialog

	BESCHREIBUNG:   Öffnet ein Dialogfeld zum Bearbeiten von Personendaten.
					Wenn man [OK] klickt, so werden die Personendaten
					überschrieben und das Boolean TRUE wiedergegeben.

	PARAMETER: 		Person. Ein Objekt der Klasse Person, welche bearbeitet
					werden soll.

	RETURN:			Boolean

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

	METHODENNAME:	showFahrzeugEditDialog

	BESCHREIBUNG:   Öffnet ein Dialogfeld zum Bearbeiten von Fahrzeugdaten.
					Wenn man [OK] klickt, so werden die Fahrzeugdaten
					überschrieben und das Boolean TRUE wiedergegeben.

	PARAMETER: 		Fahrzeug. Ein Objekt der Klasse Fahrzeug, welche bearbeitet
					werden soll.

	RETURN:			Boolean

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

	METHODENNAME:	loadPersonDataFromFile

	BESCHREIBUNG:   Lädt die Personendaten von einer xml-Datei. Die aktuellen
					Personendaten werden erstetzt.

	PARAMETER: 		File.
					Die Datei, aus welcher die Personendaten gelesen werden.

	RETURN:			void

	***************************************************************************/

	public void loadPersonDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

			personData.clear();
			personData.addAll(wrapper.getPersons());

			// Save the file path to the registry.
			setPersonFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	/***************************************************************************

	METHODENNAME:	loadFahrzeugDataFromFile

	BESCHREIBUNG:   Lädt die Fahrzeugdaten von einer xml-Datei. Die aktuellen
					Fahrzeugdaten werden erstetzt.

	PARAMETER: 		File.
					Die Datei, aus welcher die Fahrzeugdaten gelesen werden.

	RETURN:			void

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

	METHODENNAME:	savePersonDataToFile

	BESCHREIBUNG:   Speichert die aktuellen Personendaten in eine xml-Datei.

	PARAMETER: 		File.
					Die Datei, in welcher die Personendaten gespeichert
					werden sollen.

	RETURN:			void

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

	BESCHREIBUNG:   Speichert die aktuellen Fahrzeugdaten in eine xml-Datei.

	PARAMETER: 		File.
					Die Datei, in welcher die Fahrzeugdaten gespeichert
					werden sollen.

	RETURN:			void

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
			System.out.println("Fehler");

		}
	}

	/***************************************************************************

	METHODENNAME:	showBirthdayStatistics

	BESCHREIBUNG:   Öffnet die Geburtstagstatistik im root Layout

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

	/**
	 * Opens a dialog to show birthday statistics.
	 */
	public void showBirthdayStatistics() {
		try {
			// Load the fxml file and create a new stage for the popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			rootLayout.setCenter(page);

			// Code um Statistic in eienm Popup-Fesnter zu öffnen
			// Stage dialogStage = new Stage();
			// dialogStage.setTitle("Birthday Statistics");
			// dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.initOwner(primaryStage);
			// Scene scene = new Scene(page);
			// dialogStage.setScene(scene);

			// Set the persons into the controller.
			BirthdayStatisticsController controller = loader.getController();
			controller.setPersonData(personData);

			// dialogStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}