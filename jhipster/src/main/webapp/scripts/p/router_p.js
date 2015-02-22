'use strict';

jhipsterApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/p', {
                    templateUrl: 'views/ps.html',
                    controller: 'PController',
                    resolve:{
                        resolvedP: ['P', function (P) {
                            return P.query().$promise;
                        }],
                        resolvedPs: ['Ps', function (Ps) {
                            return Ps.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
