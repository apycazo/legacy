
// Core module
var core = angular.module('arc');

// Controller
function CoreCtrl ($scope, jsonEditorSrvc) {

    $scope.test = function () {

        var demoData = {
            name : 'Andres',
            last : 'Picazo'
        }

        var modalInstance = jsonEditorSrvc.showModalEditor(demoData);
        modalInstance.result.then(function (data) {
            
            alert(JSON.stringify(data));
        });
    }
};

core.controller('CoreCtrl', ['$scope', 'JsonEditorSrvc', CoreCtrl]);