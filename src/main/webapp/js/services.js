var services = angular.module('ngApp.services', ['ngResource']);

services.factory('UserService', function($resource) {

    return $resource('rest/user/:action', {},
        {
            authenticate: {
                method: 'POST',
                params: {'action' : 'authenticate'},
                headers : {'Content-Type': 'application/x-www-form-urlencoded'}
            }
        }
    );
});

services.factory('UserOperationsService', function($resource) {

    return $resource('rest/user/control', {id: '@id'})
});

services.factory('StudentService', function($resource) {

    return $resource('rest/students/:id', {id: '@id'});
});