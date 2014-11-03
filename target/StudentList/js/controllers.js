var controllers = angular.module('ngApp.controllers', ['ngApp.services'])

controllers.controller('IndexController', ['$scope', '$rootScope', 'StudentService', 'UserService', 'UserOperationsService', function ($scope, $rootScope, StudentService, UserService, UserOperationsService) {

    if($rootScope.hasRole('admin'))
    {
        $scope.userEntries = UserOperationsService.query();
    }
    $scope.studentEntries = StudentService.query();

    $scope.deleteEntry = function(studentEntry) {
        studentEntry.$remove(function() {
            $scope.studentEntries = StudentService.query();
        });
    };
}]);


controllers.controller('EditController', function ($scope, $routeParams, $location, StudentService) {

    $scope.studentEntry = StudentService.get({id: $routeParams.id});

    $scope.save = function() {
        $scope.studentEntry.$save(function() {
            $location.path('/');
        });
    };
});


controllers.controller('CreateController', function ($scope, $location, StudentService) {

    $scope.studentEntry = new StudentService();

    $scope.save = function() {
        $scope.studentEntry.$save(function() {
            $location.path('/');
        });
    };
});


controllers.controller('LoginController', ['$scope', '$rootScope', '$location', 'UserService', function ($scope, $rootScope, $location, UserService) {
    $rootScope.authenticateFailed = false;

    $scope.login = function() {
        UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function(authenticationResult) {
            var authToken = authenticationResult.token;
            $rootScope.authToken = authToken;
            UserService.get(function(user) {
                $rootScope.user = user;
                $location.path("/");
            });
        });
    };
}]);

controllers.controller('CreateUserController', ['$scope', '$location', 'UserOperationsService', function ($scope, $location, UserOperationsService) {

    $scope.userEntry = new UserOperationsService();
    $scope.isAdmin = false;
    $scope.userEntry.roles = ['user'];

    $scope.setAdminProperty = function()
    {
        if($scope.isAdmin)
        {
            $scope.isAdmin = false;
            $scope.userEntry.roles.pop();
        }
        else
        {
            $scope.isAdmin = true;
            $scope.userEntry.roles.push('admin');
        }
    }

    $scope.saveUser = function(){
        $scope.userEntry.$save(function() {
            $location.path('/');
        });
    }
}]);