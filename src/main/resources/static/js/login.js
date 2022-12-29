$(document).ready(function(){
    var remberme=$.cookie('remberme');
    if(!(remberme==null || remberme=="" || remberme=='undefined')){
        try {
            let rememberInfo=JSON.parse(remberme);
            $("#remember").prop("checked",true);
            $("#userName").val(rememberInfo.userName);
            $("#pwd").val(rememberInfo.pwd);
        }catch(err) {
            $.removeCookie('remberme');
        }
    }else{
        $("#remember").prop("checked",false);
        $("#userName").val("");
        $("#pwd").val("");
    }
    $("#btnLogin").click(function(){
        //检查输入
        if(checkInput()){
            let status = $("#remember").prop("checked");
            if(status){
                var rememberInfo = {userName:$("#userName").val(),pwd:$("#pwd").val()};
                var jsonRemberInfo=JSON.stringify(rememberInfo);
                $.cookie('remberme', jsonRemberInfo, { expires: 7 });
            }else{
                $.removeCookie('remberme');
            }
            $("#loginForm").submit();
        }
    });

    $("body").keyup(function (e) {
        if (e.keyCode == 13) {
            $("#btnLogin").click();
        }
    });

});

function checkInput(){
    if($("#userName").val().length<1){
        $("#userNameErr").text("请输入用户名");
        return false;
    }
    if($("#pwd").val().length<1){
        $("#passwordErr").text("请输入密码");
        return false;
    }
    return true;
}

