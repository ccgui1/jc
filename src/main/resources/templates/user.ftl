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
                <li class="active"><a href="#">账户<span class="sr-only">(current)</span></a></li>
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
            <div class="panel-body">
                <form class="form-inline">
                    <div class="form-group row">
                        <label for="suserNm" >账户名:</label>
                        <input type="text" id="suserNm" placeholder=""  maxlength="20" class="form-control">
                        <button type="button" class="btn btn-primary" id="searchButton">搜索</button>
                    </div>
                    <a href="javascript:void(0)" class="pull-right btn btn-success" id="addUser">注册账户</a>
                </form>
                <br>
                <table id="userTable"  class="table table-striped table-hover table-bordered" style="min-width:1000px;">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>账户名</th>
                        <th>密码</th>
                        <th>权限</th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div><!-- /input-group -->
        </div>
    </div>
    <!--bootstrap实现弹出模态框-->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">账户信息</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="userNm" class="redStar">账户名</label>
                        <input type="hidden" name="userId" id="userId">
                        <input type="text" name="userNm" class="form-control" id="userNm" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="passwd" class="redStar">密码</label>
                        <input type="text" name="passwd" class="form-control" id="passwd" maxlength="20">
                    </div>
                    <div class="form-group">
                        <label for="role" class="redStar">权限</label>
                        <select name="role" id="role" class="form-control">
                            <option value="2">普通用户</option>
                            <option value="3">销售</option>
                        </select>
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
<script src="/js/user.js"></script>
</body>
</html>