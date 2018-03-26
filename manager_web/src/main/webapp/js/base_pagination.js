var app=angular.module('pinyougou',['pagination']);
Array.prototype.removeObj=function (obj) {
    var idx = this.indexOf(obj);
    this.splice(idx,1);
}