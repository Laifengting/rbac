<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="keys" content="">
        <meta name="author" content="">
        <link rel="stylesheet" href="${APP_PATH}/static/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${APP_PATH}/static/css/font-awesome.min.css">
        <link rel="stylesheet" href="${APP_PATH}/static/css/login.css">
        <style>

        </style>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
                </div>
            </div>
        </nav>

        <div class="container">
            <h1 style="color: red">${param.errMsg}</h1>
            <form id="loginForm" action="doLogin" method="post" class="form-signin" role="form">
                <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 用户登录</h2>
                <div class="form-group has-success has-feedback">
                    <input type="text" class="form-control" id="loginacct" name="loginacct" placeholder="请输入登录账号" autofocus>
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
                <div class="form-group has-success has-feedback">
                    <input type="text" class="form-control" id="userpwd" name="userpwd" placeholder="请输入登录密码" style="margin-top:10px;">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="form-group has-success has-feedback">
                    <select class="form-control">
                        <option value="member">会员</option>
                        <option value="user">管理</option>
                    </select>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> 记住我
                    </label>
                    <br>
                    <label>
                        忘记密码
                    </label>
                    <label style="float:right">
                        <a href="reg.html">我要注册</a>
                    </label>
                </div>
                <a class="btn btn-lg btn-success btn-block" onclick="dologin()"> 登录</a>
            </form>
        </div>
        <script src="${APP_PATH}/static/jquery/jquery-2.1.1.min.js"></script>
        <script src="${APP_PATH}/static/bootstrap/js/bootstrap.min.js"></script>
        <script src="${APP_PATH}/static/layer/layer.js"></script>
        <script>
            function dologin() {
                // 非空校验
                var loginacct = $("#loginacct").val();
                // 表单元素的取值不可能为 null
                if (loginacct == '') {
                    // alert("用户登录账户不能为空，请输入");
                    layer.msg("用户登录账户不能为空，请输入", {time: 2000, icon: 5, shift: 6}, function () {
                        alert("回调方法");
                    });
                    return;
                }
                var userpwd = $("#userpwd").val();
                if (userpwd == '') {
                    // alert("密码不能为空，或输入错误");
                    layer.msg("密码不能为空，或输入错误");
                    return;
                }

                // 提交表单
                // alert("提交表单");
                // $("#loginForm").submit();

                // 使用 AJAX 提交数据
                var loadingIndex = null;
                $.ajax({
                    type: "POST",
                    url: "doAjaxLogin",
                    data: {
                        "loginacct": loginacct,
                        "userpwd": userpwd
                    },
                    beforeSend: function () {
                        loadingIndex = layer.msg("处理中", {icon: 16});
                    },
                    success: function (response) {
                        layer.close(loadingIndex);
                        if (response.success) {
                            window.location.href = "main";
                        } else {
                            layer.msg("账户或密码不正确，请重新输入", {time: 2000, icon: 5, shift: 6}, function () {
                                // alert("回调方法");
                                window.location.href = "login";
                            });
                        }
                    }
                });
            }
        </script>
    </body>
</html>
