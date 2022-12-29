$(document).ready(function(){
    initRole();
    loadData();
    $("#searchButton").click(function(){
        loadData();
    });
    $("#steacherNm").keydown(function (e) {
        if (e.which == 13) {
            $("#searchButton").click();
            return false;
        }
    });
    $("#sdateDetail").keydown(function (e) {
        if (e.which == 13) {
            $("#searchButton").click();
            return false;
        }
    });
    //注册新增按钮的事件
    $("#addClass").click(function (e) {
        $('#courseNm')[0].selectedIndex = 0;
        $('#classType').val("");
        $('#dateRange').val("");
        $("#dateDetail ").val("");
        $('#teacherNm').val("");
        $('#headmaster').val("");
        $("#myModalLabel").text("添加班级");
        $('#myModal').modal();
    });
    $('#myModal').on('show.bs.modal', function () {
    })
    $("#saveBtn").click(function () {
        var para={
            courseNm:$('#courseNm').find("option:selected").text(),
            classType:$('#classType').val(),
            dateRange:$('#dateRange').val(),
            dateDetail:$('#dateDetail').val(),
            teacherNm:$('#teacherNm').val(),
            headmaster:$('#headmaster').val()
        }
        if(para.classType==null||para.classType==undefined||para.classType==""){
            prompt("请输入班型", 'alert-warning');
            return;
        }
        if(para.teacherNm==null||para.teacherNm==undefined||para.teacherNm==""){
            prompt("请输入任课老师", 'alert-warning');
            return;
        }
        var url="/class/addClass";
        if($("#myModalLabel").text()=="编辑班级"){
            para.classId=$('#classId').val();
            url="/class/updateClass";
        }
        $.ajax({
            url:url,
            type:"POST",
            contentType: "application/json",
            dataType:"json",
            data:JSON.stringify(para),
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
function initRole(){
    if($("#userRole").val()=="3"){
        $("#addClass").hide();
    }
}
function loadData(){
    var para={
        pageNum:$("#pageNum").val(),
        pageSize:10,
        courseNm:$('#scourseNm').find("option:selected").text(),
        teacherNm:$("#steacherNm").val(),
        dateDetail:$('#sdateDetail').val()
    }

    $.ajax({
        url:"/class/searchClass",
        type:"POST",
        dataType:"json",
        contentType: "application/json",
        data:JSON.stringify(para),
        beforeSend:function(xhr){
        },
        success:function(data,textStatus,jqxhr){
            //新加table
            var html="";
            for(var i=0,l=data.content.length;i<l;i++){
                html+="<tr>\n" +
                    "<td>"+data.content[i]["class_id"]+"</td>\n" +
                    "<td>"+data.content[i]["course_nm"]+"</td>\n" +
                    "<td>"+data.content[i]["class_type"]+"</td>\n" +
                    "<td>"+data.content[i]["date_range"]+"</td>\n" +
                    "<td>"+data.content[i]["date_detail"]+"</td>\n" +
                    "<td>"+data.content[i]["teacher_nm"]+"</td>\n" +
                    "<td>"+data.content[i]["headmaster"]+"</td>\n" +
                    "<td>"+data.content[i]["create_time"]+"</td>\n" +
                    "<td class=\"text-center\">\n"+
                    "<a class=\"btn btn-primary btn-sm edit-sys\" target='_blank' href='/clastu/index/"+data.content[i]["class_id"]+"'>维护学生</a>\n" ;
                if($("#userRole").val()!="3") {
                    html += "<a classId='" + data.content[i]["class_id"] + "' class=\"btn btn-primary btn-sm edit-sys\" onclick='updateClass(this);'>编辑</a>\n";
                    html += "<a classId='" + data.content[i]["class_id"] + "' class=\"btn btn-danger btn-sm del-sys\" onclick='delClass(this)'>删除</a>\n";
                }
                html+="</td>\n" +
                    "</tr>";
            }
            $("#classTable > tbody").html(html);
            $("#totalSize").html("总共 "+data.totalSize+" 条记录");
            //添加分页 jumpto
            $("#pageNum").val(data.pageNum);
            var page="<li><a href=\"#\" onclick='jumpto(1)'>&laquo;</a></li>";
            for (var i = 1; i <= data.totalPages; i++) {
                if(data.pageNum==i){
                    page+="<li  class=\"active\"><a href=\"#\">"+i+"</a></li>";
                }else{
                    page+="<li><a href=\"#\"  onclick=jumpto("+i+")>"+i+"</a></li>";
                }
            }
            page+="<li><a href=\"#\"  onclick=jumpto("+data.totalPages+")>&raquo;</a></li>";
            $("#pagination").html(page);
        },
        error:function(){
            prompt("请求error!", 'alert-danger');
        },
        complete:function(xhr){
        }
    });
}

function updateClass(obj){
    $("#classId").val($(obj).parent().parent().children().eq(0).text());
    var tmpCourseNm=$(obj).parent().parent().children().eq(1).text();
    var count1=$("#courseNm option").length;
    for(var i=0;i<count1;i++){
        if($("#courseNm").get(0).options[i].text == tmpCourseNm){
            $("#courseNm").get(0).options[i].selected = true;
            break;
        }
    }
    $('#classType').val($(obj).parent().parent().children().eq(2).text());
    $("#dateRange").val($(obj).parent().parent().children().eq(3).text());
    $('#dateDetail').val($(obj).parent().parent().children().eq(4).text());
    $('#teacherNm').val($(obj).parent().parent().children().eq(5).text());
    $("#headmaster").val($(obj).parent().parent().children().eq(6).text());

    $("#myModalLabel").text("编辑班级");
    $('#myModal').modal();
}

function delClass(obj){
    if(confirm("真的要删除吗?")){
        $.ajax({
            url:"/class/delClass",
            type:"POST",
            dataType:"json",
            data:{"classId":$(obj).attr('classId')},
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

function jumpto(obj){
    $("#pageNum").val(obj);
    loadData();
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
