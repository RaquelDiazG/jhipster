'use strict';

jhipsterApp.factory('F', function ($resource) {
        return $resource('app/rest/fs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
