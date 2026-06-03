## Phase 7: Externe Services

### Ziel
Integration externer APIs.
Wir benötigen später aktuelle oder zukünftige Wetterinformationen, hierfür benötigen wir den 
Längen- und Breitengrad der Eventlocation.

Aus diesem Grund implementieren wir zwei RestClients

---

### Aufgabe 18: LocationService
- Implementiere einen Service zur Ermittlung von Koordinaten aus einem Ort.

Ich empfehle hier [DIESE API](https://nominatim.org/)

- Erstelle zum abfangen der Daten ein neues DTO in welchem du die relevanten Daten abspeicherst
---

### Aufgabe 19: WeatherService
- Implementiere einen Service zur Abfrage von Wetterdaten. 

Ich empfehle hier [DIESE API](https://open-meteo.com/)

---

### Aufgabe 20: Event-Empfehlungen
- Erweitere den EventService um eine Funktion, die basierend auf Wetterdaten passende Events vorschlägt.

Entscheide hier selber ob du eine Liste von verfügbaren Events anzeigt (z. B. alle mit dem Status "public") oder ein Repo mit Vorschlägen abrufst.

---

### Testing
- Schreibe Tests für deine Methoden (Spätestens jetzt musst du sie schreiben! )
- Da du nun zwei RestClients verwendest werden deine Tests mit dem normalen `@AutoConfigureMockRestServiceServer` nicht funktionieren.
- Entferne die Annotation und ergänze folgenden Code:
```java
@TestConfiguration()
    static class TestConfig {
        private final MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
        private final RestClient.Builder customizedBuilder = RestClient.builder();

        public TestConfig() {
            customizer.customize(customizedBuilder);
        }

        @Bean
        public RestClient.Builder restClientBuilder() {
            return customizedBuilder;
        }

        @Bean
        public MockRestServiceServer mockRestServiceServer() {
            return customizer.getServer(customizedBuilder);
        }
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void setUp() {
        mockRestServiceServer.reset();
    }
```
---
 