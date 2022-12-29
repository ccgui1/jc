$(document).ready(function(){
    loadData();
    $("#searchButton").click(function(){
        loadData();
    });
    $("#suserNm").keydown(function (e) {
        if (e.which == 13) {
            $("#searchButton").click();
            return false;
        }
    });
    //注册新增按钮的事件
    $("#addUser").click(function (e) {
        $("#userId").val("");
        $("#userNm").removeAttr('disabled');
        $('#userNm').val("");
        $('#passwd').val("");
        $("#myModalLabel").text("添加用户");
        $('#myModal').modal();
    });
    $('#myModal').on('show.bs.modal', function () {
    })
    $("#saveBtn").click(function () {
        var para={
            userNm:$('#userNm').val(),
            passwd:$('#passwd').val(),
            role:$('#role').val()
        }
        if(para.userNm==null||para.userNm==undefined||para.userNm==""){
            prompt("请输入用户名", 'alert-warning');
            return;
        }
        if(para.passwd==null||para.passwd==undefined||para.passwd==""){
            prompt("请输入用户密码", 'alert-warning');
            return;
        }

        var url="/user/addUser";
        if($("#myModalLabel").text()=="编辑用户信息"){
            para.userId=$("#userId").val();
            url="/user/updateUser";
        }
        $.ajax({
            url:url,
            type:"POST",
            dataType:"json",
            data:para,
            success:function(data){
                if(data.status=="200"){
                    prompt(data.msg, 'alert-success');
                    loadData();
                    $('#myModal').modal('hide');
                }else{
                    alert(data.msg);
                }
            },
            error:function(){
                prompt("请求error!", 'alert-danger');
            }
        })
    });

});
function loadData(){
    $.ajax({
        url:"/user/searchUsers",
        type:"POST",
        dataType:"json",
        data:{"userNm":$('#suserNm').val().trim()},
        beforeSend:function(xhr){
        },
        success:function(data,textStatus,jqxhr){
            //新加table
            var html="";
            for(var i=0,l=data.length;i<l;i++){
                html+="<tr>\n" +
                    "<td>"+data[i]["user_id"]+"</td>\n" +
                    "<td>"+data[i]["user_nm"]+"</td>\n" +
                    "<td>"+data[i]["passwd"]+"</td>\n" ;
                if(data[i]["role"]==1){
                    html+= "<td>管理员</td>\n" ;
                }else if(data[i]["role"]==2){
                    html+="<td>普通用户</td>\n" ;
                }else{
                    html+="<td>销售</td>\n" ;
                }
                html+="<td class=\"text-center\">\n";
                if(data[i]["role"]!="1") {
                    html+="<a userId='"+data[i]["user_id"]+"' class=\"btn btn-primary btn-sm edit-sys\" onclick='updateUser(this);'>修改</a>\n"+
                    "<a userId='" + data[i]["user_id"] + "' class=\"btn btn-danger btn-sm del-sys\" onclick='delUser(this)'>删除</a>\n";
                }
                html+="</td>\n" +
                    "</tr>";
            }
            $("#userTable > tbody").html(html);
        },
        error:function(){
            prompt("请求error!", 'alert-danger');
        },
        complete:function(xhr){
        }
    });
}

function updateUser(obj){
    $('#userId').val($(obj).parent().parent().children().eq(0).text());
    $("#userNm").attr("disabled","disabled");
    $("#userNm").val($(obj).parent().parent().children().eq(1).text());
    $('#passwd').val($(obj).parent().parent().children().eq(2).text());

    var tmprole=$(obj).parent().parent().children().eq(3).text();
    var count1=$("#role option").length;
    for(var i=0;i<count1;i++){
        if($("#role").get(0).options[i].text == tmprole){
            $("#role").get(0).options[i].selected = true;
            break;
        }
    }

    $("#myModalLabel").text("编辑用户信息");
    $('#myModal').modal();
}

function delUser(obj){
    if(confirm("真的要删除吗?")){
        $.ajax({
            url:"/user/delUser",
            type:"POST",
            dataType:"json",
            data:{"userId":$(obj).attr('userId')},
            success:function(data){
                if(data.status=="200"){
                    prompt(data.msg, 'alert-success');
                    loadData();
                }else{
                    prompt(data.msg, 'alert-danger');
                }
            },
            error:function(){
                prompt("请求error!", 'alert-danger');
            }
        });
    }
}

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
