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
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label for="orgPas" class="col-sm-2 control-label redStar">原密码</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="orgPass" placeholder="请输入原密码">
            </div>
        </div>
        <div class="form-group">
            <label for="newPass" class="col-sm-2 control-label redStar">新密码</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="newPass" placeholder="请输入新密码">
            </div>
        </div>
        <div class="form-group">
            <label for="varPass" class="col-sm-2 control-label redStar">确认密码</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="varPass" placeholder="重复输入确认密码">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="updateButton" type="button" class="btn btn-default">确认修改</button>
            </div>
        </div>
    </form>
</div>
<script src="/js/jquery-3.6.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/updatepass.js"></script>
</body>
</html>