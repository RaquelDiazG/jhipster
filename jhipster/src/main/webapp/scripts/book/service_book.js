'use strict';

jhipsterApp.factory('Book', function ($resource) {
        return $resource('app/rest/books/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
