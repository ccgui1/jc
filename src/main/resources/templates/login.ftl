<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>建策学员管理系统</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/a3common.css">
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div id="main" class="main-warp">
    <div class="main-content">
        <div class="formDiv">
            <h2 class="text-center">建策学员管理系统</h2>
            <div style="color: red">${msg!""}</div>
            <form action="userLogin" id="loginForm" method="post">
                <div class="dataform">
                    <div class="input-warp gap">
                        <span class="input-icon iconfont icon-yonghu1"></span>
                        <input id="userName" name="username" type="text" class="inputs" placeholder="账号" maxlength="20">
                    </div>
                    <div class="error-content">
                        <span id="userNameErr" class="errMsg"></span>
                    </div>
                    <div class="input-warp gap">
                        <span class="input-icon iconfont icon-baomi"></span>
                        <input class="inputs" type="password" name="password" placeholder="密码" id="pwd" maxlength="20">
                    </div>
                    <div class="error-content">
                        <span id="passwordErr" class="errMsg"></span>
                    </div>

                    <div class="btn-warp gap">
                        <div class="text-center">
                            <input type="hidden" value="jsform" id="_app"/>
                            <button type="button" id="btnLogin" class="btn btn-block lgbtn blue">登录</button>
                        </div>
                    </div>
                    <div class="gap">
                        <div class="pretty-box">
                            <input type="checkbox" value="1" name="REMEMBER" id="remember" class="">
                            <label for="remember" style="font-weight: normal">记住我</label>
                        </div>
                    </div>

                </div>
            </form>

        </div>
    </div>
</div>
<script src="/js/jquery-3.6.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.cookie.js"></script>
<script src="/js/login.js"></script>
</body>
</html>
