/**************************************************************************************************/
/*! \file
  FILE         : $Source: PersonStatisticController.java $
  BESCHREIBUNG : Controller
                 Controller für die Statistik von Personen
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

    	xAxis.setCategories(fahrzeugTypenList);
    }

    /***************************************************************************
    METHODENNAME:	setFahrezugTypStatistic
    *//*!
     lädt die Fahrzeuge für die Statistik.
     Codekommentierung: siehe FahrzeugStatisticController

     \param   List<Fahrzeug>, List<Fahrzeug>

     \return  void

    ***************************************************************************/

    public void setFahrzeugTypStatistic(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

        int[] typen= new int[fahrzeugTypenList.size()];

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

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < fahrzeugTypenList.size(); i++) {
            series.getData().add(new XYChart.Data<>(fahrzeugTypenList.get(i), typen[i]));
        }

        barChart.getData().add(series);
    }

    /***************************************************************************
    METHODENNAME:	setFahrezugTypTageStatistic
    *//*!
     lädt die Fahrzeuge für die Statistik.
     Codekommentierung: siehe FahrzeugStatisticController

     \param   List<Fahrzeug>, List<Fahrzeug>

     \return  void

    ***************************************************************************/

    public void setFahrzeugTypTageStatistic(List<Fahrzeug> fahrzeugs, List<Buchung> buchungs) {

        int[][] typen= new int[3][fahrzeugTypenList.size()];

        for (Fahrzeug f : fahrzeugs) {
        	for(int i= 0; i < fahrzeugTypenList.size(); i++) {
        		if(fahrzeugTypenList.get(i).equals(f.getFahrzeugtyp())) {
        			for(Buchung b : buchungs) {
        				if(b.getFahrzeugID() == f.getFahrzeugID()) {
        					typen[1][i]++;
                			typen[0][i]= typen[2][i] + b.getLeihdauer();
        				}
        			}
        		}
        	}
        }

        for(int i= 0; i < fahrzeugTypenList.size(); i++) {
        	if (typen[1][i] != 0) {
        		typen[2][i]= typen[0][i]/typen[1][i];
        	} else {
        		typen[2][i]= 0;
        	}
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < fahrzeugTypenList.size(); i++) {
            series.getData().add(new XYChart.Data<>(fahrzeugTypenList.get(i), typen[2][i]));
        }

        barChart.getData().add(series);
    }
}

/** @}*/ /*end of doxygen group*/