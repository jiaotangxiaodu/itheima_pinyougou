app.controller('payController', function ($scope, payService, $location) {
    //本地生成二维码
    $scope.createNative = function () {
        payService.createNative().success(
            function (response) {
                $scope.money = (response.total_fee / 100).toFixed(2);	//金额
                $scope.out_trade_no = response.out_trade_no;//订单号
                // alert("code_url = " + response.code_url);
                // console.log("QRious二维码扫不出来这个BUG我也很绝望啊:"+response.code_url);
                //二维码
                // var qr = new QRious({
                //     element: document.getElementById('qrious'),
                //     size: 250,
                //     level: 'H',
                //     value: response.code_url
                // });

                // 果然还是QRCode好用
                new QRCode(document.getElementById('qrious'), response.code_url);
                queryPayStatus(response.out_trade_no);//查询支付状态
            }
        );
    }

    //查询支付状态
    queryPayStatus = function (out_trade_no) {
        payService.queryPayStatus(out_trade_no).success(
            function (response) {
                if (response.success) {
                    location.href = "paysuccess.html#?money=" + $scope.money;
                } else {
                    if (response.message == '二维码超时') {
                        $scope.createNative();//重新生成二维码
                    } else {
                        location.href = "payfail.html";
                    }

                }
            }
        );
    }
    //获取金额
    $scope.getMoney = function () {
        return $location.search()['money'];
    }


});
