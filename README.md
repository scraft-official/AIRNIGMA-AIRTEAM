
# CZYM JEST APLIKACJA AIRNIGMA?
**AirNigma** to aplikacja stworzona do szyfrowania i odszyfrowywania wiadomości. Pozwala ona na przetwarzanie wiadomości za pomocą szyfrów Cezara, Vernama, AES, DES, TRIPLE-DES, TWOFISH i RSA.
Aplikacja umożliwia ustawienie różnych opcji dla każdego z szyfrów, które pozwalają na dodatkowe zabezpieczenie szyfrowanej wiadomości, poprzez np. zmianę wielkości bloku klucza szyfrującego.
Zaszyfrowaną wiadomość lub klucz można zapisać na dysku, w celu późniejszego importowania.


# INSTALACJA
### WYMAGANIA SYSTEMOWE:
   - Windows 10 (32 bit / 64 bit)*,
   - 2GB+ RAM,
   - ~180+ MB Przestrzeni Dyskowej

`* Aplikacja była testowana na wersji systemu Windows 10, lecz niewykluczone jest wsparcie, także dla innych wersji systemów Windows.`

### SPOSÓB INSTALACJI:
1.	Należy pobrać instalator aplikacji wedle korzystanej wersji architektury systemu, na której będzie instalowana aplikacja. (32bit lub 64bit). [POBIERZ GO TUTAJ](https://github.com/scraft-official/AIRNIGMA-AIRTEAM/releases/latest)
2.	Po pobraniu, należy otworzyć instalator za pomocą dwukrotnego kliknięcia.
3.	Gdy instalator się otworzy, należy postępować zgodnie ze wskazówkami jakie będą wyświetlane.
4.	Po zainstalowaniu, jeżeli została wybrana opcja „Utwórz ikonę Pulpitu”, to skrót aplikacji można znaleźć na pulpicie lub wyszukując go po nazwie w wyszukiwarce aplikacji.

# DOKUMENTACJA APLIKACJI
Dokumentacje aplikacji znajdziesz tutaj: [AIRNIGMA - DOKUMENTACJA.pdf](https://github.com/scraft-official/AIRNIGMA-AIRTEAM/files/8192467/AIRNIGMA.-.DOKUMENTACJA.pdf)

# BUDOWANIE APLIKACJI
### DO ZBUDOWANIA APLIKACJI POTRZEBNE BĘDZIE:
   - [Apache Maven](https://maven.apache.org/download.cgi)
   - [JDK 11](https://www.oracle.com/pl/java/technologies/javase/jdk11-archive-downloads.html)

### JAK ZBUDOWAĆ?
   - Należy pobrać pliki tego repozytorium. (Przycisk "CODE" > Download ZIP)
   - Pobrane repozytorium należy rozpakować.
   - Wewnątrz wypakowanego folderu należy uruchomić CMD (Command Prompt) i wywołać komendę\
   ```mvn clean install```
   - Gotowe pliki aplikacji znajdują się w lokalizacji **/target/**
   
   `* Plik z koncówką -shaded.jar jest to plik JAR aplikacji ze wszystkimi bibliotekami.`\
   `* Plik z koncówką .exe jest to plik EXE aplikacji ze wszystkimi bibliotekami.`\
   `* Plik zakończony tylko .jar jest to plik JAR aplikacji bez bibliotek. (Brak możliwości uruchomienia)`

# DODATKOWE INFORMACJE:
* Wykorzystane w aplikacji ikony zostały pobrane ze strony https://www.flaticon.com
* Testy automatyczne znajdują się w katalogu plików źródłowych „SRC/TEST/JAVA” i są uruchamiane podczas eksportowania aplikacji za pomocą narzędzia Apache Maven.
