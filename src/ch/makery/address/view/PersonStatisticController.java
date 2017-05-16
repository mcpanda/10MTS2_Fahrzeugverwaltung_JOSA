/**************************************************************************************************/
/*! \file
  FILE         : $Source: PersonStatisticController.java $
  BESCHREIBUNG : Controller
                 Controller fuer die Statistik von Personen
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
     laedt die Personen fuer die Statistik.

     \param   List<Person>, List<Buchung>

     \return  void

    ***************************************************************************/

    public void setPersonStatistic(List<Person> persons, List<Buchung> buchungs) {
    	personenNamenList.clear();

    	/* erstelle Array mit den PersonenIDs und der Leihdauer */
        int[][] ausleihe = new int[2][persons.size()];

        for( int i= 0; i < ausleihe[0].length; i++) {
        	ausleihe[0][i]= persons.get(i).getPersonID();
        	ausleihe[1][i]= 0;
        }

        for (Buchung b : buchungs) {
        	for( int i= 0; i < ausleihe[0].length; i++) {
        		if(ausleihe[0][i] ==  b.getPersonID()) {
        			ausleihe[1][i] += b.getLeihdauer();
        		}
        	}
        }

        /* sortiere das Array nach der Leihdauer; absteigend */
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

    	/* nehme nur 10 Personen in die Statistik auf */
    	int darstellungsgroesse= 10;
    	if (darstellungsgroesse > ausleihe[0].length) {
    		darstellungsgroesse= ausleihe[0].length;
    	}
    	for (int i= 0; i < darstellungsgroesse; i++) {
    		for (Person p : persons) {
    			if (ausleihe[0][i] == p.getPersonID()) {
    				personenNamenList.add(persons.get(i).getFirstName() + " " + persons.get(i).getLastName());
    			}
    		}
    	}

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        barChart.setTitle("Personen - durchschnittliche Ausleihdauer");
        barChart.setLegendVisible(false);


        // Fuege die Daten zur Statistik hinzu
        for (int i = 0; i < personenNamenList.size(); i++) {
        	series.getData().add(new XYChart.Data<>(personenNamenList.get(i), ausleihe[1][i]));
        }

        barChart.getData().add(series);
    }
}

/** @}*/ /*end of doxygen group*/