# 10MTS2_Fahrzeugverwaltung_JOSA
Schulprojekt

# History

# Version 1.6.1 07.04.2017
Anpassungen und BugFixes 
+ Buchung Nr.1 bei leerer Buchungsliste ist nun möglich
+ Formatierung für Doxygen abgeschlossen
+ Ausgeliehenzeile bei Person- und FahrzeugEdit entfernt (Falg wird über
Buchungsmenü gesetzt)

tbd:
+ ausführliche Kommentare (jede Zeile^^)

# Version 1.6.1 07.04.2017
BugFixes
+ bei der PLZ sind nur noch 5 stellige digits erlaubt
+ keine IDs < 1 mehr erlaubt
+ Leihdauer zählt nun stets +1 Tage dazu

# Version 1.6.0 06.04.2017
Anpassungen
+ Vervollständigung der Baumsuche und der Sortierungen
+ Löschung Redundanter Codezeilen beim binären Baum(Tree) / Knoten(Node)
+ die Suchanfrage muss man nicht mehr zweimal schließen
+ Fehlermeldung bei der Suche, falls keine Person gefunden wird
+ Angabe eines Zeitunterschiedes zwischen linearer Suche und Suche über binären Baum
+ Hinzufügung von weiteren Sortierungsoptionen

tbd
+ aktualisierung der ComboBox bei FahrzeugTypAuswahl (bisher funktioniert dies nur bei Tasteneinschlag im Feld Fahrzeug/Person)

# Version 1.5.0 05.04.2017 
Umfangreiche Änderung
+ Grundlegende Umstrukturierung der Klasse Buchung und BuchungsEditDialog
+ Umgehung der globalen Variable für die BuchungsID
+ ComboBox filtert nun auch nach Strings
+ Führerscheinarten mit Kombinationsmöglichkeiten erweitert
+ Die Buchungen beachten nun neben Fahrzeugtyp auch Führerscheintauglichkeit und Ausleihzustand. Ist eines nicht plausibel erfolgt eien Fehlermeldung
+ Bei der Initializierung der Buchung werden die Listen für die ComboBoxen schon geladen
+ Einschränkung der Buchung nur auf per ComboBox ausgewählten Fahrzeuge und Personen
+ Entfernung weiterer redundanten Codezeilen

tbd
+ Laufzeitanzeige zur Ermittlung des Zeitunterschiedes der Suche mit binären Bäumen und linearer Suche
+ Suche in binären Bäumen auf Nachnamen erweitern
+ ComboBox soll beim anklicken mit der Maus die Listen filtern/einlesen
+ hinzufügen der restlichen Sortierungstabellen

# Version 1.4.0 04.04.2017 
Binärer Baum mit Suchfunktion hinzugefügt.
+ Ebenso wurde redundanter Code gelöscht und die Kommentierung für DoxyGen angepasst.

# Version 1.3.5 30.03.2017
BuchungEditDialog verändert
+ Es wird nun neben der ID auch der Vor- und Nachname bzw. der Hersteller und die Marke in der gefilterten ComboBox angezeigt
+ Sortierung nach Hersteller hinzugefügt
+ Sortierung nach Marke hinzugefügt
+ Sortierung nach Kilometerstand hinzugefügt
+ Sortierung nach Leistung hinzugefügt
+ binärer Baum (Tree/Node) erstellt

# Version 1.3.4 29.03.2017
Automatisches laden von Personen udn Fahrzeug IDs
+ bei den Buchungen werden nun Personen und Buchungs IDs automatisch geladen
+ BugFixes im Sortieralgorithmus

# Version 1.3.3 29.03.2017 
BugFix: 
+ remove commons_long_jar from Library

# Version 1.3.2 29.03.2017
Selection Sort funktionsfähig
+ SelectionSort zur Sortierung nach Nachnamen abgeschloßen

tbd
+ Sortierung für Vornamen, ID, Stadt hinzufügen

# Version 1.3.1 28.03.2017
Angefangener Controller für den Sortieralgorythmus und die Suche mit binären Bäumen
+ Controller Prototyp zur Nachnamensortierung mit "funktionsfähigem" Sortieralgorythmus.
+ Suchknopf und View-Grundgerüst für die Suche über einen binären Baum

tbd
+ mit SceneBuilder Scene bereitstellen und Controller über MainApp einbinden

# Version 1.2.2 28.03.2017 
ErrorHandling nicht vorhandene IDs
+ Bei der Buchung werden nicht vorhandene Personen und Fahrzeug IDs per Fehlermeldung abgefangen
+ Filter sind nun funktionsfähig
+ Die AutoCompleteFilter bei der Eingabe der PersonenID und FahrzeugID funktionieren nun einwandfrei

# Version 1.2.1 28.03.2017
Anpassung der Person und Fahrzeug-Edits
+ Das Erstellen von Fahrzeugen und Personen mit gleicher ID erzeugt eine Fehlermeldung.

