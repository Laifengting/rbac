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
                    <div><a class="navbar-brand" style="font-size:32px;" href="${APP_PATH}/main">众筹平台 - 角色维护</a></div>
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
                        url: "${APP_PATH}/permission/loadData",
                        autoParam: ["id", "name=n", "level=lv"]
                    },
                    view: {
                        addHoverDom: addHoverDom,
                        removeHoverDom: removeHoverDom,
                        selectedMulti: false,
                        addDiyDom: function (treeId, treeNode) {
                            var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                            if (treeNode.icon) {
                                icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background", "");
                            }
                        },

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

            function addHoverDom(treeId, treeNode) {
                //   <a><span></span></a>
                var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
                aObj.attr("href", "javascript:;");
                if (treeNode.editNameFlag || $("#btnGroup" + treeNode.tId).length > 0) return;
                var s = '<span id="btnGroup' + treeNode.tId + '">';
                if (treeNode.level == 0) {
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addNode(' + treeNode.id + ')" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if (treeNode.level == 1) {
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="updateNode(' + treeNode.id + ')" href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    if (treeNode.children.length == 0) {
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteNode(' + treeNode.id + ')" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                    }
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addNode(' + treeNode.id + ')" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if (treeNode.level == 2) {
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="updateNode(' + treeNode.id + ')" href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="deleteNode(' + treeNode.id + ')" href="#">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                }
                s += '</span>';
                aObj.after(s);
            }

            function removeHoverDom(treeId, treeNode) {
                $("#btnGroup" + treeNode.tId).remove();
            }

            function addNode(treeId) {
                window.location.href = "${APP_PATH}/permission/add?id=" + treeId;
            }

            function updateNode(treeId) {
                window.location.href = "${APP_PATH}/permission/update?id=" + treeId;
            }

            function deleteNode(treeId) {
                var loadingIndex;
                layer.confirm("删除选中的许可信息,是否继续", {icon: 3, title: '提示'},
                    function (cindex) {
                        // 确认回调，删除选择的用户信息
                        $.ajax({
                            type: "POST",
                            url: "${APP_PATH}/permission/delete",
                            // 将表单内容序列化为字符串。
                            data: {"id": treeId},
                            beforeSend: function () {
                                loadingIndex = layer.msg("处理中", {icon: 16});
                            },
                            success: function (response) {
                                layer.close(loadingIndex);
                                if (response.success) {
                                    // 读取当前树对象
                                    var treeObj = $.fn.zTree.getZTreeObj("permissionTree")
                                    // 刷新数据
                                    treeObj.reAsyncChildNodes(null, "refresh");
                                } else {
                                    layer.msg("许可信息删除失败", {time: 2000, icon: 5, shift: 6}, function () {
                                    });
                                }
                            }
                        });
                        layer.close(cindex);
                    }, function (cindex) {
                        // 取消回调
                        layer.close(cindex);
                    });
            }
        </script>
    </body>
</html>
