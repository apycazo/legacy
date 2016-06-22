// ========================================================
// APP.JS : Create all modules and config here
// ========================================================

// Example
/*
angular.module('app',['dep']);
angular.module('dep',[]);

Config
var prefix = 'hvk.' // a prefix to avoid name collisions

Define controllers, services, etc like this:
angular.module('dep').controller(prefix+'naive', function ($scope) {});
*/

// Available animations:
// bounce, shake
// fadeIn, fadeInUp, fadeInDown, fadeInLeft, fadeInRight
// fadeOut, fadeOutUp, fadeInDown, fadeInLeft, fadeInRight

// Default setup
var prefix = 'kaishi.';

var modCoreName = prefix + 'init';
var modControllersName = prefix + 'controllers';
var modServicesName = prefix + 'services';

angular.module(modCoreName,['ngRoute','ngAnimate', modControllersName]);
angular.module(modControllersName,[modServicesName]);
angular.module(modServicesName,[]);
