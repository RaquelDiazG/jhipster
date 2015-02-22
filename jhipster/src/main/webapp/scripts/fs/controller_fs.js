'use strict';

jhipsterApp.controller('FsController', function ($scope, resolvedFs, Fs, resolvedF) {

        $scope.fss = resolvedFs;
        $scope.fs = resolvedF;

        $scope.create = function () {
            Fs.save($scope.fs,
                function () {
                    $scope.fss = Fs.query();
                    $('#saveFsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.fs = Fs.get({id: id});
            $('#saveFsModal').modal('show');
        };

        $scope.delete = function (id) {
            Fs.delete({id: id},
                function () {
                    $scope.fss = Fs.query();
                });
        };

        $scope.clear = function () {
            $scope.fs = {nombre: null, id: null};
        };
    });
