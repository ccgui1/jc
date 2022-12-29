$(document).ready(function(){
    $("#updateButton").click(function(){
        var orgPass=$("#orgPass").val();
        if(orgPass==null||orgPass==undefined||orgPass==""){
            //alert-success 成功提示
            //alert-danger 失败提示
            //alert-warning 提醒
            //alert-info 信息提示
            //alert-pormpt 信息提示
            prompt("原密码不能为空!", 'alert-warning');
            return;
        }
        var newPass=$("#newPass").val();
        if(newPass==null||newPass==undefined||newPass==""){
            prompt("新密码不能为空!", 'alert-warning');
            return;
        }
        var varPass=$("#varPass").val();
        if(newPass!=varPass){
            prompt("两次密码不一致,请重新输入!", 'alert-warning');
            return;
        }
        var data={
            "orgPass":orgPass,
            "newPass":newPass
        }
        $.ajax({
            url:"/updatePass",
            type:"POST",
            dataType:"json",
            data:data,
            success:function(data){
                if(data.status=="200"){
                    prompt(data.msg, 'alert-success');
                    window.location.href="/login";
                }else{
                    prompt(data.msg, 'alert-danger');
                }
            },
            error:function(){
                prompt("请求error!", 'alert-danger');
            }
        });
    });
});

var prompt = function (message, style, time) {
    style = (style === undefined) ? 'alert-success' : style;
    time = (time === undefined) ? 1200 : time;
    $('<div id="promptModal">')
        .appendTo('body')
        .addClass('alert ' + style)
        .css({
            "display": "block",
            "z-index": 99999,
            "left": ($(document.body).outerWidth(true) - 120) / 2,
            "top": ($(window).height() - 45) / 2,
            "position": "absolute",
            "padding": "20px",
            "border-radius": "5px"
        })
        .html(message)
        .show()
        .delay(time)
        .fadeOut(10, function () {
            $('#promptModal').remove();
        });
}
