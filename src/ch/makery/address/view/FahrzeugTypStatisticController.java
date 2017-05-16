/**************************************************************************************************/
/*! \file
  FILE         : $Source: PersonStatisticController.java $
  BESCHREIBUNG : Controller
                 Controller fuer die Statistik von Fahrzeugtypen
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
CLASS:	FahrzeugTypStatisticController
*//*!
 Die Klasse FahrzeugTypStatisticController hat nur einen Standardkonstruktor.

***************************************************************************/

public class FahrzeugTypStatisticController {

	private ObservableList<String> fahrzeugTypenList = FXCollections.observableArrayList();
	private ObservableList<String> fahrzeugTypenListSorted = FXCollections.observableArrayList();

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
    	fahrzeugTypenList.addAll("Motorrad", "Cityflitzer", "Langstrecke", "Kleintransporter", "LKW");
    	fahrzeugTypenListSorted.addAll("Motorrad", "Cityflitzer", "Langstrecke", "Kleintransporter", "LKW");

    	xAxis.setCategories(fahrzeugTypenListSorted);
    }

    /***************************************************************************
    METHODENNAME:	setFahrezugTypStatistic
    *//*!
     laedt die Fahrzeuge fuer die Statistik.
     Codekommentierung: siehe FahrzeugStatisticController

     \param   List<Fahrzeug>, List<Fahrzeug>

     \return  void

    ***************************************************************************/

    public void setFahrzeugTypStatistic(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

        int[] typen= new int[fahrzeugTypenList.size()];

        /* berechne Ausleihhaeufigkeit */
        for (Fahrzeug f : fahrzeugs) {
        	for(int j= 0; j < fahrzeugTypenList.size(); j++) {
        		if(fahrzeugTypenList.get(j).equals(f.getFahrzeugtyp())) {
        			for (Buchung b : buchungs) {
        				if(f.getFahrzeugID() == b.getFahrzeugID()) {
        					typen[j]++;
        				}
        			}
        		}
        	}
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        barChart.setTitle("Fahrzeugtyp - Ausleihhaeufigkeit");
        barChart.setLegendVisible(false);

        // Nehme Daten in die Statistik auf
        for (int i = 0; i < fahrzeugTypenList.size(); i++) {
            series.getData().add(new XYChart.Data<>(fahrzeugTypenList.get(i), typen[i]));
        }

        barChart.getData().add(series);
    }

    /***************************************************************************
    METHODENNAME:	setFahrezugTypTageStatistic
    *//*!
     laedt die Fahrzeuge fuer die Statistik.
     Codekommentierung: siehe FahrzeugStatisticController

     \param   List<Fahrzeug>, List<Fahrzeug>

     \return  void

    ***************************************************************************/

    public void setFahrzeugTypTageStatistic(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

    	// 4 dim. Array
    	// 1. Zeile: Gesamtausleihtage
    	// 2. Zeile: Ausleihhaeufigkeit
    	// 3. Zeile: Quotient, falls Ausleihhaeufigkeit != 0 ist
    	// 4. Zeile: Fahrzeugtyp
        int[][] typen= new int[4][fahrzeugTypenList.size()];

        for (int i= 0; i < fahrzeugTypenList.size(); i++) {
        	for (Fahrzeug f : fahrzeugs) {
        		if(fahrzeugTypenList.get(i).equals(f.getFahrzeugtyp())) {
        			for(Buchung b : buchungs) {
        				if(b.getFahrzeugID() == f.getFahrzeugID()) {
        					typen[1][i]++;
                			typen[0][i]+= b.getLeihdauer();
        				}
        			}
        		}
        	}
        	typen[3][i]= i;
        }

        for(int i= 0; i < fahrzeugTypenList.size(); i++) {
        	if (typen[1][i] != 0) {
        		typen[2][i]= typen[0][i]/typen[1][i];
        	} else {
        		typen[2][i]= 0;
        	}
        }

        /* Sortierung; absteigend */
        int [][] Platzhalter= new int[2][1];

    	for (int i= 0; i < typen[0].length - 1; i++) {
    		int maxIndex= i;
    		Platzhalter[0][0]= typen[3][i];
    		Platzhalter[1][0]= typen[2][i];

    		for (int j= i + 1; j < typen[0].length; j++) {
    			if (Platzhalter[1][0] < typen[2][j]) {
    				maxIndex= j;
    				Platzhalter[0][0]= typen[3][j];
    	   			Platzhalter[1][0]= typen[2][j];
    			}
    		}

    		typen[3][maxIndex]= typen[3][i];
       		typen[2][maxIndex]= typen[2][i];

       		typen[3][i]= Platzhalter[0][0];
        	typen[2][i]= Platzhalter[1][0];
    	}

    	/* abhaengig von der Zahl in der 4. Zeile, werden die Fahrzeugtypen in die Liste aufgenohmen */
    	fahrzeugTypenListSorted.clear();
    	for (int i= 0; i < typen[0].length; i++) {
    		switch (typen[3][i]) {
    		case 0:
    		{
    			fahrzeugTypenListSorted.add("Motorrad");
    			break;
    		}
    		case 1:
    		{
    			fahrzeugTypenListSorted.add("Cityflitzer");
    			break;
    		}

    		case 2:
    		{
    			fahrzeugTypenListSorted.add("Langstrecke");
    			break;
    		}
			case 3:
			{
				fahrzeugTypenListSorted.add("Kleintransporter");
    			break;
    		}
			case 4:
			{
				fahrzeugTypenListSorted.add("LKW");
    			break;
    		}
    		default: break;
    		}
    	}

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        barChart.setTitle("Fahrzeugtyp - durchschnittliche Ausleihdauer");
        barChart.setLegendVisible(false);

        // Nehme Daten in die Statistik auf
        for (int i = 0; i < fahrzeugTypenListSorted.size(); i++) {
            series.getData().add(new XYChart.Data<>(fahrzeugTypenListSorted.get(i), typen[2][i]));
        }

        barChart.getData().add(series);
    }
}

/** @}*/ /*end of doxygen group*/