// Main controller for Minefield

var modCore = angular.module('Minefield', ['ngRoute', 'ngAnimate', 'modControllers']);

// Configuracion de redirecciones	
modCore.config(function ($routeProvider) {

	$routeProvider
		.when('/', {
			redirectTo: '/simple'
		})
		.when('/simple', {
			// en la sección ng-view carga este código
			templateUrl: 'html/simpleView.html',
			controller: 'simpleController'
		})
		.when('/repeat', {
			templateUrl: 'html/repeatView.html',
		})
		.when('/form', {
			templateUrl: 'html/formView.html',
			controller: 'formController'
		})
		.when('/css', {
			templateUrl: 'html/cssView.html',
			controller: 'cssController'
		})
		.when('/services', {
			templateUrl: 'html/servicesView.html',
			controller: 'servicesController'
		})
		.when('/validation', {
			templateUrl: 'html/validationView.html',
			controller: 'validationController'
		})
		.when('/route/:id', {
			templateUrl: 'html/modCoreView.html',
			controller: 'modCore'
		})
		.when('/directive', {
			templateUrl: 'html/directiveView.html'
		})
		.when('/basicAnimation', {
			templateUrl: 'html/basicAnimation.html',
			controller: 'animationController'
		})
		.when('/small', {
			templateUrl: 'smallTemplate.html',
		})
		.when('/templateTextLite', {
			templateUrl: 'templateTextLite.html',
		})
		.when('/err', {
			templateUrl: 'html/error.html',
		})
		.otherwise({
			redirectTo: '/err'
		});

	// add dynamic templates
	for (index in templateLibrary) {
		$routeProvider.when(
			'/' + templateLibrary[index].name,
			{ 
				templateUrl : templateLibrary[index].name + '.html',
				controller : templateLibrary[index].controller
			}
		);
	}

});

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Custom directive
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//  SRC: http://www.befundoo.com/university/tutorials/angularjs-directives-tutorial/
/* 	Directive restrictions
	‘A’ – Attribute (You want to use your directive as <div rating>)
	‘E’ – Element (Use it as <rating>)
	‘C’ – Class. (Use it like <div class=”rating”>)
	‘M’ – Comment (Use it like <!– directive: rating –>
*/
modCore.directive('customRating', function () {

	var listTemplate = '<ul><li ng-repeat="star in stars" class="filled">\u2605</li></ul>';
	var continuousTemplate = '<ul><tt ng-repeat="star in stars" class="bigText">\u2605</tt></ul>';

    return {
    	restrict: 'A',
      	template: continuousTemplate,
      	scope: {
        	ratingValue: '='
      	},
      	link: function (scope, elem, attrs) {
        	scope.stars = [];
        	for (var i = 0; i < scope.ratingValue; i++) {
          		scope.stars.push({});
        	}
      	}
  	}
});

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Advanced directive
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
modCore.directive('collapsible', function () {
	var template = '<div>'
		+ '<div class="collapsible head" ng-click="toggle()">{{title}}</div>'
		+ '<div class="collapsible body" ng-show="visible" ng-transclude></div>'
		+ '</div>';

	/*
	Transclude: Marks the insertion point for the directive dom node content inside the template
	ex: <div custom-directive>
			<p>Content transcluded</p>
		</div>

	template:
		<!-- I want the directive dom content appended on this div -->
		<div class="bold text" ng-transclude>
			<p>This is the directive content</p>
		</div>

	Without transclusion:
	result:
		<div class="bold text">
			<p>This is the directive content</p>
		</div>

	With transclusion
		<div class="bold text">
			<p>This is the directive content</p>
			<p>Content transcluded</p>
		</div>
	*/

	return {
		restrict : 'AE',
		template: template,
		transclude: true, // mix existing with template
		scope: {
			// Associates title with the value given to 'custom-title'
			title : '=customTitle'
		},
		link: function (scope, elem, attrs) {
			scope.visible = true;
			scope.toggle = function () {
				scope.visible = !scope.visible;
			}
		}
	}
});

// collapsible controller example
modCore.controller('collapsibleController', function ($scope){
	$scope.title = "== Title (click to toggle) ==";
	$scope.text = 'Body text from scope';
});

// Small templates
modCore.run(function ($templateCache) {
	$templateCache.put('smallTemplate.html', '<div>This is a small template, loaded from JS</div>');
	$templateCache.put('templateTextLite.html', templateTextLite);
	// Adds dynamic templates
	for (index in templateLibrary) {
		$templateCache.put(templateLibrary[index].name + '.html', templateLibrary[index].html);
	}
});

// Sample component (without transclusion)
modCore.directive('directive', function () {

	var template = '<form ng-submit="doEcho(echo)">'
			+ '<input ng-model="echo" type="text" class="form-control" placeholder="Test me!"></input>'
			+ '<span>Echo response :: {{response}}</span>'
			+ '</form>';

	return {
		restrict: 'AE',
		template: template,
		require: '^parent',
		controller: function ($scope) {
			$scope.doEcho = function (text) {
				$scope.response = text;
			};
		}
		// Link is used to manipulate the dom node
		/*,
		link: function (scope, elem, attrs, parentCtrl) {
			scope.doEcho = function (text) {
				console.log("doEcho :: " + text);
				debugger
				parentCtrl.setResponse(text);
			}
		}*/
	}
});


modCore.controller('templateLinker', function ($scope) {
	$scope.templates = templateLibrary;
});

// Template as directive
modCore.directive('cslnk', function() {
	return {
		restrict: 'E',
		templateUrl: 'templateLink.html'
		//, replace: true
	};
});

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Template loader
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
/*
modCore.factory('$templateCache', function($cacheFactory, $http, $injector) {
	var cache = $cacheFactory('templates');
  	var allTplPromise;
  	var templatePath = 'html/templates.html';
 
  	return {
    	get: function(url) {
      		var fromCache = cache.get(url);
 
      		// already have required template in the cache
      		if (fromCache) {
        		return fromCache;
      		}
 
	    	// first template request ever - get the all tpl file
	      	if (!allTplPromise) {
	        	allTplPromise = $http.get(templatePath).then(function(response) {
	          		// compile the response, which will put stuff into the cache
	          		$injector.get('$compile')(response.data);
	          		return response;
	        	});
	      	}
 
      		// return the all-tpl promise to all template requests
      		return allTplPromise.then(function(response) {
        		return {
          			status: response.status,
          			data: cache.get(url)
        		};
      		});
    	},
 
    	put: function(key, value) {
      		cache.put(key, value);
    	}
  	};
});
*/