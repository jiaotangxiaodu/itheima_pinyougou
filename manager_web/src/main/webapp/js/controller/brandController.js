app.controller("brandController",function ($scope,$controller,brandService) {
    $controller("baseController",{$scope:$scope});

    $scope.search = function (pageNum,pageSize,searchEntity) {
        brandService.search(pageNum,pageSize,searchEntity).success(function (response) {

            $scope.searchList = response.rows;
            $scope.updatePaginationConfTotal(response.total);
        })
    }
    $scope.saveOrUpdate = function () {
        var id = $scope.insertOrUpdateEntity.id;
        var response;
        if(id == null){
            response =brandService.insert($scope.insertOrUpdateEntity);
        }else{
            response =brandService.update($scope.insertOrUpdateEntity);
        }
        if(!response.success){
            alert(response.info)
        }else{
            //$scope.reloadList();
        }
    }
    $scope.findById = function (id) {
        brandService.findById(id).success(function (response) {
           $scope.insertOrUpdateEntity = response;
        });
    }
    // 删除逻辑
    $scope.delete = function () {
        var ids = $scope.getSelections();
        brandService.delete(ids).success(function (response) {
            if(!response.success){
                alert(response.info)
            }else{
                $scope.reloadList();
            }
        })
    }
})

