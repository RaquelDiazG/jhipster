'use strict';

jhipsterApp.controller('FController', function ($scope, resolvedF, F, resolvedFs) {

        $scope.fs = resolvedF;
        $scope.fss = resolvedFs;

        $scope.create = function () {
            F.save($scope.f,
                function () {
                    $scope.fs = F.query();
                    $('#saveFModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.f = F.get({id: id});
            $('#saveFModal').modal('show');
        };

        $scope.delete = function (id) {
            F.delete({id: id},
                function () {
                    $scope.fs = F.query();
                });
        };

        $scope.clear = function () {
            $scope.f = {nombre: null, id: null};
        };
    });
