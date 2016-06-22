
var modControllers = angular.module('modControllers', ['modServices']);

// ========================================================
// Simple controller
// ========================================================
modControllers.controller('simpleController', function ($scope) {
	$scope.greeting = { text: 'Hello' };

	var callback = function () {
		console.log($scope.greeting.text);
		if ($scope.greeting.text.length > 10) {
			$scope.greeting.text = '';
			window.alert('Text is too big!');
		}
	};

	$scope.$watch('greeting.text', callback);
});

// ========================================================
// Controller for repeating items
// ========================================================
modControllers.controller ('repeatController', function ($scope) {
	$scope.items = [
		{ name : 'orange', quantity : 0, price : 1.2 },
		{ name : 'apple', quantity : 1, price : 1.8 },
		{ name : 'lemon', quantity : 2, price : 1.5 }
	];

	$scope.remove = function (index) {
		console.log('Removing index: ' + index + '. Item count is: ' + $scope.items.length);
		var removedItem = $scope.items.splice(index,1);
	};

	$scope.add = function (name, price) {
		console.log ('Adding element: ' + name + '. Price: ' + price);
		$scope.items.push({
			name : name,
			quantity: 0,
			price: price
		});
	};
});

// ========================================================
// Controller for form testing
// ========================================================
modControllers.controller('formController', function ($scope) {
	$scope.name = 'Form controller tester';
	$scope.cost = 0;
	$scope.formVisible = true;
	$scope.receivedText =  'No text received (empty)';

	$scope.computeRealCost = function () {
		console.log('Calculating real cost');
		$scope.needed = $scope.cost * 3;
	}

	$scope.submit = function () {
		console.log('Form submited');
		window.alert('Needed: ' + $scope.needed);
	}

	$scope.reset = function () {
		$scope.cost = 0;
	}

	$scope.toggleView = function () {
		$scope.formVisible = !$scope.formVisible;
	}

	$scope.submitAlternate = function (text) {
		console.log('Received text: ' + text);
		if (text && text != '') {
			$scope.receivedText = 'Received: ' + text;
		}
		else {
			$scope.receivedText = 'No text received (empty)';
		}
	}
});

// ========================================================
// Controller for dynamic css testing
// ========================================================
modControllers.controller('cssController', function ($scope) {
	$scope.selected = false;
});

// ========================================================
// Controller for services testing (services=singleton)
// ========================================================
modControllers.controller('servicesController', function ($scope, webMock, mocky) {
	$scope.url = 'unknown';
	$scope.lag = -1;
	$scope.code = '52653f714e3560b40151d9e5';
	$scope.jsonResponse = 'nothing received';

	$scope.getData = function () {
		$scope.url = webMock.mock_getURL();
		$scope.lag = webMock.mock_getInt();
	};

	$scope.clear = function () {
		$scope.url = 'unknown';
		$scope.lag = -1;
	};

	$scope.getJson = function (code) {
		var callback = function (data) {
			$scope.jsonResponse = data;
		};
		mocky.getJson(code, callback);
	}	
});

// ========================================================
// Routing controller
// ========================================================
modControllers.constant('mails', [
	{ "from" : "name@gmail.com", "to" : "company@company.com", "body" : "This is a first test mail" },
	{ "from" : "company@company.com", "to" : "name@gmail.com", "body" : "This is a second test mail" }
]);

modControllers.controller('modCore', function ($scope, $routeParams, mails) {
	console.log('modCore access: ' + $routeParams.id);
	$scope.mail = mails[$routeParams.id];
});

// ========================================================
// Validation controller
// ========================================================
modControllers.controller('validationController', function ($scope) {
	$scope.clear = function () {
		$scope.user.first = '';
		$scope.user.last = '';
		$scope.user.email = '';
		$scope.user.age = undefined;
	};

	$scope.addUser = function () {
		$scope.message = 'Added  user ' + $scope.user.first;
		$scope.clear();
	}
});

// ========================================================
// Animation controller
// ========================================================
modControllers.controller('animationController', function ($scope) {
	$scope.checked = false;
	$scope.items = [];

	$scope.check = function () {
		$scope.checked = !$scope.checked;
	};

	$scope.add = function (item) {
		console.log('Adding ' + item);
		$scope.items.push(item);
		$scope.newTodo = '';		
	}

	$scope.remove = function () {
		if ($scope.items.length > 0) {
			$scope.items.splice(0,1);
		}
	}

});


modControllers.controller('jsonLoadController', function ($scope, $http, $sce) {
	$scope.path = 'res/sample.json';
	//$scope.firstDataHtml = '<p>Unset</p>';

	$scope.load = function (path) {
		$http.get(path)
			.success(function (data) {
				$scope.data = data;
				$scope.firstDataHtml = $sce.trustAsHtml(data[0].content.trunk);
			});
	}

	$scope.loadIndex = function (path,index) {
		$http.get(path)
			.success(function (data) {
				$scope.data = data;
				$scope.firstDataHtml = $sce.trustAsHtml(data[index].content.trunk);
			});
	}

});

