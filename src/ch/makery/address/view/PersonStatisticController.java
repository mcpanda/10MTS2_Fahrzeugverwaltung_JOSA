/**************************************************************************************************/
/*! \file
  FILE         : $Source: PersonStatisticController.java $
  BESCHREIBUNG : Controller
                 Controller f�r die Statistik von Personen
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
import ch.makery.address.model.Person;
import ch.makery.address.model.Buchung;

/***************************************************************************
CLASS:	PersonStatisticController
*//*!
 Die Klasse PersonStatisticController hat nur einen Standardkonstruktor.

***************************************************************************/

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
    *//*!
     Initialisiert die Controller Klasse. Diese Methode wird automatisch
     aufgerufen, nachdem die fxml Datei geladen wurde.

     \param   void

     \return  void

    ***************************************************************************/

    @FXML
    private void initialize() {
    	personenNamenList.add("test");

    	xAxis.setCategories(personenNamenList);
    }

    /***************************************************************************
    METHODENNAME:	setPersonStatistic
    *//*!
     l�dt die Personen f�r die Statistik.

     \param   List<Person>, List<Buchung>

     \return  void

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

/** @}*/ /*end of doxygen group*/