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
                <li><a href="/student/index">学生信息</a></li>
                <li class="active"><a href="#">班级<span class="sr-only">(current)</span></a></li>

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
                    <form class="form-inline">
                        <div class="form-group row">
                            <label for="scourseNm" >课程</label>
                            <select name="scourseNm" id="scourseNm" class="form-control">
                                <option value="-1">所有课程</option>
                                <#list courseMap?keys as key>
                                    <option value="${courseMap[key]}">${courseMap[key]}</option>
                                </#list>
                            </select>
                            <label for="steacherNm" >任课老师</label>
                            <input type="text" id="steacherNm" placeholder="任课老师" maxlength="20" class="form-control">
                            <label for="sdateDetail" >上课时间</label>
                            <input type="text" id="sdateDetail" placeholder="上课时间" maxlength="100" class="form-control">
                            <button type="button" class="btn btn-primary" id="searchButton">搜索</button>
                        </div>
                        <a href="javascript:void(0)" class="pull-right btn btn-success" id="addClass">创建班级</a>
                    </form>
                    <br>
                    <table id="classTable"  class="table table-striped table-hover table-bordered" style="min-width:1000px;">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>课程</th>
                            <th>班型</th>
                            <th>日期</th>
                            <th width="150px">上课时间</th>
                            <th>任课老师</th>
                            <th>班主任</th>
                            <th>创建时间</th>
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
    <!--bootstrap实现弹出模态框-->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">班级信息</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="classId" id="classId">
                    <div class="form-group">
                        <label for="courseNm" class="redStar">课程</label>
                        <select name="courseNm" id="courseNm" class="form-control">
                            <#list courseMap?keys as key>
                                <option value="${courseMap[key]}">${courseMap[key]}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="classType" class="redStar">班型</label>
                        <input type="text" name="classType" class="form-control" id="classType" maxlength="10">
                    </div>
                    <div class="form-group">
                        <label for="dateRange">日期</label>
                        <input type="text" name="dateRange" class="form-control" id="dateRange" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="dateDetail">上课时间</label>
                        <input type="text" name="dateDetail" class="form-control" id="dateDetail" maxlength="100">
                    </div>
                    <div class="form-group">
                        <label for="teacherNm" class="redStar">任课老师</label>
                        <input type="text" name="teacherNm" class="form-control" id="teacherNm" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="headmaster">班主任</label>
                        <input type="text" name="headmaster" class="form-control" id="headmaster" maxlength="20">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" id="closeBtn" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="saveBtn" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/jquery-3.6.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/class.js"></script>
</body>
</html>