## Phase 4: DTO und Mapper

### Ziel
Trennen von API und Datenmodell.

---

### Aufgabe 12: DTOs
- Erstelle alle notwendigen DTOs entsprechend dem [UML-Diagramm](./uml-diagramm/Event-Buddy-2026-03-27-111038.png).
<details>
    <summary>Eine kleine Hilfe</summary>

- EventRequestDTO  

- EventResponseDTO  

- ParticipantDTO
    </details>

- Überlege welche Werte hier sinnvoll sind und welche wir nicht ins DTO übernehmen
- Erstelle für alle sinnvollen Felder Inputvalidierung per Spring Validation.
---

### Aufgabe 13: Implementieren von Mapper Klassen
- Implementiere einen EventMapper, welcher (Request)DTOs in Events und Events in (Response)DTOs umwandelt.
- Implementiere einen ParticipantMapper, welcher (Request)DTOs in Participants umwandelt.
- Brauchst du noch mehr Mapper? Wenn ja: Lege sie ebenfalls an!
- Versehe deine Mapper Klassen mit der `@Component` Annotation und binde sie per Dependency Injection in die entsprechenden Services ein.

---

### Aufgabe 14: Controller anpassen
- Passe die Controller so an, dass ausschließlich mit DTOs gearbeitet wird.

---

### Testing
- Schreibe/update deine Tests (früher oder später musst du sie eh schreiben ;-) )

---
