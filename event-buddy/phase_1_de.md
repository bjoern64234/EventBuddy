# Das Ziel von Event-Buddy
Am Ende soll eine Anwendung entstehen, die:

- Events und Teilnehmer verwaltet
- Kosten berechnet und verteilt
- externe Daten einbindet
- eine einfache Benutzeroberfläche bietet

OPTIONAL:
- anbindung eines Bezahldienstes
- komplexere Benutzeroberfläche

---

## Phase 1: Grundlagen

### Ziel
Du kannst Events speichern und abrufen.

---

### Aufgabe 1: Projekt erstellen
- Erstelle ein Spring Boot Projekt mit den benötigten Dependencies.
- ConnectionString für die MongoDB in den Umgebungsvariablen verstecken

---

### Aufgabe 2: Event Model
- Erstelle die Klasse `Event` mit allen nötigen Datenfeldern basierend auf dem [UML-Diagramm](./uml-diagramm/Event-Buddy-2026-03-27-111038.png).

---

### Aufgabe 3: Server Architektur
- Erstelle ein(en) Controller, Service & Repository für Events.
- Versorge deinen Controller mit Endpunkten um Events anzulegen und abzufragen (POST, GET all, etc.)
- Versorge deinen Service mit den entsprechenden Methoden.
- Lasse deine Methoden passende Exceptions werfen.

---

### Testing
- Schreibe Tests für deine Methoden (früher oder später musst du sie eh schreiben ;-) )

---


