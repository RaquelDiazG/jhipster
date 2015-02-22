'use strict';

jhipsterApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/f', {
                    templateUrl: 'views/fs.html',
                    controller: 'FController',
                    resolve:{
                        resolvedF: ['F', function (F) {
                            return F.query().$promise;
                        }],
                        resolvedFs: ['Fs', function (Fs) {
                            return Fs.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
