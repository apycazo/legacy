
function arcKeypress () {
    
    return function(scope, element, attrs) {

        element.bind("keydown keypress", function(event) {

            var keyCode = event.which || event.keyCode;

            // If enter key is pressed
            if (keyCode === 13) {

                event.preventDefault();

                scope.$apply(function() {

                    // Evaluate the expression
                    scope.$eval(attrs.arcKeypress);
                });

            }
        });
    };
};

angular.module('arc').directive('arcKeypress', arcKeypress);