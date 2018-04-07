//控制层
app.controller('goodsController', function ($scope, $controller, goodsService,uploadService,itemCatService,typeTemplateService) {

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
                    alert(response.info);
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
            return;
        }
        itemCatService.findByParentId(parentId).success(function (response) {
            $scope.categoryOptions[level] = response;
        })
    }
    $scope.image_entity = {url:""}
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
        $scope.entity = {goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};
        $scope.entity.goodsDesc.removeSpecificationValue = function (attributeName,attributeValue) {

            for(var i =0 ; i< this.specificationItems.length;i++){
                var specItem = this.specificationItems[i];
                if(specItem.attributeName == attributeName){
                    $scope.removeFromArray(specItem.attributeValue,attributeValue);
                    if(specItem.attributeValue.length == 0){
                        $scope.removeFromArray(this.specificationItems,specItem);
                    }
                    return;
                }
            }
        }
        $scope.entity.goodsDesc.pushSpecificationValue = function (attributeName,attributeValue) {
            for(var i =0 ; i< this.specificationItems.length;i++){
                var specItem = this.specificationItems[i];
                if(specItem.attributeName == attributeName){
                    specItem.attributeValue.push(attributeValue);
                    return;
                }
            }
            this.specificationItems.push({"attributeName":attributeName,"attributeValue":[attributeValue]})
        }
    }
    $scope.initImage = function () {
        $scope.image_entity = {}
    }

    $scope.$watch("entity.goods.category1Id",function (newValue,oldValue) {
        $scope.getCategroyOptionsByParentId(newValue,1);
    })

    $scope.$watch("entity.goods.category2Id",function (newValue,oldValue) {
        $scope.getCategroyOptionsByParentId(newValue,2);
    })
    $scope.$watch("entity.goods.category3Id",function (newValue,oldValue) {
        if(newValue == undefined || newValue == null){
            return;
        }
        itemCatService.findOne(newValue).success(function (response) {
            $scope.entity.goods.typeTemplateId = response.typeId;
        })
    })
    $scope.$watch("entity.goods.typeTemplateId",function (newValue,oldValue) {
        initTypeTemplate();
        if(newValue == undefined || newValue == null){
            return;
        }
        typeTemplateService.findOne(newValue).success(function (response) {
            $scope.typeTemplate = response;
            $scope.typeTemplate.brandIds =JSON.parse($scope.typeTemplate.brandIds);
            $scope.typeTemplate.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
        })
        typeTemplateService.findSpecListById(newValue).success(function (response) {
            $scope.specList = response;
        })
    })
    function initTypeTemplate() {
        $scope.entity.goodsDesc.specificationItems=[];
        $scope.entity.goods.brandId = undefined;
    }
    $scope.updateSpecAttribute = function ($event,attributeName,attributeValue) {
        if($event.target.checked){
            $scope.entity.goodsDesc.pushSpecificationValue(attributeName,attributeValue);
        }else{
            $scope.entity.goodsDesc.removeSpecificationValue(attributeName,attributeValue);
        }
    }

    $scope.$watch("entity.goodsDesc.specificationItems",function (newValue,oldValue) {
        $scope.entity.itemList = [];
        var specificationItems = newValue;
        if(specificationItems.length == 0){
            return;
        }

        var valueIndexs = new Array(specificationItems.length);
        for(var j = 0 ; j < specificationItems.length ; j++){
            valueIndexs[j] = 0;
        }
        recursionPush(specificationItems,valueIndexs,0);
    },true);

    function recursionPush(specificationItems,valueIndexs,columnIndex) {
        if(columnIndex == valueIndexs.length){
            var spec = {};
            for(var i =0 ; i < valueIndexs.length ; i++){
                var name = specificationItems[i].attributeName;
                var value = specificationItems[i].attributeValue[valueIndexs[i]].optionName;
                spec[name] = value;
            }
            var item = {"price":0,"stockCount":99999,"status":"0","isDefault":"0","spec":spec};
            $scope.entity.itemList.push(item);
            return;
        }
        for(var j =0;j<specificationItems[columnIndex].attributeValue.length;j++){
            var newValueIndexs = JSON.parse(JSON.stringify(valueIndexs));
            newValueIndexs[columnIndex] = j;
            recursionPush(specificationItems,newValueIndexs,columnIndex+1);
        }
    }

    $scope.findValue = function (obj,key) {
        return obj[key];
    }
});	
