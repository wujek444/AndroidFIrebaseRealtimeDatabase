# AndroidFIrebaseRealtimeDatabase

Jak to gubienko odpalić?
1. odpalić cmd jako admin, bo inaczej będzie krzyczało
2. (tylko jeden raz) zainstalować parse-server (nie jestem pewien czy mongodb-runner jest potrzebny, ale tak było na stronce): <br/>
	 npm install -g parse-server mongodb-runner
3. odpalić serwer lokalnie z cmd:<br/>
	parse-server --appId parseTest --masterKey master --databaseURI mongodb://user:user@ds125365.mlab.com:25365/parse-server-test
	
	gdzie:
	- to po --appId to id apki, które jest używane do zainicjalizowania serwera z poziomu apki<br/>
	- to po --masterKey to taki klucz admina<br/>
	- to po --databaseURL to adres bazy mongodb, którą utworzyłem w chmurze (nazwa_uzytkownika:haslo@)<br/>
4. zainstalować dashboard'a, na którym będziemy mogli przeglądać dane i zarządzać bazą:<br/>
	npm install -g parse-dashboard<br/>
5. odpalić dashboard'a:<br/>
	parse-dashboard --appId parseTest --masterKey master --serverURL  http://localhost:1337/parse<br/>
6. dashboard będzie teraz dostępny prawdopodobnie pod adresem http://localhost:4040/<br/>

Działa dodawanie tasków i wyświetlanie ich z poziomu apki.

