

// Core module
var core = angular.module('arc',[]);

// Service
function CommonSrvc () {

    // Creates the service instance
    return {
        showAlert : function (text) {

            text = angular.isDefined(text) ? text : 'An alert has been thrown';
            alert(text);
        }
    }
}

core.factory('CommonSrvc', CommonSrvc);

// Controller
function CoreCtrl ($scope, commonSrvc) {

    $scope.text = 'Angular library loaded and working';

    $scope.alert = function (text) {

        commonSrvc.showAlert(text);
    }
}

core.controller('CoreCtrl', ['$scope', 'CommonSrvc', CoreCtrl]);