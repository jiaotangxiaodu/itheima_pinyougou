//首页控制器
app.controller('indexController', function ($controller, $scope, loginService) {
    $controller('baseController', {$scope: $scope});//继承
    $scope.showName = function () {
        loginService.showName().success(
            function (response) {
                $scope.loginName = response.loginName;
            }
        );
    }
});
