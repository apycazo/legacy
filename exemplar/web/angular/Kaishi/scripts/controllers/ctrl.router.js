// Router controller

angular.module(modControllersName).controller('RouterCtrl', function ($routeProvider) {
	
	$routeProvider

	.when('/error', 
		{
			templateUrl: 'views/error.html',
			controller: undefined
		})
	.otherwise(
		{
			redirectTo: 'views/notFound.html'
		});
});