<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <link rel="stylesheet" href="${APP_PATH}/static/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${APP_PATH}/static/css/font-awesome.min.css">
        <link rel="stylesheet" href="${APP_PATH}/static/css/main.css">
        <link rel="stylesheet" href="${APP_PATH}/static/css/doc.min.css">
        <style>
            .tree li {
                list-style-type: none;
                cursor: pointer;
            }
        </style>
    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <div><a class="navbar-brand" style="font-size:32px;" href="${APP_PATH}/main">众筹平台 - 用户维护</a></div>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li style="padding-top:8px;">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
                                    <i class="glyphicon glyphicon-user"></i> ${user.username} <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
                                    <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                                    <li class="divider"></li>
                                    <li><a href="${APP_PATH}/logout"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                                </ul>
                            </div>
                        </li>
                        <li style="margin-left:10px;padding-top:8px;">
                            <button type="button" class="btn btn-default btn-danger">
                                <span class="glyphicon glyphicon-question-sign"></span> 帮助
                            </button>
                        </li>
                    </ul>
                    <form class="navbar-form navbar-right">
                        <input type="text" class="form-control" placeholder="Search...">
                    </form>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <div class="tree">
                        <%@ include file="/WEB-INF/views/common/menu.jsp" %>
                    </div>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                    <ol class="breadcrumb">
                        <li><a href="#">首页</a></li>
                        <li><a href="#">数据列表</a></li>
                        <li class="active">分配角色</li>
                    </ol>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form role="form" id="roleForm" class="form-inline">
                                <input type="hidden" name="userId" value="${user.id}">
                                <div class="form-group">
                                    <label for="exampleInputPassword1">未分配角色列表</label><br>
                                    <select id="leftList" name="unAssignedRoleIds" class="form-control" multiple size="10"
                                            style="width:200px;overflow-y:auto;">
                                        <c:forEach items="${unassignedRoles}" var="role" varStatus="status">
                                            <option value="${role.id}">${role.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <ul>
                                        <li id="left2RightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                        <br>
                                        <li id="right2LeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
                                            style="margin-top:20px;"></li>
                                    </ul>
                                </div>
                                <div class="form-group" style="margin-left:40px;">
                                    <label for="exampleInputPassword1">已分配角色列表</label><br>
                                    <select id="rightList" name="assignedRoleIds" class="form-control" multiple size="10"
                                            style="width:200px;overflow-y:auto;">
                                        <c:forEach items="${assignedRoles}" var="role" varStatus="status">
                                            <option value="${role.id}">${role.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                                class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">帮助</h4>
                    </div>
                    <div class="modal-body">
                        <div class="bs-callout bs-callout-info">
                            <h4>测试标题1</h4>
                            <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                        </div>
                        <div class="bs-callout bs-callout-info">
                            <h4>测试标题2</h4>
                            <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                        </div>
                    </div>
                    <!--
                    <div class="modal-footer">
                      <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                      <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                    -->
                </div>
            </div>
        </div>
        <script src="${APP_PATH}/static/jquery/jquery-2.1.1.min.js"></script>
        <script src="${APP_PATH}/static/bootstrap/js/bootstrap.min.js"></script>
        <script src="${APP_PATH}/static/script/docs.min.js"></script>
        <script src="${APP_PATH}/static/layer/layer.js"></script>
        <script type="text/javascript">
            $(function () {
                $(".list-group-item").click(function () {
                    if ($(this).find("ul")) {
                        $(this).toggleClass("tree-closed");
                        if ($(this).hasClass("tree-closed")) {
                            $("ul", this).hide("fast");
                        } else {
                            $("ul", this).show("fast");
                        }
                    }
                });

                $("#left2RightBtn").click(function () {
                    var opts = $("#leftList :selected");
                    if (opts.length === 0) {
                        layer.msg("请选择需要分配的角色", {time: 2000, icon: 5, shift: 6}, function () {
                        });
                    } else {
                        var loadingIndex;
                        $.ajax({
                            type: "POST",
                            url: "${APP_PATH}/user/doassign",
                            data: $("#roleForm").serialize(),
                            beforeSend: function () {
                                loadingIndex = layer.msg("处理中", {icon: 16});
                            },
                            success: function (response) {
                                layer.close(loadingIndex);
                                if (response.success) {
                                    $("#rightList").append(opts);
                                    layer.msg("分配角色成功", {time: 2000, icon: 6, shift: 6}, function () {
                                    });
                                } else {
                                    layer.msg("分配角色失败", {time: 2000, icon: 5, shift: 6}, function () {
                                    });
                                }
                            }
                        });
                    }
                });

                $("#right2LeftBtn").click(function () {
                    var opts = $("#rightList :selected");
                    if (opts.length === 0) {
                        layer.msg("请选择需要去除的角色", {time: 2000, icon: 5, shift: 6}, function () {
                        });
                    } else {
                        var loadingIndex;
                        $.ajax({
                            type: "POST",
                            url: "${APP_PATH}/user/doUnassign",
                            data: $("#roleForm").serialize(),
                            beforeSend: function () {
                                loadingIndex = layer.msg("处理中", {icon: 16});
                            },
                            success: function (response) {
                                layer.close(loadingIndex);
                                if (response.success) {
                                    $("#leftList").append(opts);
                                    layer.msg("取消分配角色成功", {time: 2000, icon: 6, shift: 6}, function () {
                                    });
                                } else {
                                    layer.msg("取消分配角色失败", {time: 2000, icon: 5, shift: 6}, function () {
                                    });
                                }
                            }
                        });
                    }
                });
            });
        </script>
    </body>
</html>
