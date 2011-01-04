Testowanie aplikacji

Przed przystąpieniem do testowania należy przygotować bazę danych. W tym
celu wybieramy konkretny system (aktualnie może to być tylko PostgreSQL 8.2
lub nowszy). Z katalogu $SOURCE_HOME/sql/postgresql/ potrzebny będzie plik
INSTALL.SQL. Zawiera on skrypt DDL tworzący początkową strukturę bazy danych, tj:
 * domeny SQL
 * sekwencje
 * relacje
 * definicje procedur
 * definicje wyzwalaczy
a także ustawiający początkowe wartości jeśli takie są potrzebne. W szczególności
dodaje pierwszego użytkownika systemu (jest to użytkownik 'admin' z hasłem 'admin').
Jest dość dokładnie skomentowany więc wszelkie problemy (które nie powinny wystąpić)
najpewniej da się rozwiązać przeglądając komentarze.

Mając przygotowaną bazę danych udostępniamy informacje o niej programowi (jeśli nic
nie powiemy przyjmie domyślnie poniższe dane
* host: localhost
* user: helpdesk
* pass: helpdesk
* db:   helpdesk_test
- w środowisku rozwojowych ostatnia zmienna ma wartość 'helpdesk') lub zdajemy się
na wartości domyślne.

Teraz w oknie terminala (lub za pośrednictwem IDE, z którego korzystamy) wydajemy
polecenie:

%mvn test

Jeśli po niedługim czasie (zależy on dość mocno od maszyny, na której pracujemy) pojawi się
powrót do linii poleceń a tuż przed nim odnajdziemy linię podobną do poniższej:

...
[INFO] BUILD SUCCESSFUL
...

to znaczy, że wszystko działa jak należy. 