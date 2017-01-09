
var modControllers = angular.module('modControllers', ['modServices']);

var defaultImageURL = 'http://localhost:3000/resources/unknown_user_100x97.jpg';

modControllers.controller('registerController', function ($scope, $http, $location) {

	$scope.clearForm = function () {
		$scope.user = {
			nick : '',
			first : '',
			last : '',
			pwd1 : '',
			pwd2 : '',
			img : '',
			email : ''
		};
	};

	$scope.register = function () {
		if ($scope.user.pwd1 != $scope.user.pwd2) {
			alert("Passwords not matching!");
		}
		else {
			var data = {
				id : $scope.user.nick,
				name : $scope.user.first + ' ' + $scope.user.last,
				password : $scope.user.pwd1,
				email : $scope.user.email,
				img : $scope.user.img || defaultImageURL
			};
			$http.post('/register', data)
				.success(function (res) {
					alert("User registered");
					$scope.clearForm();
					$location.url('/');
				})
				.error(function (err) {
					alert("Error registering user!");
					console.log(err);
				});
		}
	}
});

modControllers.controller('publicIndexController', 
	function ($scope, $rootScope, $location, $http, $sce, $anchorScroll, server, common) {

		$rootScope.logged = false;

		var callback = function (response) {
			common.loadUserData(response,$scope);
		}

		$scope.refresh = function (displacement) {
			var curPage = typeof $scope.pageInfo != 'undefined' ? ($scope.pageInfo.offset/$scope.pageInfo.limit)+1 : 1;
			var offset = typeof displacement != 'undefined' ? displacement : 0;
			server.getPublicPage(curPage + offset, callback);
		};

		$scope.refresh();
	}
);

modControllers.controller('privateIndexController', 
	function ($scope, $rootScope, $routeParams, $location, $http, $sce, $anchorScroll, server, common) {

		var checkLoggedUser = function (response) {
			if (typeof response == 'object' && response == '0') $location('/');
			else {
				$rootScope.isAdmin = response.name == 'admin'
				$rootScope.userlogged = response.name;
				$rootScope.logged = true;
			}
		};

		$scope.refresh = function (displacement) {
			var curPage = typeof $scope.pageInfo != 'undefined' ? ($scope.pageInfo.offset/$scope.pageInfo.limit)+1 : 1;
			var offset = typeof displacement != 'undefined' ? displacement : 0;
			var callback = function (response) {
				if (response == null) {
					$location.url('/');
					alert("Authentication error, please log in");
				}
				else common.loadUserData(response,$scope);
			};
			server.getPublicAndRegisteredPage(curPage + offset, callback);
		};

		$scope.requestToDelete = function (id) {
			$scope.markedToDelete = id;
		};

		$scope.cancelDelete = function () {
			$scope.markedToDelete = '';
		};

		$scope.removePost = function (id) {	
			var callback = function (response) {
				if (response == null) alert("Can't remove post!");
				$scope.markedToDelete = '';
				$scope.refresh();
			}
			server.removePostByID(id, callback);
		};

		$scope.requestToEdit = function (id) {
			$location.url('/edit/'+id);
		};

		server.getLoggedUser(checkLoggedUser);
		$scope.markedToDelete = '';

		$scope.post = {
			type : 'registered'
		};

		$scope.refresh();
	}
);



