
	// Instancio e inicializo una constante en el módulo de servicios. Par (clave, valor)
servicesModule.constant('baseUrl', 'http://localhost:8080/Bender/rest');

	// Instancio un servicio en el módulo de servicios. 
	//	Le paso el elemento angular $http y la  
servicesModule.service("backcalls", function ($http, baseUrl){
	console.log("backcalls init");
	
	
		// creamos una función que será utilizable desde aquellos elementos que tengan 'visibilidad'
	var getRandomName = function (callback){
		console.log("backcalls getting random name");
			//	Utilizamos el objeto Angular $http para invocar un get al backend.
			//	Este es el objeto Angular que se encarga de las llamadas ajax y metodos get, post, put y delete.
			//	Utilizamos la url del backend que hemos almacenado en la constante del servicio.
		$http.get(baseUrl+"/Player/getRandomName")
				// Aplicará esta función si la llamada tiene exito (200 OK)
					//	Los parámetros son los que devuelve el backend.
					//	La función callback es la que ha recibido como parámetro (getRandomName) en este caso.
					//		Esta definida en la llamada (homeCtrl).
			.success(function (data,status,headers,config){
					callback(data);
				})
					//	Esta funcion se ejecuta si hay algun error en el proceso de la llamada
			.error(function (data, status, headers,config){
					alert(status);
				});
	
	};
	
	var getCompletePlayer = function (player, callback){
		console.log("backcalls getting complete player");
		console.log(player);
		$http.post(baseUrl+"/Player/completePlayer", player)
			.success(function(data,status,headers,config){
				callback(data);
				})
			.error(function (data,status,headers,config){
				alert(status);
				});
	};
	
		//	Publicamos el API del servicio. Estas serán las funcionalidades que se pueden invocar desde fuera.
	return {
		getRandomName: getRandomName,
		completePlayer: getCompletePlayer
	};
	
});