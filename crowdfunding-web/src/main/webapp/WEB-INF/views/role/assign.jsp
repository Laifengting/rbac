<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
        <link rel="stylesheet" href="${APP_PATH}/static/ztree/zTreeStyle.css">
        <style>
            .tree li {
                list-style-type: none;
                cursor: pointer;
            }

            table tbody tr:nth-child(odd) {
                background: #F4F4F4;
            }

            table tbody td:nth-child(even) {
                color: #C00;
            }
        </style>
        <title>Permission Index</title>
    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <div><a class="navbar-brand" style="font-size:32px;" href="${APP_PATH}/main">众筹平台 - 许可维护</a></div>
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
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                        </div>
                        <div class="panel-body">
                            <button class="btn btn-success" onclick="doAssign()">分配许可</button>
                            <ul id="permissionTree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="${APP_PATH}/static/jquery/jquery-2.1.1.min.js"></script>
        <script src="${APP_PATH}/static/bootstrap/js/bootstrap.min.js"></script>
        <script src="${APP_PATH}/static/script/docs.min.js"></script>
        <script src="${APP_PATH}/static/layer/layer.js"></script>
        <script src="${APP_PATH}/static/ztree/jquery.ztree.all-3.5.min.js"></script>
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

                // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
                var setting = {
                    async: {
                        enable: true,
                        url: "${APP_PATH}/permission/loadAssignData?roleId=" + ${param.id},
                        autoParam: ["id", "name=n", "level=lv"]
                    },
                    view: {
                        selectedMulti: false,
                        addDiyDom: function (treeId, treeNode) {
                            var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                            if (treeNode.icon) {
                                icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background", "");
                            }
                        },
                    },
                    check: {
                        enable: true
                    }
                };
                $.fn.zTree.init($("#permissionTree"), setting);
            });
            $("tbody .btn-success").click(function () {
                window.location.href = "assignRole.html";
            });
            $("tbody .btn-primary").click(function () {
                window.location.href = "edit.html";
            });

            function doAssign() {
                // 读取当前树对象
                var treeObj = $.fn.zTree.getZTreeObj("permissionTree")
                var nodes = treeObj.getCheckedNodes(true);
                if (nodes.length === 0) {
                    layer.msg("请选择要给当前角色分配的许可", {time: 2000, icon: 0, shift: 6}, function () {
                    });
                } else {
                    var d = "roleId=${param.id}";
                    $.each(nodes, function (i, node) {
                        d += "&permissionIds=" + node.id;
                    })

                    $.ajax({
                        type: "POST",
                        url: "${APP_PATH}/role/doAssign",
                        data: d,
                        beforeSend: function () {
                            loadingIndex = layer.msg("处理中", {icon: 16});
                        },
                        success: function (response) {
                            layer.close(loadingIndex);
                            if (response.success) {
                                layer.msg("许可信息分配成功", {time: 2000, icon: 6}, function () {
                                });
                            } else {
                                layer.msg("许可信息分配失败", {time: 2000, icon: 5, shift: 6}, function () {
                                });
                            }
                        }
                    });
                }
            }
        </script>
    </body>
</html>
