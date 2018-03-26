/*如果使用分页控件,子类必须有search(pageNum.pageSize,searchEntity)方法*/
app.controller("baseController",function ($scope) {
    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function(){
            $scope.reloadList();//重新加载
        }
    };
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.searchEntity);
    }
    //更新条目总数
    $scope.updatePaginationConfTotal = function (totalItem) {
        $scope.paginationConf.totalItems = totalItem;
    }

    // 处理多选框
    $scope.selections =[];
    $scope.updateSelections = function ($event,id) {
        var checked = $event.target.checked;
        if(checked){
            $scope.selections.push(id);
        }else{
            var idx = $scope.selections.indexOf(id);
            $scope.selections.splice(idx,1);
        }
    }
    $scope.getSelections = function () {
        return $scope.selections;
    }

})