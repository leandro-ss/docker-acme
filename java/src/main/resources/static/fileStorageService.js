'use strict';
var App = angular.module('fileStorageDemo',['angularSimplePagination']);

App.constant('urls', {
    URL_UPLOAD: 'http://localhost:8080/file/upload',
    URL_DOWNLOAD: 'http://localhost:8080/file/download',
    URL_RENAME: 'http://localhost:8080/file/rename',
    URL_DELETE: 'http://localhost:8080/file/delete',
    URL_LIST: 'http://localhost:8080/file'
});

App.factory('fileStorageService', ['$http', '$q', 'urls', function ($http, $q, urls) {

        var factory = {
            uploadFile: uploadFile,
            downloadFile: downloadFile,
            deleteFile: deleteFile,
            renameFile: renameFile
        };

        return factory;

        function renameFile(oldFileName,newFileName) {
            var deferred = $q.defer();
            
            $http.get(urls.URL_RENAME+"/"+oldFileName+"?newFName="+newFileName)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        alert(errResponse.data.errorMessage);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        };

        function deleteFile(fileName) {
            var deferred = $q.defer();
            
            $http.get(urls.URL_DELETE+"/"+fileName)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        alert(errResponse.data.errorMessage);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        };

        function uploadFile(file) {
            var deferred = $q.defer();
            var formData = new FormData();
            
            formData.append('file', file);

            $http.post(urls.URL_UPLOAD, formData,{
                transformRequest : angular.identity,
                headers : {
                    'Content-Type' : undefined
                }})
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        alert(errResponse.data.errorMessage);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        };

        function downloadFile(fileName) {
            var deferred = $q.defer();
            $http.get(urls.URL_DOWNLOAD+'/'+fileName)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        alert(errResponse.data.errorMessage);
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }
    }
]);