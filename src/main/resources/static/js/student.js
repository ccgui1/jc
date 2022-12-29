$(document).ready(function(){
    //初始化角色权限隐藏按钮
    initRole();
    loadData();
    $("#searchButton").click(function(){
        loadData();
    });
    $("#sstudentNm").keydown(function (e) {
        if (e.which == 13) {
            $("#searchButton").click();
            return false;
        }
    });
    $("#scollege").keydown(function (e) {
        if (e.which == 13) {
            $("#searchButton").click();
            return false;
        }
    });
    $("#sadviser").keydown(function (e) {
        if (e.which == 13) {
            $("#searchButton").click();
            return false;
        }
    });

    $("#downloadStudent").click(function(){
        $('#downloadForm').submit();
    });

    //注册新增按钮的事件
    $("#addStudent").click(function (e) {
        $('#studentId').val("");
        $("#studentNm").val("");
        $('#college').val("");
        $('#majorNm').val("");
        $("#uid").val("");
        $('#phone').val("");
        $('#email').val("");
        $("#qq").val("");
        $('#adviser').val("");
        $('#signCourse').val("");
        $("#signTime").val("");
        $("#examPassed").val("");
        $("#employmentUnit").val("");
        $("#memo").val("");
        $("#myModalLabel").text("添加学生");
        $('#myModal').modal();
    });
    $('#myModal').on('show.bs.modal', function () {
    })
    $("#saveBtn").click(function () {
        var para={
            studentNm:$("#studentNm ").val(),
            college:$('#college').val(),
            majorNm:$('#majorNm').val(),
            uid:$("#uid").val(),
            phone:$('#phone').val(),
            email:$('#email').val(),
            qq:$("#qq").val(),
            adviser:$('#adviser').val(),
            signCourse:$('#signCourse').val(),
            signTime:$("#signTime").val(),
            examPassed:$("#examPassed").val(),
            employmentUnit:$("#employmentUnit").val(),
            memo:$("#memo").val()
        }
        if(para.studentNm==null||para.studentNm==undefined||para.studentNm==""){
            prompt("请输入学生姓名", 'alert-warning');
            return;
        }
        if(para.phone==null||para.phone==undefined||para.phone==""){
            prompt("请输入电话号码", 'alert-warning');
            return;
        }
        var url="/student/addStudent";
        if($("#myModalLabel").text()=="编辑学生"){
            para.studentId=$('#studentId').val();
            url="/student/updateStudent";
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
        $("#downloadStudent").hide();
        $("#addStudent").hide();
    }
}

function loadData(){
    var para={
        pageNum:$("#pageNum").val(),
        pageSize:10,
        studentNm:$('#sstudentNm').val(),
        college:$("#scollege").val(),
        adviser:$('#sadviser').val(),
        uid:$("#suid").val(),
        phone:$('#sphone').val(),
    }

    $.ajax({
        url:"/student/searchStudents",
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
                    "<td>"+data.content[i]["student_id"]+"</td>\n" +
                    "<td>"+data.content[i]["student_nm"]+"</td>\n" +
                    "<td>"+data.content[i]["college"]+"</td>\n" +
                    "<td>"+data.content[i]["major_nm"]+"</td>\n" +
                    "<td>"+data.content[i]["uid"]+"</td>\n" +
                    "<td>"+data.content[i]["phone"]+"</td>\n" +
                    "<td>"+data.content[i]["email"]+"</td>\n" +
                    "<td>"+data.content[i]["qq"]+"</td>\n" +
                    "<td>"+data.content[i]["adviser"]+"</td>\n" +
                    "<td>"+data.content[i]["sign_course"]+"</td>\n" +
                    "<td>"+data.content[i]["sign_time"]+"</td>\n" +
                    "<td>"+data.content[i]["exam_passed"]+"</td>\n" +
                    "<td>"+data.content[i]["employment_unit"]+"</td>\n" +
                    "<td>"+data.content[i]["memo"]+"</td>\n" +
                    "<td class=\"text-center\">\n"+
                    "<a studentId='"+data.content[i]["student_id"]+"' class=\"btn btn-primary btn-sm edit-sys\" onclick='viewStudent(this);'>查看</a>\n" ;
                    if($("#userRole").val()!="3") {
                        html+="<a studentId='" + data.content[i]["student_id"] + "' class=\"btn btn-primary btn-sm edit-sys\" onclick='updateStudent(this);'>编辑</a>\n" ;
                        html+="<a studentId='" + data.content[i]["student_id"] + "' class=\"btn btn-danger btn-sm del-sys\" onclick='delStudent(this)'>删除</a>\n" ;
                    }
                html+="</td>\n" +
                    "</tr>";
            }
            $("#studentTable > tbody").html(html);
            hidenTableCol();

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
//隐藏部分列
function hidenTableCol(){
    $("#studentTable > thead > tr").children().eq(3).hide();
    // $("#studentTable > thead > tr").children().eq(4).hide();
    $("#studentTable > thead > tr").children().eq(6).hide();
    $("#studentTable > thead > tr").children().eq(7).hide();
    $("#studentTable > thead > tr").children().eq(12).hide();
    $("#studentTable > thead > tr").children().eq(13).hide();
    $("#studentTable > tbody > tr").each(function () {
        $(this).children().eq(3).hide();
        // $(this).children().eq(4).hide();
        $(this).children().eq(6).hide();
        $(this).children().eq(7).hide();
        $(this).children().eq(12).hide();
        $(this).children().eq(13).hide();
    });
}

function viewStudent(obj){
    $('#vstudentNm').text($(obj).parent().parent().children().eq(1).text());
    $('#vcollege').text($(obj).parent().parent().children().eq(2).text());
    $("#vmajorNm").text($(obj).parent().parent().children().eq(3).text());
    $('#vuid').text($(obj).parent().parent().children().eq(4).text());
    $('#vphone').text($(obj).parent().parent().children().eq(5).text());
    $("#vemail").text($(obj).parent().parent().children().eq(6).text());
    $('#vqq').text($(obj).parent().parent().children().eq(7).text());
    $('#vadviser').text($(obj).parent().parent().children().eq(8).text());
    $("#vsignCourse").text($(obj).parent().parent().children().eq(9).text());
    $('#vsignTime').text($(obj).parent().parent().children().eq(10).text());
    $('#vexamPassed').text($(obj).parent().parent().children().eq(11).text());
    $('#vemploymentUnit').text($(obj).parent().parent().children().eq(12).text());
    $('#vmemo').text($(obj).parent().parent().children().eq(13).text());

    $.ajax({
        url:"/student/getStudentClassList",
        type:"POST",
        dataType:"json",
        data:{"studentId":$(obj).attr('studentId')},
        success:function(data){
            let html="";
            for(let i=0,l=data.length;i<l;i++){
                html+="<tr>\n" +
                    "<td>"+data[i]["course_nm"]+"</td>\n" +
                    "<td>"+data[i]["class_type"]+"</td>\n" +
                    "<td>"+data[i]["date_range"]+"</td>\n" +
                    "<td>"+data[i]["teacher_nm"]+"</td>\n" +
                    "<td>"+data[i]["headmaster"]+"</td>\n" +
                    "<td>"+data[i]["create_time"]+"</td>\n" +
                    "</tr>";
            }
            $("#viewStudentTable > tbody").html(html);
        },
        error:function(){
            prompt("请求error!", 'alert-danger');
        }
    });

    $("#myModalLabelView").text("学生详情");
    $('#myModalView').modal();
}
function updateStudent(obj){
    $("#studentId").val($(obj).parent().parent().children().eq(0).text());
    $('#studentNm').val($(obj).parent().parent().children().eq(1).text());
    $('#college').val($(obj).parent().parent().children().eq(2).text());
    $("#majorNm").val($(obj).parent().parent().children().eq(3).text());
    $('#uid').val($(obj).parent().parent().children().eq(4).text());
    $('#phone').val($(obj).parent().parent().children().eq(5).text());
    $("#email").val($(obj).parent().parent().children().eq(6).text());
    $('#qq').val($(obj).parent().parent().children().eq(7).text());
    $('#adviser').val($(obj).parent().parent().children().eq(8).text());
    $("#signCourse").val($(obj).parent().parent().children().eq(9).text());
    $('#signTime').val($(obj).parent().parent().children().eq(10).text());
    $('#examPassed').val($(obj).parent().parent().children().eq(11).text());
    $('#employmentUnit').val($(obj).parent().parent().children().eq(12).text());
    $('#memo').val($(obj).parent().parent().children().eq(13).text());
    $("#myModalLabel").text("编辑学生");
    $('#myModal').modal();
}

function delStudent(obj){
    if(confirm("真的要删除吗?")){
        $.ajax({
            url:"/student/delStudent",
            type:"POST",
            dataType:"json",
            data:{"studentId":$(obj).attr('studentId')},
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
