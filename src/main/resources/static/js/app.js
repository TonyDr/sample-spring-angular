var app = angular.module('app', ['ngRoute', 'ngResource']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/staff', {
            templateUrl: '/view/staff.html',
            controller: 'staffController'
        })
        .when('/audit', {
            templateUrl: '/view/audit.html',
            controller: 'auditController'
        })
        .otherwise(
            {redirectTo: '/'}
        );
});