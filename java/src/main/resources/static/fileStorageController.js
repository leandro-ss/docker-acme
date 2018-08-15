App.controller('fileStorageController',
['$scope','$rootScope','$http','urls','fileStorageService', function($scope, $rootScope, $http, urls, fileStorageService) {

    $scope.file = '';
    $scope.token = '';
    $scope.items = [];

    $scope.editedItem = '';

    $scope.settings = {
        currentPage: 0,
        offset: 0,
        pageLimit: 10,
        total: 0
    };

    $scope.rename = function (oldFileName) {
        fileStorageService.renameFile(fileName)
            .then(
                function (response) {
                    alert("file uploaded successfully.");
                    $http.get("http://localhost:8080/file").success(
                        function (response) {
                            for (var i in response.listFileName) {
                                $scope.items.push({ name: response.listFileName[i] });
                            }
                        });
                }
            );
    }

    $scope.delete = function (fileName) {
        fileStorageService.deleteFile(fileName)
            .then(
                function (response) {
                    alert("file uploaded successfully.");
                    $http.get("http://localhost:8080/file").success(
                        function (response) {
                            for (var i in response.listFileName) {
                                $scope.items.push({ name: response.listFileName[i] });
                            }
                        });
                }
            );
    }

    $scope.upload = function () {
        var file = $scope.file;
        fileStorageService.uploadFile(file)
            .then(
                function (response) {
                    alert("file uploaded successfully.");
                    $http.get("http://localhost:8080/file").success(
                        function (response) {
                            for (var i in response.listFileName) {
                                $scope.items.push({ name: response.listFileName[i] });
                            }
                        });
                }
            );
    }

    $http.get("http://localhost:8080/file").success(
        function (response) {
            for (var i in response.listFileName) {
                $scope.items.push({ name: response.listFileName[i] });
            }

            $scope.settings.total = $scope.items.length;

            if (response.nextContinuationToken != null){
                $scope.settings.total += 10;
                $scope.token = response.nextContinuationToken;
            }
            
        });

    $scope.callback = function () {
        $http.get("http://localhost:8080/file?token=" + $scope.token).success(
            function (response) {
                for (var i in response.listFileName) {
                    $scope.items.push({ name: response.listFileName[i] });
                }
                $scope.token = response.nextContinuationToken;
            });
    }
}]);

App.directive('fileModel', [ '$parse', 
    function($parse) {
        return {
            restrict : 'A',
            link : function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function() {
                    scope.$apply(function() {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
}]);

App.directive('ngRenameClick', [ 
    function() {
        return {
            restrict : 'A',
            link: function ($scope, element, attr) {
                var msg = attr.ngRenameClick || "Are you sure?";
                var clickAction = attr.renameClick;
                element.bind('click',function (event) {
                    var newName = prompt("Please enter the new Name:", "");
                    if (newName != null && newName != "") {
                        $scope.$parent.editedItem = newName;
                        $scope.$eval(clickAction);
                    }
                });
            }
        };
}]);

App.directive('ngConfirmClick', [
    function(){
        return {
            link: function (scope, element, attr) {
                var msg = attr.ngConfirmClick || "Are you sure?";
                var clickAction = attr.confirmedClick;
                element.bind('click',function (event) {
                    if ( window.confirm(msg) ) {
                        scope.$eval(clickAction)
                    }
                });
            }
        };
}]);
