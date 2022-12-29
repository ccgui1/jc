<!DOCTYPE html>
<html lang="en">
<head>
    <title>建策学员管理系统</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/common.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/login">建策学员管理系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <#if Session.role == "1">
                <li><a href="/user/index">账户</a></li>
                </#if>
                <input type="hidden" id="userRole" value="${Session.role!"2"}">
                <li class="active"><a href="#">学生信息<span class="sr-only">(current)</span></a></li>
                <li><a href="/class/index">班级</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        ${Session.name!"无名"}
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/updatePass">修改密码</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/logout">注销</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="container">
    <div class="row">
            <div class="panel">
                <div class="panel-body">
                    <form class="form-inline" id="downloadForm" target="_self" method="post" action="/student/downloadStudents">
                        <div class="form-group row">
                            <label for="sstudentNm" >姓名</label>
                            <input type="text" id="sstudentNm" name="sstudentNm" placeholder="学生姓名" maxlength="20" class="form-control input-sm">
                            <label for="scollege" >院校</label>
                            <input type="text" id="scollege" name="scollege" placeholder="院校名称" maxlength="20" class="form-control input-sm">
                            <label for="sadviser" >课程顾问</label>
                            <input type="text" id="sadviser" name="sadviser" placeholder="课程顾问名" maxlength="20" class="form-control input-sm">

                            <label for="suid" >身份证</label>
                            <input type="text" id="suid" name="suid" placeholder="身份证编号" maxlength="30" class="form-control input-sm">
                            <label for="sphone" >手机号码</label>
                            <input type="text" id="sphone" name="sphone" placeholder="手机号码" maxlength="20" class="form-control input-sm">
                            <button type="button" class="btn btn-primary " id="searchButton" style="margin: 5px 0 0 35px">搜索</button>
                            <a href="javascript:void(0)" class="btn btn-success" id="downloadStudent" style="margin: 5px 0 0 5px">下载</a>
                        </div>
                        <a href="javascript:void(0)" class="pull-right btn btn-success" id="addStudent" style="margin-bottom: 5px">注册学生</a>
                    </form>
                    <br>
                    <table id="studentTable"  class="table table-striped table-hover table-bordered" style="min-width:1000px;">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>姓名</th>
                            <th>院校</th>
                            <th>专业</th>
                            <th>身份证号</th>
                            <th>手机</th>
                            <th>邮箱</th>
                            <th>QQ</th>
                            <th>课程顾问</th>
                            <th>报名课程</th>
                            <th>报名时间</th>
                            <th>已通过考试</th>
                            <th>就业单位</th>
                            <th>备注</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <span id="totalSize" class="pull-left"></span>
                    <input type="hidden" id="pageNum" value="1">
                    <ul id="pagination" class="pagination pull-right">
                    </ul>
                </div><!-- /input-group -->

            </div>
    </div>
    <!--bootstrap实现弹出模态框编辑-->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">学生信息</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="studentNm" class="redStar">姓名</label>
                        <input type="hidden" name="studentId" id="studentId">
                        <input type="text" name="studentNm" class="form-control" id="studentNm" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="college">院校</label>
                        <input type="text" name="college" class="form-control" id="college" maxlength="40">
                    </div>
                    <div class="form-group">
                        <label for="majorNm">专业</label>
                        <input type="text" name="majorNm" class="form-control" id="majorNm" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="uid">身份证号</label>
                        <input type="text" name="uid" class="form-control" id="uid" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="phone" class="redStar">手机</label>
                        <input type="text" name="phone" class="form-control" id="phone" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="email">邮箱</label>
                        <input type="text" name="email" class="form-control" id="email" maxlength="100">
                    </div>
                    <div class="form-group">
                        <label for="qq">qq</label>
                        <input type="text" name="qq" class="form-control" id="qq" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="adviser">课程顾问</label>
                        <input type="text" name="adviser" class="form-control" id="adviser" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="signCourse">报名课程</label>
                        <input type="text" name="signCourse" class="form-control" id="signCourse" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="signTime">报名时间</label>
                        <input type="text" name="signTime" class="form-control" id="signTime" maxlength="40">
                    </div>
                    <div class="form-group">
                        <label for="examPassed">已通过考试</label>
                        <input type="text" name="examPassed" class="form-control" id="examPassed" maxlength="40">
                    </div>
                    <div class="form-group">
                        <label for="employmentUnit">就业单位</label>
                        <input type="text" name="employmentUnit" class="form-control" id="employmentUnit" maxlength="40">
                    </div>
                    <div class="form-group">
                        <label for="memo">备注</label>
                        <input type="text" name="memo" class="form-control" id="memo" maxlength="256">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" id="closeBtn" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="saveBtn" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                </div>
            </div>
        </div>
    </div>
    <!--bootstrap实现弹出模态框查看-->
    <div class="modal fade" id="myModalView" tabindex="1" role="dialog" aria-labelledby="myModalLabelView" data-backdrop="static">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">学生信息</h4>
                </div>
                <div class="modal-body">
                    <table class="table table-striped">
                        <thead>
                            <th width="25%">属性名</th><th>属性值</th>
                        </thead>
                        <tbody>
                            <tr><td>姓名</td><td><label id="vstudentNm"></label></td></tr>
                            <tr><td>院校</td><td><label id="vcollege"></label></td></tr>
                            <tr><td>专业</td><td><label id="vmajorNm"></label></td></tr>
                            <tr><td>身份证号</td><td><label id="vuid"></label></td></tr>
                            <tr><td>手机</td><td><label id="vphone"></label></td></tr>
                            <tr><td>邮箱</td><td><label id="vemail"></label></td></tr>
                            <tr><td>qq</td><td><label id="vqq"></label></td></tr>
                            <tr><td>课程顾问</td><td><label id="vadviser"></label></td></tr>
                            <tr><td>报名课程</td><td><label id="vsignCourse"></label></td></tr>
                            <tr><td>报名时间</td><td><label id="vsignTime"></label></td></tr>
                            <tr><td>已通过考试</td><td><label id="vexamPassed"></label></td></tr>
                            <tr><td>就业单位</td><td><label id="vemploymentUnit"></label></td></tr>
                            <tr><td>备注</td><td><label id="vmemo"></label></td></tr>
                        </tbody>
                    </table>
                    <div class="form-group">
                    </div>
                    <table id="viewStudentTable" class="table table-striped">
                        <caption>约课记录</caption>
                        <thead>
                        <th>课程</th>
                        <th>班型</th>
                        <th>日期</th>
                        <th>任课老师</th>
                        <th>班主任</th>
                        <th>约课时间</th>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>

                </div>
                <div class="modal-footer">
                    <button type="button" id="closeBtn" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>确定</button>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="/js/jquery-3.6.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/student.js"></script>
</body>
</html>