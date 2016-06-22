'use strict';

	// dentro del módulo de controladores (definido en app.js) instancio un controlador.
	//	el controlador se llama "homeCtrl" y tiene acceso a los recursos: $rootScope, $scope, $routeParms y backcalls
	//		Backcalls es un servicio definido con ese nombre en backcalls.js
controllersModule.controller("homeCtrl", function ($rootScope, $scope, $routeParams, backcalls, $route){
	
			//	Estas funciones se ejecutan al iniciarse el controlador.
		console.log("> homeCtrl init");
			//	Instancio y asigno valor a una variable en el rootScope, que será visible desde cualquier elemento de Angular
		$rootScope.currentView = "homeView";
			//	Instancio y asigno valor a una variable en el Scope del controlador.
		$scope.playerName = "noName";
			//	mas de lo mismo
		$scope.player = {
				name : "otherPlayerName",
				type : "uncomplete"
		};
		
			//	Instancio una funcionalidad que se publica en el Scope del controlador para que cualquiera dentro de el
			//		pueda recurrir a ella.
		$scope.getPlayerName = function(){
			var playerName = "searching name";
			alert (playerName);
				//	Realiza una llamada al servicio backcalls (recibido por parámetro); En este caso a la función getRandomName
			backcalls.getRandomName(
						//	Defino como parámetro la función de callback a la que llamará cuando haya terminado.
					function (data){
						console.log("backcalls callback");
						console.log(data);
						$scope.playerName = data;
					}) ;
		};
		
		$scope.completePlayer = function (){
			backcalls.completePlayer(
				$scope.player,
				function (data){
					console.log(data.name);
					console.log(data.type);
					$scope.player = data;
				}
			);
		};
		
		
		


		
	
});