var $table;
$(document).ready(function(){
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

    $("#downloadStudent").click(function(){
        $('#downloadForm').submit();
    });
    //注册新增按钮的事件
    $("#addStudent").click(function (e) {
        $('#pstudentNm').val("");
        $('#pcollege').val("");
        $("#myModalLabel").text("添加学生");
        $('#myModal').modal();
        InitMainTable ();
    });
    $('#myModal').on('show.bs.modal', function () {
    })
    $("#psearchButton").click(function (e) {
        $table.bootstrapTable('refresh')
    });
    $("#pStudentNm").keydown(function (e) {
        if (e.which == 13) {
            $("#psearchButton").click();
            return false;
        }
    });
    $("#pCollege").keydown(function (e) {
        if (e.which == 13) {
            $("#psearchButton").click();
            return false;
        }
    });
    $("#saveBtn").click(function () {
        let rows = $table.bootstrapTable('getSelections');
        let ids = [];
        rows.forEach(item => {
            ids.push(item);
        });
        if(ids.length<1){
            prompt("未选择学生", 'alert-warning');
            return;
        }
        var para={
            classId:$('#classId').val(),
            courseNm:$('#courseNm').val(),
            students:ids,
        }
        $.ajax({
            url:"/clastu/batchAddStudent",
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
    let para={
        pageNum:$("#pageNum").val(),
        pageSize:10,
        classId:$('#classId').val(),
        studentNm:$("#sstudentNm").val(),
    }
    $.ajax({
        url:"/clastu/searchClaStu",
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
                    "<td>"+data.content[i]["student_nm"]+"</td>\n" +
                    "<td>"+data.content[i]["college"]+"</td>\n" +
                    "<td>"+data.content[i]["phone"]+"</td>\n" +
                    "<td class=\"text-center\">\n"+
                    "<a classId='"+data.content[i]["class_id"]+"' studentId='"+data.content[i]["student_id"]+"'  class=\"btn btn-danger btn-sm del-sys\" onclick='delClaStu(this)'>删除</a>\n"+
                    "</td>\n" +
                    "</tr>";
            }
            $("#claStuTable > tbody").html(html);
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
    if($("#userRole").val()=="3"){
        $("#claStuTable > thead > tr").children().eq(3).hide();
    }
    $("#claStuTable > tbody >tr").each(function () {
        if($("#userRole").val()=="3"){
            $(this).children().eq(3).hide();
        }
    });
}

//初始化bootstrap-table的内容
function InitMainTable () {
    $("#pclaStuTable").bootstrapTable('refresh');
    //记录页面bootstrap-table全局变量$table，方便应用
    $table = $('#pclaStuTable').bootstrapTable({
        url: "/clastu/searchStudentForClass",                      //请求后台的URL（*）
        method: 'POST',                      //请求方式（*）
        contentType: "application/json",
        dataType:"json",
        //toolbar: '#toolbar',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        queryParamsType:'', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
        paginationPreText:'<',              //上一页按钮样式
        paginationNextText:'>',             //下一页按钮样式
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）

        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列（选择显示的列）
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "student_id",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
        //得到查询的参数
        queryParams : function (params) {
            //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
            let temp = {
                pageSize: params.pageSize,                         //页面大小
                pageNum: params.pageNumber,   //页码
                studentNm:$("#pStudentNm").val(),
                college:$("#pCollege").val()
            };
            return JSON.stringify(temp);
        },
        columns: [{
            field: 'check',
            checkbox: true,
            align: 'center',
            valign: 'middle',
            formatter: function(value, row) {
                if(row['class_ids'] == undefined||row['class_ids'] == '' || row['class_ids'].indexOf($("#classId").val())==-1){
                    return value;
                }else{
                    return { disabled: true }
                }
            }
        },{
            field: 'student_nm',
            title: '姓名',
            sortable: false
        },{
            field: 'college',
            title: '院校',
            sortable: false
        },{
            field: 'phone',
            title: '手机',
            sortable: false
        },{
            field: 'sign_course',
            title: '已报名课程',
            sortable: false
        },{
            field: 'class_course',
            title: '已约课课程',
            sortable: false
        }],
        onLoadSuccess: function (res) {
            let tableData = new Object();
            tableData.total = res.totalSize;//总记录书
            tableData.pageIndex=res.pageNum;//当前页
            tableData.pageSize=res.pageSize;//每页大小
            tableData.totalPages=res.totalPages;//页码总数
            tableData.rows = res.content;
            $('#pclaStuTable').bootstrapTable("load",tableData);
        },
        onLoadError: function () {
            prompt("数据加载失败!", 'alert-danger');
        }
    });
};

function delClaStu(obj){
    if(confirm("真的要删除吗?")){
        $.ajax({
            url:"/clastu/delClaStu",
            type:"POST",
            dataType:"json",
            data:{"classId":$('#classId').val(),"studentId":$(obj).attr('studentId')},
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

let prompt = function (message, style, time) {
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
