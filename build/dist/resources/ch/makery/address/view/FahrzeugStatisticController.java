/**************************************************************************************************/
/*! \file
  FILE         : $Source: FahrzeugStatisticController.java $
  BESCHREIBUNG : Controller
                 Controller fuer die Statistik von Fahrzeugen
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

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.model.Buchung;

/***************************************************************************
CLASS:	FahrzeugStatisticController
*//*!
 Die Klasse PersonStatisticController hat nur einen Standardkonstruktor.

***************************************************************************/

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
    *//*!
     Initialisiert die Controller Klasse. Diese Methode wird automatisch
     aufgerufen, nachdem die fxml Datei geladen wurde.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void initialize() {
    	herstellerMarkenList.add("test");

    	xAxis.setCategories(herstellerMarkenList);
    }

    /***************************************************************************
    METHODENNAME:	setFahrezugStatistic
    *//*!
     laedt die Fahrzeuge fuer die Statistik.

     \param   List<Fahrzeug>, List<Fahrzeug>

     \return  void

    ***************************************************************************/

    public void setFahrzeugStatistic(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

    	// 2 dim. Array
    	// 1. Zeile: FahrzeugID
    	// 2. Zeile: dazugehoerige Ausleihhaeufigkeit
        int[][] ausleihe = new int[2][fahrzeugs.size()];
        herstellerMarkenList.clear();

        for( int i= 0; i < ausleihe[0].length; i++) {
        	ausleihe[0][i]= fahrzeugs.get(i).getFahrzeugID();	// fuegt in die erste Zeile alle vorhandenen FahrzeugIDs
//        	herstellerMarkenList.add(fahrzeugs.get(i).getHersteller() + " " + fahrzeugs.get(i).getMarke()); // fuegt in die Liste die Fahrzeugbeschreibung hinzu
        }
        for (Buchung b : buchungs) {
        	int gebucht= b.getFahrzeugID();
        	for( int i= 0; i < ausleihe[0].length; i++) {
        		if( gebucht == ausleihe[0][i]) {				// falls die FahrzeugID in einem Buchungseintrag auftaucht, so wird die dazugehoerige Ausleihhaeufigkeit um 1 erhoeht
        			ausleihe[1][i]++;
        		}
        	}
        }

        /* Sortierung; absteigend */
        int [][] Platzhalter= new int[2][1];

    	for (int i= 0; i < ausleihe[0].length - 1; i++) {
    		int maxIndex= i;
    		Platzhalter[0][0]= ausleihe[0][i];
    		Platzhalter[1][0]= ausleihe[1][i];

    		for (int j= i + 1; j < ausleihe[0].length; j++) {
    			if (Platzhalter[1][0] < ausleihe[1][j]) {
    				maxIndex= j;
    				Platzhalter[0][0]= ausleihe[0][j];
    	   			Platzhalter[1][0]= ausleihe[1][j];
    			}
    		}

    		ausleihe[0][maxIndex]= ausleihe[0][i];
       		ausleihe[1][maxIndex]= ausleihe[1][i];

       		ausleihe[0][i]= Platzhalter[0][0];
        	ausleihe[1][i]= Platzhalter[1][0];
    	}

    	for (int i= 0; i < ausleihe[0].length; i++) {
    		for (Fahrzeug f : fahrzeugs) {
    			if (ausleihe[0][i] == f.getFahrzeugID()) {
    				herstellerMarkenList.add(f.getHersteller() + " " + f.getMarke()); // fuegt in die Liste die Fahrzeugbeschreibung hinzu
    			}
    		}
    	}

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        barChart.setTitle("Fahrzeug - Ausleihhaeufigkeit");
        barChart.setLegendVisible(false);

        // Nehme Daten in die Statistik auf
        for (int i = 0; i < herstellerMarkenList.size(); i++) {

            series.getData().add(new XYChart.Data<>(herstellerMarkenList.get(i), ausleihe[1][i]));
        }

        barChart.getData().add(series);
    }

    /***************************************************************************
    METHODENNAME:	setFahrezugTageStatistic
    *//*!
     laedt die Fahrzeuge fuer die Statistik.

     \param   List<Fahrzeug>, List<Fahrzeug>

     \return  void

    ***************************************************************************/

    public void setFahrzeugTageStatistic(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

    	// 4 dim. Array
    	// 1. Zeile: FahrzeugID
    	// 2. Zeile: dazugehoerige Gesamtausleihtage
    	// 3. Zeile: dazugehoerige Ausleihhaeufigkeit
    	// 4. Zeile: Quotient, falls Ausleihhaeufigkeit != 0 ist

        int[][] ausleihe = new int[4][fahrzeugs.size()];
        herstellerMarkenList.clear();
        for( int i= 0; i < ausleihe[0].length; i++) {
        	ausleihe[0][i]= fahrzeugs.get(i).getFahrzeugID();	// fuegt in die erste Zeile alle vorhandenen FahrzeugIDs
        }

        for (Buchung b : buchungs) {
        	int gebucht= b.getFahrzeugID();
        	for( int i= 0; i < ausleihe[0].length; i++) {
        		if( gebucht == ausleihe[0][i]) {				// falls die FahrzeugID in einem Buchungseintrag auftaucht, so wird die dazugehoerige Ausleihhaeufigkeit um 1 erhoeht und die Ausleihdauer um die ausgeliehenen Tage
        			ausleihe[2][i]++;
        			ausleihe[1][i]= ausleihe[1][i] + b.getLeihdauer();
        		}
        	}
        }

        for ( int i= 0; i < ausleihe[0].length; i++) {
        	if (ausleihe[2][i] != 0) {							// falls Ausleihhaeufigkeit != 0 ist, so wird der Quotient aus Ausleihdauer und Ausleihhaeufigkeit gebildet
        		ausleihe[3][i]= ausleihe[1][i]/ ausleihe[2][i];
        	} else {
        		ausleihe[3][i]= 0;
        	}
        }

        /* Sortierung; absteigend */
        int [][] Platzhalter= new int[2][1];

    	for (int i= 0; i < ausleihe[0].length - 1; i++) {
    		int maxIndex= i;
    		Platzhalter[0][0]= ausleihe[0][i];
    		Platzhalter[1][0]= ausleihe[3][i];

    		for (int j= i + 1; j < ausleihe[0].length; j++) {
    			if (Platzhalter[1][0] < ausleihe[3][j]) {
    				maxIndex= j;
    				Platzhalter[0][0]= ausleihe[0][j];
    	   			Platzhalter[1][0]= ausleihe[3][j];
    			}
    		}

    		ausleihe[0][maxIndex]= ausleihe[0][i];
       		ausleihe[3][maxIndex]= ausleihe[3][i];

       		ausleihe[0][i]= Platzhalter[0][0];
        	ausleihe[3][i]= Platzhalter[1][0];
    	}

    	for (int i= 0; i < ausleihe[0].length; i++) {
    		for (Fahrzeug f : fahrzeugs) {
    			if (ausleihe[0][i] == f.getFahrzeugID()) {
    				herstellerMarkenList.add(f.getHersteller() + " " + f.getMarke()); // fuegt in die Liste die Fahrzeugbeschreibung hinzu
    			}
    		}
    	}

    	XYChart.Series<String, Integer> series = new XYChart.Series<>();
    	barChart.setTitle("Fahrzeug - durchschnittliche Ausleihdauer");
    	barChart.setLegendVisible(false);

    	// Nehme Daten in die Statistik auf
        for (int i = 0; i < herstellerMarkenList.size(); i++) {
            series.getData().add(new XYChart.Data<>(herstellerMarkenList.get(i), ausleihe[3][i]));
        }

        barChart.getData().add(series);
    }
}

/** @}*/ /*end of doxygen group*/