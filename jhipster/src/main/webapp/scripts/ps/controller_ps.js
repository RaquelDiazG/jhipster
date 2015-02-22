'use strict';

jhipsterApp.controller('PsController', function ($scope, resolvedPs, Ps, resolvedP) {

        $scope.pss = resolvedPs;
        $scope.ps = resolvedP;

        $scope.create = function () {
            Ps.save($scope.ps,
                function () {
                    $scope.pss = Ps.query();
                    $('#savePsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.ps = Ps.get({id: id});
            $('#savePsModal').modal('show');
        };

        $scope.delete = function (id) {
            Ps.delete({id: id},
                function () {
                    $scope.pss = Ps.query();
                });
        };

        $scope.clear = function () {
            $scope.ps = {nombre: null, id: null};
        };
    });
