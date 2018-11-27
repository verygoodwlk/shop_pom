$(function(){
    $.ajax({
        url:"http://localhost:8084/sso/islogin",
        method:"post",
        dataType:"jsonp"
    });
})

function islogin(data){
    if(data != null){
        //登录成功
        var json = eval("(" + data + ")");
        $("#pid").html(json.name + "您好，欢迎来到<b>ShopCZ商城</b> <a href='http://localhost:8084/sso/logout'>注销</a>");
    } else {
        //未登录
        $("#pid").html("[<a href=\"javascript:login();\">登录</a>][<a >注册</a>]");
    }
}

//进行登录
function login(){
    //获得本地的URL
    var localUrl = location.href;
    //对url进行编码
    localUrl = encodeURI(localUrl);
    localUrl = localUrl.replace("&", "%26");
    location.href="http://localhost:8084/sso/tologin?returnUrl=" + localUrl;
}