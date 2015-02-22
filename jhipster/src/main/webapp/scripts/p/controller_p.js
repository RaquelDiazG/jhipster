'use strict';

jhipsterApp.controller('PController', function ($scope, resolvedP, P, resolvedPs) {

        $scope.ps = resolvedP;
        $scope.pss = resolvedPs;

        $scope.create = function () {
            P.save($scope.p,
                function () {
                    $scope.ps = P.query();
                    $('#savePModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.p = P.get({id: id});
            $('#savePModal').modal('show');
        };

        $scope.delete = function (id) {
            P.delete({id: id},
                function () {
                    $scope.ps = P.query();
                });
        };

        $scope.clear = function () {
            $scope.p = {nombre: null, id: null};
        };
    });
