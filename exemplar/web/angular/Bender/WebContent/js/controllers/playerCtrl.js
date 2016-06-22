'use strict';

controllersModule.controller("playerCtrl", function ($scope, $rootScope){
	console.log("player control loaded");
	
	var ctrlPlayerName = "Controller Player Name ";
	$rootScope.rootScopePlayerName = "root Scope Player Name";
	
	
	$scope.controllerPlayerName = function (){
		return ctrlPlayerName+" !";
	};
	
	
});