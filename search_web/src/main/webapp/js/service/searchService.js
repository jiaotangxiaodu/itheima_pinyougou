app.service("searchService",function ($http) {
    this.search = function (searchMap) {
        return $http.post("../itemSearch/search.do",searchMap);
    }
})