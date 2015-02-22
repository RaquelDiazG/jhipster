'use strict';

jhipsterApp.factory('Ps', function ($resource) {
        return $resource('app/rest/pss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
