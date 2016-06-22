

// Core module
var core = angular.module('arc');


function processConfig (data, config) {

	var baseConfig = {
		title : 'Configure',
		translate : function (text) {
			return text.charAt(0).toUpperCase() + text.slice(1);
		},
		buttons : {
			ok : {
				text : 'ok',
				onClick : function (data, modalInstance) {

					modalInstance.close(data);
				}
			},
			cancel : {
				text : 'cancel',
				onClick : function (data, modalInstance) {

					modalInstance.dismiss();
				}
			}
		},
		styles : {
			template : {
				padding : '15px'
			}
		},
		css : {
			template : 'arc-json-edit-template'
		}
	};

	if (!angular.isObject(config)) {

		config = baseConfig;
		config.dataModel = {
			fields : []
		};

		angular.forEach(data, function (value, index) {
			config.dataModel.fields.push(index);
		});

	}

	// Buttons as list
	config.buttonList = [];
	angular.forEach(config.buttons, function (value, index) {

		config.buttonList.push(value);
	});
	
	return config;

};

// Service
function JsonEditorSrvc ($modal) {

    return {

        showModalEditor : function (data, config) {

        	// Check arguments
        	if (!angular.isObject(data)) {
        		data = {};
        	}
        	config = processConfig(data, config);
        	
        	var modalController = function (scope, modalInstance, data, config) {

        		scope.data = data;
        		scope.config = config;
        		scope.modalInstance = modalInstance;

        	};

        	var modalConfig = {
        		templateUrl : 'arc-json-edit-tplt.html',
        		controller : ['$scope', '$modalInstance', 'data', 'config', modalController],
        		resolve : {
        			data : function () {
        				return data;
        			},
        			config : function () {
        				return config;
        			}
        		}
        	};

        	return $modal.open(modalConfig);
        	
        }

    }
}

core.factory('JsonEditorSrvc', ['$modal', JsonEditorSrvc]);