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
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.model.Buchung;

/**************************************************************************/
/*                                                                        */
/* Class FahrzeugTypStatisticController                                   */
/*																		  */
/*                                                                        */
/**************************************************************************/

public class FahrzeugTypStatisticController {

	private ObservableList<String> fahrzeugTypenList = FXCollections.observableArrayList();

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
    	fahrzeugTypenList.addAll("Motorrad", "Cityflitzer", "Langstrecke", "Kleintransporter", "LKW");

    	xAxis.setCategories(fahrzeugTypenList);
    }

    /***************************************************************************

	METHODENNAME:	setFahrezugStatistic

	BESCHREIBUNG:   lädt die Persoen für die Statistic.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void setFahrzeugTypStatistic(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

        int[] typen= new int[fahrzeugTypenList.size()];

        for (Buchung b : buchungs) {
        	for(int j= 0; j < fahrzeugTypenList.size(); j++) {
    			System.out.println(fahrzeugTypenList.get(j) + " = " + b.getFahrzeugtyp());
        		if(fahrzeugTypenList.get(j).equals(b.getFahrzeugtyp())) {
        			typen[j]++;
        		}
        		System.out.println(typen[j]);
        	}
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < fahrzeugTypenList.size(); i++) {
            series.getData().add(new XYChart.Data<>(fahrzeugTypenList.get(i), typen[i]));
        }

        barChart.getData().add(series);
    }

}
