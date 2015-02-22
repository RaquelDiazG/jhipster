'use strict';

jhipsterApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/ps', {
                    templateUrl: 'views/pss.html',
                    controller: 'PsController',
                    resolve:{
                        resolvedPs: ['Ps', function (Ps) {
                            return Ps.query().$promise;
                        }],
                        resolvedP: ['P', function (P) {
                            return P.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
