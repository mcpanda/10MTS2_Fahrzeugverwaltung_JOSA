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
/* Class FahrzeugStatisticController                                      */
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

	METHODENNAME:	setFahrezugStatistic

	BESCHREIBUNG:   l�dt die Fahrzeuge und Buchungseintr�ge f�r die Statistik.


	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void setFahrzeugStatistic(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

    	// 2 dim. Array
    	// 1. Zeile: FahrzeugID
    	// 2. Zeile: dazugeh�rige Ausleihh�ufigkeit
        int[][] ausleihe = new int[2][fahrzeugs.size()];
        herstellerMarkenList.clear();
        for( int i= 0; i < ausleihe[0].length; i++) {
        	ausleihe[0][i]= fahrzeugs.get(i).getFahrzeugID();	// f�gt in die erste Zeile alle vorhandenen FahrzeugIDs
        	herstellerMarkenList.add(fahrzeugs.get(i).getHersteller() + " " + fahrzeugs.get(i).getMarke()); // f�gt in die Liste die Fahrzeugbeschreibung hinzu
        }
        for (Buchung b : buchungs) {
        	int gebucht= b.getFahrzeugID();
        	for( int i= 0; i < ausleihe[0].length; i++) {
        		if( gebucht == ausleihe[0][i]) {				// falls die FahrzeugID in einem Buchungseintrag auftaucht, so wird die dazugeh�rige Ausleihh�ufigkeit um 1 erh�ht
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

    /***************************************************************************

	METHODENNAME:	setFahrezugTageStatistic

	BESCHREIBUNG:   l�dt die FahrzeugTypen f�r die Statistik.

	PARAMETER: 		void

	RETURN:			void

	***************************************************************************/

    public void setFahrzeugTageStatistic(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

    	// 4 dim. Array
    	// 1. Zeile: FahrzeugID
    	// 2. Zeile: dazugeh�rige Gesamtausleihtage
    	// 3. Zeile: dazugeh�rige Ausleihh�ufigkeit
    	// 4. Zeile: Quotient, falls Ausleihh�ufigkeit != 0 ist

        int[][] ausleihe = new int[4][fahrzeugs.size()];
        herstellerMarkenList.clear();
        for( int i= 0; i < ausleihe[0].length; i++) {
        	ausleihe[0][i]= fahrzeugs.get(i).getFahrzeugID();	// f�gt in die erste Zeile alle vorhandenen FahrzeugIDs
        	herstellerMarkenList.add(fahrzeugs.get(i).getHersteller() + " " + fahrzeugs.get(i).getMarke());	// f�gt in die Liste die Fahrzeugbeschreibung hinzu
        }

        for (Buchung b : buchungs) {
        	int gebucht= b.getFahrzeugID();
        	for( int i= 0; i < ausleihe[0].length; i++) {
        		if( gebucht == ausleihe[0][i]) {				// falls die FahrzeugID in einem Buchungseintrag auftaucht, so wird die dazugeh�rige Ausleihh�ufigkeit um 1 erh�ht und die Ausleihdauer um die ausgeliehenen Tage
        			ausleihe[2][i]++;
        			ausleihe[1][i]= ausleihe[1][i] + b.getLeihdauer();
        		}
        	}
        }

        for ( int i= 0; i < ausleihe[0].length; i++) {
        	if (ausleihe[2][i] != 0) {							// falls Ausleihh�ufigkeit != 0 ist, so wird der Quotient aus Ausleihdauer und Ausleihh�ufigkeit gebildet
        		ausleihe[3][i]= ausleihe[1][i]/ ausleihe[2][i];
        	} else {
        		ausleihe[3][i]= 0;
        	}
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < herstellerMarkenList.size(); i++) {
            series.getData().add(new XYChart.Data<>(herstellerMarkenList.get(i), ausleihe[3][i]));
        }

        barChart.getData().add(series);
    }
}
