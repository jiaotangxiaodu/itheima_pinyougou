//控制层
app.controller('goodsController', function ($scope, $controller, goodsService,uploadService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        $scope.entity.goodsDesc.introduction = editor.html();
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    // $scope.reloadList();//重新加载
                    alert("保存成功");
                    $scope.initEntity();
                    editor.html("");
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }
    $scope.categoryOptions = [];
    //加载分类
    $scope.getCategroyOptionsByParentId = function (parentId,level) {
        if(parentId == null){
            $scope.categoryOptions[level] = [];
        }
        goodsService.getCategroyOptionsByParentId(parentId).success(function (response) {
            $scope.categoryOptions[level] = response;
        })
    }
    $scope.image_entity = {}
    $scope.upload = function () {
        uploadService.upload().success(function (response) {
            if(response.success){
                var url = response.info;
                $scope.image_entity.url = url;
            }else{
                alert(response.info)
            }
        }).error(function () {
            alert("上传发生错误");
        })
    }
    $scope.addImage = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }
    $scope.initEntity = function () {
        $scope.entity = {goods:{},goodsDesc:{itemImages:[]}};
    }
});	