tbd
+ bessere Filterung bei der Buchung
+ Sortierung bei Eintragung neuer Personen und Fahrzeugen
+ Verknüpfung zwischen Fahrzeugtyp+Führerschein+Fahrberechtigung
+ drei Statistiken
+ Buchungsunterscheidung zwischen ausleihen, zurückgeben und ändern
+ bessere Handhabung der Buchung mit Übergabe der personenData und fahrzeugData von der MainApp per Methoden

# Version 1.0.8 27.03.2017
Neue Statistik hinzugefügt
+ Statistik über Fahrzeugtypen
+ Statistik für Fahrzeugbuchungen hinzugefügt.

# Version 1.0.7 26.03.2017 
Modifizierung
+ Buchungsmöglichkeit direkt aus Personen- und Fahrzeugansicht
+ Automatische Berechnung der Ausleihdauer
+ Setzen des Ausleihzustandes bei der Buchung, abhängig vom Rückgabedatum und aktuellem Datum

tbd
+ Filterung bei der Buchung funktioniert noch nicht einwandfrei
+ PersonenID- und FahrzeugID-Duplikate ausschließen
+ Statistiken

# Version 1.0.6 25.03.2017
Neue Attribute und ChoiceBoxes
+ Attribute fahrzeugtyp, Lizenz und Ausgeliehen hinzugefügt und bei dem EditDialog per ChoiceBox hinzugefügt.

tbd
+ per Fahrzeugtyp Personen und Fahrzeuge aussortieren
+ automatische Berechnung der Ausleihtage
+ automatische Bestimmung des Ausleizustands per Datum
+ Statistiken

# Version 1.0.5.2 25.03.2017
AutoComplete ist so weit fertig
+ AutoComplete bei der ComboBox erzeugt zwar eine Fehlermeldung, wenn man zu viele Zahlen eingibt, die Auswahl funktioniert aber denoch.
+ Die BuchungsIDs sind nicht mehr veränderbar, sonderen werden automatisch eingetragen, d.h. die BuchungsID bleibt unverändert, wenn man etwas ändern möchte, oder die globale BuchungsID wird um eins erhöht, wenn eine neue Buchugn angelegt wird.

tbd
+ Bei FahrzeugID und PersonID Duplikate ausschließen
+ Statistiken

# Version 1.0.5.1 23.03.2017
ComboBox Modifications
+ ComboBox.setEditable(true)
+ Entfernung auskommentiertem VersuchsCode

tbd
+ Autocomplete
+ Übernahme der ComboBoxauswahl in die Datenbank

# Version 1.0.4 23.03.2017 
ComboBoxen beim BuchungEditDialog hinzugefügt.
+ Bei der Buchung werden die IDs der Personen und Fahrzeuge in die ComboBox mit einem zusätzlichen Button eingelesen.

tbd
+ BuchungEditDialog soll Auswahl per ComboBox setzen
+ ComboBox mit Tastatureingabe und gleichzeitiger Auswahreduzierung verbinden
+ Die IDs aller Personen, Fahrzeuge und Buchungen sollen jeweils fortlaufend und nicht veränderbar sein
+ Auswahlfeld für das Datum
+ Automatische Berechnung der Ausleihdauer

# Version 1.0.3 22.03.2017
Verknüpfung von Fahrzeug und Person unter der Buchungsklasse
+ Anhand der PersonID und FahrzeugID werden entsprechend Namen, Vornamen, Hersteller und Marke in der Buchungsübersicht angezeigt.

# Version 1.0.2 18.03.2017
Fertigstellung vorläufiger Buchung
+ Es existieren nun drei Datenbanken (Person, Fahrzeug, Buchung), die gespeichert, bearbeitet und geladen werden können. Leider sind sie noch ohne Bezug zueinander.
+ Einheitliche Dialogfelder.
+ Erstellung von Prototypen für die Buchungen.

tbd
+ Es fehlt z.B. ein Dropboxmenü bei der Buchung für die vorhanden Personen und Fahrzeuge, sowie ein automatisches Laden der zugehörigen Namen, Vornamen, Hersteller, Marke. Ebenso fehlt eine automatische Berechnung der Leihdauer.

# Version 1.0.1 18.03.2017
Grundlage der Fuhrparkverwaltung.
+ Es werden Personen und Fahrzeuge gespeichert und können bearbeiten und geladen werden.
+ Die Daten werden in zwei xml-Dateien gespeichert.

tbd
+ Controller für Buchung Ausbleibend.

# Version 1.0.0 18.03.2017 
Rohbau der Fahrzeugverwaltung.

# Version 0.9.0 16.03.2017
Aufgabenbereiche
+ Aufgabenbereich I fertig.
+ Aufgabenbereich II angefangen.
tbd
+ Verknüpfung mit Person und Fahrzeug ausbleibend.

# Version 0.8.0 15.03.2017
Erste Version
+ Aufgabenbereich I aus vollständigem JavaFX Tutorial
+ Aufgabenbereich II angefangen

