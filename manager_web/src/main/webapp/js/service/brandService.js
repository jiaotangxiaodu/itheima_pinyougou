app.service("brandService",function ($http) {
    var baseUrl = "../brand/"
    this.search = function (pageNum,pageSize,searchEntity) {
        if(searchEntity == null){
            return $http.get(baseUrl+"findByPage.do?pageNum="+pageNum+"&pageSize="+pageSize);
        }else{
            return $http.post(baseUrl+"search.do?pageNum="+pageNum+"&pageSize="+pageSize,searchEntity);
        }
    }
    this.insert = function (brand) {
        return $http.post(baseUrl+"add.do",brand);
    }
    this.update = function (brand) {
        return $http.post(baseUrl+"update.do",brand);
    }
    this.findById = function (id) {
        return $http.get(baseUrl+"findById.do?id="+id);
    }
    this.delete = function (ids) {
        return $http.get(baseUrl+"delete.do?ids="+ids);
    }
})