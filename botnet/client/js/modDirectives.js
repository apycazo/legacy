
var directives = angular.module('modDirectives', ['modServices'])

// ===========================================================================
// NavBar directive
// ===========================================================================
directives.controller('ctrl-navbar', function ($scope, $rootScope, $http, $location, $route) {
    $scope.logged =  $rootScope.logged;    

    $scope.register = function () {
        $location.url('/register');
    };

    $scope.login = function (user, passwd) {
        $http.post('/login', {
            username: user,
            password: passwd,
        })
        .success(function(user) {
            $location.url('/restricted');
        })
        .error(function() {
            alert("Login error user/passwd incorrect.");
            $scope.user.id = "";
            $scope.user.pwd = "";
        });
    };

    $scope.logout = function () {        
        $http.post('/logout');
        $rootScope.userlogged = "";
        $rootScope.isAdmin = false;
        $scope.user = {
            id : "",
            pwd : ""
        };
        $location.url('/');
    };

    $scope.refresh = function () {
        $route.reload();
    }
});

directives.directive('navbar', function() {
    return {
        restrict: 'EA',
        templateUrl: 'views/navBar.html',
        controller : 'ctrl-navbar'
    };
});

// ===========================================================================
// Directive for new post
// ===========================================================================

directives.controller('ctrl-edit-post', function ($scope, $sce, $document, $routeParams, $location, server) {
    $scope.post = {
        title : '',
        text : '',
        type : 'registered'
    };
    
    var resetSelection = function () {
        $scope.sel = {
            start : 0,
            end : 0,
            text : ''
        };
    };

    resetSelection();
    $scope.postButtonText = 'Post';

    if ($routeParams.id) {
        $scope.postButtonText = 'Update';
        server.getPostById($routeParams.id, function (result) {
            if (typeof result == 'object') {
                $scope.post.title = result.title;
                $scope.post.text = result.content;
                $scope.post.type = result.type;
            }
        });
    }

    var wrapWithTags = function (initTag, endTag) {
        var elem = $scope.post.text;
        if ($scope.sel.start != $scope.sel.end) {
            var head = elem.substring(0,$scope.sel.start);
            var tail = elem.substring($scope.sel.end,elem.length);
            $scope.post.text = head + initTag + $scope.sel.text + endTag + tail;         
        }
        else $scope.post.text += initTag + endTag;
        resetSelection();
    };

    $scope.bold = function () {
        wrapWithTags('<b>','</b>');
    };

    $scope.italics = function () {
        wrapWithTags('<i>','</i>');
    };

    $scope.image = function () {
        wrapWithTags('<br><img src="">','</img>');
    };

    $scope.h1 = function () {
        wrapWithTags('<h1>','</h1>');
    };

    $scope.h2 = function () {
        wrapWithTags('<h2>','</h2>');
    };

    $scope.selectPrivacy = function (type) {
        $scope.post.type = type;
    };

    $scope.postMessage = function () {
        if (typeof $scope.post != "undefined" && $scope.post.title != "" && $scope.post.text != "") {
            if ($routeParams.id) {
                server.updatePostById($routeParams.id, $scope.post.title, $scope.post.text, $scope.post.type, function (response) {
                    console.log(response);
                    $location.url('/restricted');
                });
            }
            else {
                server.sendPost($scope.post.title, $scope.post.text, $scope.post.type, function (response) {
                    $scope.clearPost();
                    $scope.refresh();
                });
            }
        }
    };

    $scope.clearPost = function () {
        if (typeof $scope.post != "undefined") {
            $scope.post.text = "";
            $scope.post.title = "";
        }
    };
});

directives.directive('editpost', function() {
    return {
        restrict: 'EA',
        templateUrl: 'views/edit-post.html',
        controller : 'ctrl-edit-post',
        link: function (scope, element, attrs) {
            scope.txt = element.find('#code-area');
            scope.view = element.find('#view-area');
            scope.txt.blur(function () { 
                scope.sel = {
                    start : this.selectionStart,
                    end : this.selectionEnd,
                    text : this.value.substring(this.selectionStart,this.selectionEnd)
                }
            });            
        }
    };
});

