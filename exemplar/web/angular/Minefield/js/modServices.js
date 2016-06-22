// ========================================================
// modServices
// ========================================================
var modServices = angular.module('modServices', []);

// Constant declaration
modServices.constant('googleUrl', 'http://google.es');

/*
modServices.config(['$httpProvider', function($httpProvider) {
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    }
]);
*/

// ========================================================
// Mocked service
// ========================================================
modServices.service('webMock', function ($http, googleUrl) {

	var mock_getURL = function () {
		return googleUrl;
	}

	var mock_getInt = function () {
		return 5;
	}

	return {
		mock_getURL: mock_getURL,
		mock_getInt: mock_getInt
	};

});

// ========================================================
// Online mocked service (doesn't work due to CORS)
// ========================================================
// http://www.mocky.io/v2/52653f714e3560b40151d9e5
modServices.constant('mockyBaseUrl', 'http://www.mocky.io/v2/');

// expected response: { "status" : "ready", count : 10 }
modServices.service('mocky', function ($http, mockyBaseUrl) {

	var getJson = function (code, callback) {
		$http.get(mockyBaseUrl + code)
			.success(function (data,status,headers,config) {
					callback(data);
				}
			)
			.error(function (data, status, headers, config) {
					alert(status + ' :: ' + data);
					callback(data);
				}
			);
	}

	return {
		getJson : getJson
	}
});