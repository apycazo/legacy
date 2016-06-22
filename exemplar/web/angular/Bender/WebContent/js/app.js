/*  App Module */

	// El código en Angular se puede organizar en módulos.
	// Aquí creamos un módulo en la variable app. El módulo se llama Bender y tiene visibles para hacer llamadas los 
	// 	módulos Controllers y Services (que se crean más abajo)
var app = angular.module('Bender', ['Bender.Controllers','Bender.Services']);

	// Como sistema de organización ( el que he querido ) he creado un módulo para los controladores y otro para los servicios
var controllersModule = angular.module('Bender.Controllers',['Bender.Services']);
var servicesModule = angular.module('Bender.Services',[]);


	//	Inicializamos el módulo Bender contenido en la variable 'app'
	//	Le paso la variable general de Angular $routeProvider para que pueda hacer uso de ella.
	//
	// Otro cambio superfluo
app.config(function ($routeProvider){
	console.log("> app.config init");	
	
	//	Gracias a esta variable puedo actuar sobre la barra de navegación.
	$routeProvider.
		// si la ruta actual es /home aplica:
		when('/',{
				// en la sección ng-view carga este código
			templateUrl: 'partials/home.html',
				// el controlador asignado a la sección ng-view es este
			controller: 'homeCtrl',
				// la variable (definida por mi por mi cara bonita) toma este valor...
			activeView: 'home'
		}).
		when('/player', {
			templateUrl: 'partials/player.html',
			controller: 'playerCtrl',
			activeView: 'player'
		}).
		when('/err', {
			templateUrl: 'partials/err404.html',
			activeView: 'error'
		}).
		when('/noWorks',{
			templateUrl: 'partials/noWorks.html',
			activeView: 'fool'
		}).
		//	en otro caso
		otherwise({
			// que cambie la ruta y redirija a:
			redirectTo: '/err'
		});
	
});