WeatherApp.jar

WeatherApp.jar est une application de météo. Lorsque la méthode doRefresh() du composant WeatherComponent est appélée, le composant recense des données météo à partir du site https://openweathermap.org/ sous format JSON. Le composant utilise une libraire externe pour lire et extraire les données JSON: json-simple qui est sous license Apache License 2.0.

Le composant a un événement principal : RefreshEvent. L'application a deux boutons: Start et Refresh.

Lorsque l'utilisateur appuie sur Start, le composant lance un Thread et génère un événement RefreshEvent toutes les deux minutes par défaut. Les RefreshListener appelent la méthode doRefresh() de WeatherComponent lorsqu'ils reçoivent un événement RefreshEvent.

Le bouton Refresh appelle directement la méthode doRefresh().