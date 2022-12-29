<!DOCTYPE html>
<html lang="en">
<head>
    <title>建策学员管理系统</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/bootstrap-table.min.css" rel="stylesheet">
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

            <h2>班级:${classRow["class_id"]}-${classRow["class_type"]}-${classRow["date_range"]}-${classRow["teacher_nm"]}</h2>
            <div class="panel-body">
                <form class="form-inline" id="downloadForm" target="_self" method="post" action="/clastu/downloadClsStu">
                    <input type="hidden" id="classId" name="classId" value="${classRow["class_id"]}">
                    <input type="hidden" id="courseNm" name="courseNm" value="${classRow["course_nm"]}">
                    <div class="form-group row">
                        <label for="sstudentNm">姓名</label>
                        <input type="text" id="sstudentNm" placeholder="学生名字" maxlength="20">
                        <button type="button" class="btn btn-primary" id="searchButton">搜索</button>
                        <a href="javascript:void(0)" class="btn btn-success" id="downloadStudent">下载</a>
                    </div>
                    <a href="javascript:void(0)" class="pull-right btn btn-success" id="addStudent">学生约课</a>
                </form>
                <br>
                <table id="claStuTable"  class="table table-striped table-hover table-bordered">
                    <thead>
                    <tr>
                        <th>姓名</th>
                        <th>院校</th>
                        <th>手机</th>
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
                    <h4 class="modal-title" id="myModalLabel">学员检索</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="pStudentNm">姓名</label>
                        <input type="text" id="pStudentNm" placeholder="学生名字" maxlength="20">
                        <label for="pCollege">院校</label>
                        <input type="text" id="pCollege" placeholder="院校名称" maxlength="40">
                        <button type="button" class="btn btn-primary" id="psearchButton">搜索</button>
                    </div>
                    <div class="form-group">
                        <table id="pclaStuTable"  class="table table-striped table-hover table-bordered">
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" id="closeBtn" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>取消</button>
                    <button type="button" id="saveBtn" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>确定</button>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="/js/jquery-3.6.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/bootstrap-table.min.js"></script>
<script src="/js/bootstrap-table-zh-CN.min.js"></script>
<script src="/js/clastu.js"></script>
</body>
</html>