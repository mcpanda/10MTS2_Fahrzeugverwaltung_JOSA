package ch.makery.address.view;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import ch.makery.address.model.Person;
import ch.makery.address.model.Buchung;

/**************************************************************************/
/*                                                                        */
/* Class PersonStatisticController                                        */
/*																		  */
/*                                                                        */
/**************************************************************************/

public class PersonStatisticController {

	private ObservableList<String> personenNamenList = FXCollections.observableArrayList();

	/**************************************************************************/
	/*                                                                        */
	/* FXML Section                                                           */
	/*                                                                        */
	/**************************************************************************/

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

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
    	personenNamenList.add("test");

    	xAxis.setCategories(personenNamenList);
    }

    /***************************************************************************

	METHODENNAME:	setPersonStatistic

	BESCHREIBUNG:   lädt die Personen für die Statistic.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void setPersonStatistic(List<Person> persons, List<Buchung> buchungs) {

        int[][] ausleihe = new int[2][persons.size()];
        personenNamenList.clear();
        for( int i= 0; i < ausleihe[0].length; i++) {
        	ausleihe[0][i]= persons.get(i).getPersonID();
        	personenNamenList.add(persons.get(i).getFirstName() + " " + persons.get(i).getLastName());
        }
        for (Buchung b : buchungs) {
        	int gebucht= b.getFahrzeugID();
        	for( int i= 0; i < ausleihe[0].length; i++) {
        		if( gebucht == ausleihe[0][i]) {
        			ausleihe[1][i]++;
        		}
        	}
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < personenNamenList.size(); i++) {
            series.getData().add(new XYChart.Data<>(personenNamenList.get(i), ausleihe[1][i]));
        }

        barChart.getData().add(series);
    }



}
