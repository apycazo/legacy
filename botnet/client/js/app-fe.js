
var app_fe = angular.module('BotNet',['ngRoute','modControllers', 'modDirectives']);

app_fe.config(function($routeProvider, $locationProvider, $httpProvider) {

	//================================================
    // Check if the user is connected
    //================================================
    var checkLoggedin = function($q, $timeout, $http, $location, $rootScope){
     	// Initialize a new promise
	    var deferred = $q.defer();

	    // Make an AJAX call to check if the user is logged in
	    $http.get('/loggedin').success(function(user) {
	    	console.log("Checking logged used");
	        // Authenticated
	        if (user !== '0') {
	        	$timeout(deferred.resolve, 0);
	        }
	        // Not Authenticated
	        else {
	        	$rootScope.message = 'You need to log in.';
	        	alert("Authentication error.");
	        	$timeout(function(){deferred.reject();}, 0);
	        	$location.url('/login');
	        }
	    });
	    return deferred.promise;
    };

    //================================================
    // Add an interceptor for AJAX errors
    //================================================
    $httpProvider.responseInterceptors.push(function($q, $location) {
    	return function(promise) {
        	return promise.then(
          		// Success: just return the response
          		function(response) {
            		return response;
          		}, 
          		// Error: check the error status to get only the 401
          		function(response) {
            		if (response.status === 401)
              			$location.url('/login');
            		return $q.reject(response);
          		}
        	);
      	}
    });

	//================================================
	// Route configuration
	//================================================
	$routeProvider
		.when('/', {
			templateUrl: 'views/index.html',
			controller: 'publicIndexController'
		})
		.when('/login', {
			redirectTo: '/'
		})
		.when('/users/:username', {
			templateUrl: 'views/index.html',
			controller: 'privateIndexController'
		})
		.when('/restricted', {
			templateUrl: 'views/index.html',
			controller: 'privateIndexController'
		})
		.when('/register', {
			templateUrl: 'views/register.html',
			controller: 'registerController'
		})
		.when('/terms', {
			templateUrl: 'views/terms.html'
		})
		.when('/err', {
			templateUrl: 'views/error.html',
		})
		.when('/edit/:id', {
			template: '<div class="col-md-offset-1 col-md-10 bn-boxed"><div editpost style="margin-top:10px;"></div></div>'
		})
		.otherwise({
			redirectTo: '/err'
		});
	// ====================================================
});

// Start run
app_fe.run(function($rootScope, $http) {

    // Logout function is available in any pages
    $rootScope.logout = function() {
    	$http.post('/logout');
    };
});