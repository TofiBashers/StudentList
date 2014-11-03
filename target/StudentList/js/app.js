angular.module('ngApp', ['ngRoute', 'ngApp.services', 'ngApp.controllers'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/create', {
				templateUrl: 'partials/create.html',
				controller: 'CreateController',
                resolve:{
                    factory:checkRouting
                }
			});
			
			$routeProvider.when('/edit/:id', {
				templateUrl: 'partials/edit.html',
				controller: 'EditController',
                resolve:{
                    factory:checkRouting
                }
			});

			$routeProvider.when('/login', {
				templateUrl: 'partials/login.html',
				controller: 'LoginController'
			});

            $routeProvider.when('/createUser', {
                templateUrl: 'partials/createUser.html',
                controller: 'CreateUserController',
                resolve:{
                    factory:checkRouting
                }
            });
			
			$routeProvider.otherwise({
				templateUrl: 'partials/index.html',
				controller: 'IndexController',
                resolve:{
                    factory:checkRouting
                }
			});
			
			$locationProvider.hashPrefix('!');

 		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			      
			        		if (status == 401) {
			        			$location.path( "/login" );
                                $rootScope.authenticateFailed = true;
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    

		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			config.headers['X-Auth-Token'] = authToken;
		        		}
		        		return config;
		        	}
		        };
		    }
	    );
		   
		} ]
		
	).run(function($rootScope, $location, UserService) {

		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$location.path("/login");
		};

		$location.path("/login");
		
		$rootScope.initialized = true;
	});

var checkRouting = function ($q, $rootScope, $location) {
    if($rootScope.authToken){
        return true;
    }
    else{
        $location.path("/login")
        return false;
    }
}



