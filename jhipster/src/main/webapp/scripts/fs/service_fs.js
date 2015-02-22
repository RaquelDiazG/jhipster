'use strict';

jhipsterApp.factory('Fs', function ($resource) {
        return $resource('app/rest/fss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
