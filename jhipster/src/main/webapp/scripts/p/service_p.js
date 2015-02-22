'use strict';

jhipsterApp.factory('P', function ($resource) {
        return $resource('app/rest/ps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
