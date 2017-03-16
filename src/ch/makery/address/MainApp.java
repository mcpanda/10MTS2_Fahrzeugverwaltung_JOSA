package ch.makery.address;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ch.makery.address.model.Person;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.FahrzeugEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.FahrzeugOverviewController;
import java.io.File;
import java.util.prefs.Preferences;
import javax.xml.bind.JAXBContext;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.model.FahrzeugListWrapper;
import javax.xml.bind.Unmarshaller;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.xml.bind.Marshaller;
import ch.makery.address.view.RootLayoutController;
import ch.makery.address.view.BirthdayStatisticsController;


public class MainApp extends Application {

    private Stage primaryStage;
    private static BorderPane rootLayout;

    public static BorderPane getRootLayout() {
    	return rootLayout;
    }

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private ObservableList<Fahrzeug> fahrzeugData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        personData.add(new Person("Olga", "Neuer", "Siemensstrasse 12", 93055, "Regensburg"));
        personData.add(new Person("Mohammad", "Hummels", "Siemensstrasse 12", 93055, "Regensburg"));
        personData.add(new Person("Martin", "Boateng", "Siemensstrasse 12", 93055, "Regensburg"));
        personData.add(new Person("Korbinian", "Lahm", "Siemensstrasse 12", 93055, "Regensburg"));
        personData.add(new Person("Nichlas", "Hoewedes", "Siemensstrasse 12", 93055, "Regensburg"));
        personData.add(new Person("Aileen", "Khedira", "Siemensstrasse 12", 93055, "Regensburg"));
        personData.add(new Person("Murat", "Schweinsteiger", "Siemensstrasse 12", 93055, "Regensburg"));
        personData.add(new Person("hmm", "Mueller", "Siemensstrasse 12", 93055, "Regensburg"));
        personData.add(new Person("Irena", "Oezil", "Alfred-Teves-Strass 11", 38518, "Gifhorn"));
        personData.add(new Person("Janes", "Klose", "Alfred-Teves-Strass 11", 38518, "Gifhorn"));
        personData.add(new Person("Michelle", "Goetze", "Alfred-Teves-Strass 11", 38518, "Gifhorn"));
        personData.add(new Person("Erik", "Schuerle", "Alfred-Teves-Strass 11", 38518, "Gifhorn"));
        personData.add(new Person("Felix", "Krammer", "Alfred-Teves-Strass 11", 38518, "Gifhorn"));
        personData.add(new Person("Micky", "Mustafi", "Vahrenwalder Str. 9", 30165, "Hannover"));
        personData.add(new Person("Fabian", "Weidenfeller", "Vahrenwalder Str. 9", 30165, "Hannover"));
        personData.add(new Person("Bilal", "Durm", "Heinrich-Hertz-Strasse 45", 78052, "Villingen"));
        personData.add(new Person("Julia", "Mertesacker", "Sieboldstrasse 19", 90411, "Nuernberg"));
        personData.add(new Person("Simon", "Ginter", "Sieboldstrasse 19", 90411, "Nuernberg"));
        personData.add(new Person("Alexander", "Kroos", "Sieboldstrasse 19", 90411, "Nuernberg"));
    }

    /**
     * Returns the data as an observable list of Persons.
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public ObservableList<Fahrzeug> getFahrzeugData() {
        return fahrzeugData;
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
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

    public boolean showFahrzeugEditDialog(Fahrzeug fahrzeug) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FahrzeugEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Fahrzeug Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
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

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Fuhrparkverwaltung");

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
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

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
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

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
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

    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
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

    /**
     * Saves the current person data to the specified file.
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
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

    /**
     * Opens a dialog to show birthday statistics.
     */
    public void showBirthdayStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}