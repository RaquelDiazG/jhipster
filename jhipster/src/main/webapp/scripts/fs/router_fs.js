'use strict';

jhipsterApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/fs', {
                    templateUrl: 'views/fss.html',
                    controller: 'FsController',
                    resolve:{
                        resolvedFs: ['Fs', function (Fs) {
                            return Fs.query().$promise;
                        }],
                        resolvedF: ['F', function (F) {
                            return F.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
