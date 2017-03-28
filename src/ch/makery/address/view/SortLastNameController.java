package ch.makery.address.view;

import ch.makery.address.model.Person;
import javafx.collections.ObservableList;

public class SortLastNameController {

	public void SortLastName() {

	}

	public ObservableList<Person> sortNachname (ObservableList<Person> persons) {
		Person Platzhalter= new Person();

		for (int i= 0; i < persons.size() - 2; i++) {
			int minIndex= i;
			Platzhalter.setLastName(persons.get(i).getLastName());

			for (int j= i + 1; j < persons.size(); j++) {
				if (Platzhalter.getLastName().compareTo(persons.get(j).getLastName()) > 0) {
					minIndex= j;
					Platzhalter.setLastName(persons.get(i).getLastName());
				}
			}
			persons.set(minIndex, persons.get(i));

			persons.set(i, Platzhalter);

		}
		return persons;
	}
}
