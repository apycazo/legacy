
// TODO : Change things to use ngResources

var modServices = angular.module('modServices', []);

// ===========================================================================
// Server API
// ===========================================================================
modServices.service('server', function ($http) {

	var sendPost = function (title, content, privacy, callback) {
		var data = {
			type : privacy,
			title : title,
			content : content.replace(/\n/g, "<br/>")
		};

		$http({
            url: '/upload',
            method: "POST",
            data: data,
            headers: {'Content-Type': 'application/json'}
        })
			.success(function (data,status,headers,config) {
				callback(data);
			})
			.error(function (data,status,headers,config) {
				callback(null);
			});	
	};

	var getPublicPage = function (page, callback) {
		$http.get('/data/public/'+page)
			.success(function (data,status,headers,config) {
				callback(data);
			})
			.error(function (data,status,headers,config) {
				callback(null);
			});
	};

	var getPublicAndRegisteredPage = function (page, callback) {
		$http.get('/data/private/'+page)
			.success(function (data,status,headers,config) {
				callback(data);
			})
			.error(function (data,status,headers,config) {
				callback(null);
			});
	};

	var removePostByID = function (id, callback) {
		$http.delete('/remove/'+id)
			.success(function (data,status,headers,config) {
				callback(data);
			})
			.error(function (data,status,headers,config) {
				callback(null);
			});
	};

	var getLoggedUser = function (callback) {
		$http.get('/loggedin')
			.success(function (data, status, headers, config) {
				if (typeof callback != 'undefined') callback(data);
			})
			.error(function (data, status, headers, config) {
				if (typeof callback != 'undefined') callback(null);
			});
	};

	var getPostById = function (id, callback) {
		$http.get('/post/'+id)
			.success(function (data, status, headers, config) {
				if (typeof callback != 'undefined') callback(data);
			})
			.error(function (data, status, headers, config) {
				if (typeof callback != 'undefined') callback(null);
			});
	};

	var updatePostById = function (id, title, content, privacy, callback) {
		var data = {
			id : id,
			type : privacy,
			title : title,
			content : content.replace(/\n/g, "<br/>")
		};

		$http({
            url: '/update/post',
            method: "POST",
            data: data,
            headers: {'Content-Type': 'application/json'}
        	})
			.success(function (data,status,headers,config) {
				callback(data);
			})
			.error(function (data,status,headers,config) {
				callback(null);
			});	
	};

	return {
		sendPost: sendPost,
		getPublicPage : getPublicPage,
		getPublicAndRegisteredPage: getPublicAndRegisteredPage,
		removePostByID: removePostByID,
		getLoggedUser : getLoggedUser,
		getPostById : getPostById,
		updatePostById : updatePostById
	};

});

// ===========================================================================
// Common functions for controllers
// ===========================================================================

modServices.service('common', function ($http, $sce) {
	var loadUserData = function (response, scope) {
		// sanitize html (required for multi-line content)
		response.data.forEach(function (msg) {
			msg.content = $sce.trustAsHtml(msg.content);
		});
		scope.items = response.data;
		scope.pageInfo = response.pageInfo;
		scope.hasPreviousPages = scope.pageInfo.offset > 0;
		var pageCount = Math.floor(scope.pageInfo.count / scope.pageInfo.limit) + 1
		var curPage = Math.floor(scope.pageInfo.offset / scope.pageInfo.limit) + 1;
		scope.hasMorePages = pageCount > curPage;
	};

	return {
		loadUserData : loadUserData
	}

});