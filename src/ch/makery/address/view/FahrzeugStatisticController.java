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
/* Class BirthdayStatisticsController                                     */
/*																		  */
/*                                                                        */
/**************************************************************************/

public class FahrzeugStatisticController {

	private ObservableList<String> herstellerMarkenList = FXCollections.observableArrayList();

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
    	herstellerMarkenList.add("test");

    	xAxis.setCategories(herstellerMarkenList);
    }

    /***************************************************************************

	METHODENNAME:	setFahrezugStatistik

	BESCHREIBUNG:   lädt die Persoen für die Statistik.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void setFahrzeugStatistik(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

        int[][] ausleihe = new int[2][fahrzeugs.size()];
        herstellerMarkenList.clear();
        for( int i= 0; i < ausleihe[0].length; i++) {
        	ausleihe[0][i]= fahrzeugs.get(i).getFahrzeugID();
        	herstellerMarkenList.add(fahrzeugs.get(i).getHersteller() + " " + fahrzeugs.get(i).getMarke());
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
        for (int i = 0; i < herstellerMarkenList.size(); i++) {
            series.getData().add(new XYChart.Data<>(herstellerMarkenList.get(i), ausleihe[1][i]));
        }

        barChart.getData().add(series);
    }




}
