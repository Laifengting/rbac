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
        <title>Role Index</title>
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
                            <form class="form-inline" role="form" style="float:left;">
                                <div class="form-group has-feedback">
                                    <div class="input-group">
                                        <div class="input-group-addon">查询条件</div>
                                        <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                                    </div>
                                </div>
                                <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                                </button>
                            </form>
                            <button type="button" class="btn btn-danger" onclick="batchDeleteRoles()" style="float:right;margin-left:10px;">
                                <i
                                        class=" glyphicon glyphicon-remove"></i> 删除
                            </button>
                            <button type="button" class="btn btn-primary" style="float:right;"
                                    onclick="window.location.href='${APP_PATH}/role/add'"><i
                                    class="glyphicon glyphicon-plus"></i> 新增
                            </button>
                            <br>
                            <hr style="clear:both;">
                            <div class="table-responsive">
                                <form id="roleForm">
                                    <table class="table  table-bordered">
                                        <thead>
                                            <tr>
                                                <th width="30">#</th>
                                                <th width="30"><input type="checkbox" id="selectAllBox"></th>
                                                <th>名称</th>
                                                <th width="100">操作</th>
                                            </tr>
                                        </thead>

                                        <tbody id="roleData">

                                        </tbody>

                                        <tfoot>
                                            <tr>
                                                <td colspan="6" align="center">
                                                    <ul class="pagination">
                                                    </ul>
                                                </td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="${APP_PATH}/static/jquery/jquery-2.1.1.min.js"></script>
        <script src="${APP_PATH}/static/bootstrap/js/bootstrap.min.js"></script>
        <script src="${APP_PATH}/static/script/docs.min.js"></script>
        <script src="${APP_PATH}/static/layer/layer.js"></script>
        <script type="text/javascript">
            var likeFlag = false;
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
                pageSize = 10;
                pageQuery(1);

                // 模糊查询
                $("#queryBtn").click(function () {
                    var queryText = $("#queryText").val();
                    if (queryText === "") {
                        likeFlag = false;
                    } else {
                        likeFlag = true;
                    }
                    pageQuery(1);
                });

                // 全选/全不选点击事件
                $("#selectAllBox").click(function () {
                    var flag = this.checked;
                    // alert(flag);
                    // 注意要加个空格
                    $("#roleData :checkbox").each(function () {
                        this.checked = flag;
                    })
                });
            });
            $("tbody .btn-success").click(function () {
                window.location.href = "assignRole.html";
            });
            $("tbody .btn-primary").click(function () {
                window.location.href = "edit.html";
            });

            // 跳转到第几页
            function jumpPage() {
                var pageNo = $(".g-input").val();
                if (pageNo >= totalPage) {
                    pageQuery(totalPage);
                } else if (pageNo < 1) {
                    pageQuery(1);
                } else {
                    pageQuery(pageNo);
                }
            }

            function research() {
                pageSize = $("#pageSize").val();
                // object = document.getElementById("pageSize");
                // for (let i = 0; i < object.length; i++) {
                //     if (object[i] === pageSize) {
                //         object[i].selected = true;
                //     }
                // }
                pageQuery(1);
            }

            // 分页查询
            function pageQuery(pageNo) {
                // 查询加载动画
                var loadingIndex = null;
                // var
                var jsonData = {
                    "pageNo": pageNo,
                    "pageSize": pageSize
                }
                if (likeFlag === true) {
                    jsonData.queryText = $("#queryText").val();
                }

                $.ajax({
                    type: "POST",
                    url: "${APP_PATH}/role/pageQuery",
                    data: jsonData,
                    beforeSend: function () {
                        loadingIndex = layer.msg("处理中", {icon: 16});
                    },
                    success: function (response) {
                        layer.close(loadingIndex);
                        if (response.success) {
                            // 局部刷新页面数据
                            var tableContent = "";
                            var pageContent = "";

                            var page = response.data.page;
                            console.log(page);
                            var userList = page.data;

                            totalPage = page.totalPages;

                            $.each(userList, function (index, role) {
                                tableContent += '<tr>';
                                tableContent += '    <td>' + (index + 1) + '</td>';
                                tableContent += '    <td><input type="checkbox" name="roleId" value="' + role.id + '"></td>';
                                tableContent += '  <td>' + role.name + '</td>';
                                tableContent += '    <td>';
                                tableContent += '        <button type="button" class="btn btn-success btn-xs" onclick="goAssignPage(' + role.id + ')"><i class=" glyphicon glyphicon-check"></i></button>';
                                tableContent += '        <button type="button" class="btn btn-primary btn-xs" onclick="goUpdatePage(' + role.id + ')"><i class=" glyphicon glyphicon-pencil"></i></button>';
                                tableContent += '        <button type="button" class="btn btn-danger btn-xs" onclick="deleteRole(' + role.id + ',\'' + role.name + '\')"><i class=" glyphicon glyphicon-remove"></i></button>';
                                tableContent += '    </td>';
                                tableContent += '</tr>';
                            });

                            // 如果当前页大于1 则显示上一页
                            if (pageNo > 1) {
                                pageContent += '<li><a href="#" onclick="pageQuery(' + (pageNo - 1) + ')">上一页</a></li>';
                            }
                            // 如果总页数大于 5 则会省略中间页显示为 ...
                            if (page.totalPages > 5) {
                                if (pageNo == 1) {
                                    pageContent += '<li class="active"><a href="#">' + 1 + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 2 + ')">' + 2 + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 3 + ')">' + 3 + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 4 + ')">' + 4 + '</a></li>';
                                    pageContent += '<li><a href="#">...</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + page.totalPages + ')">' + page.totalPages + '</a></li>';
                                } else if (pageNo == 2) {
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 1 + ')">' + 1 + '</a></li>';
                                    pageContent += '<li class="active"><a href="#">' + 2 + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 3 + ')">' + 3 + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 4 + ')">' + 4 + '</a></li>';
                                    pageContent += '<li><a href="#">...</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + page.totalPages + ')">' + page.totalPages + '</a></li>';
                                } else if (pageNo == 3) {
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 1 + ')">' + 1 + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 2 + ')">' + 2 + '</a></li>';
                                    pageContent += '<li class="active"><a href="#">' + 3 + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 4 + ')">' + 4 + '</a></li>';
                                    pageContent += '<li><a href="#">...</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + page.totalPages + ')">' + page.totalPages + '</a></li>';
                                } else if (pageNo == page.totalPages) {
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 1 + ')">' + 1 + '</a></li>';
                                    pageContent += '<li><a href="#">...</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + (page.totalPages - 3) + ')">' + (page.totalPages - 3) + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + (page.totalPages - 2) + ')">' + (page.totalPages - 2) + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + (page.totalPages - 1) + ')">' + (page.totalPages - 1) + '</a></li>';
                                    pageContent += '<li class="active"><a href="#">' + page.totalPages + '</a></li>';
                                } else if (pageNo == page.totalPages - 1) {
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 1 + ')">' + 1 + '</a></li>';
                                    pageContent += '<li><a href="#">...</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + (page.totalPages - 3) + ')">' + (page.totalPages - 3) + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + (page.totalPages - 2) + ')">' + (page.totalPages - 2) + '</a></li>';
                                    pageContent += '<li class="active"><a href="#">' + (page.totalPages - 1) + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + page.totalPages + ')">' + page.totalPages + '</a></li>';
                                } else if (pageNo == page.totalPages - 2) {
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 1 + ')">' + 1 + '</a></li>';
                                    pageContent += '<li><a href="#">...</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + (page.totalPages - 3) + ')">' + (page.totalPages - 3) + '</a></li>';
                                    pageContent += '<li class="active"><a href="#">' + (page.totalPages - 2) + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + (page.totalPages - 1) + ')">' + (page.totalPages - 1) + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + page.totalPages + ')">' + page.totalPages + '</a></li>';
                                } else {
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + 1 + ')">' + 1 + '</a></li>';
                                    pageContent += '<li><a href="#">...</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + (pageNo - 1) + ')">' + (pageNo - 1) + '</a></li>';
                                    pageContent += '<li class="active"><a href="#">' + pageNo + '</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + (pageNo + 1) + ')">' + (pageNo + 1) + '</a></li>';
                                    pageContent += '<li><a href="#">...</a></li>';
                                    pageContent += '<li><a href="#" onclick="pageQuery(' + page.totalPages + ')">' + page.totalPages + '</a></li>';
                                }
                            } else {
                                // 如果总页数小于等于 5 显示全部页面
                                for (var i = 1; i <= page.totalPages; i++) {
                                    if (i == pageNo) {
                                        pageContent += '<li class="active"><a href="#">' + i + '</a></li>';
                                    } else {
                                        pageContent += '<li><a href="#" onclick="pageQuery(' + i + ')">' + i + '</a></li>';
                                    }
                                }
                            }

                            if (pageNo < page.totalPages) {
                                pageContent += '<li><a href=" #" onclick="pageQuery(' + (pageNo + 1) + ')">下一页</a></li>';
                            }

                            pageContent += '<li><label>';
                            pageContent += '    <select id="pageSize" onchange="research()" aria-controls="DataTables_Table_0" size="1" name="DataTables_Table_0_length" class="g-pages">';
                            pageContent += '        <option value="10">10条/页</option>';
                            pageContent += '        <option value="20">20条/页</option>';
                            pageContent += '        <option value="50">50条/页</option>';
                            pageContent += '        <option value="100">100条/页</option>';
                            pageContent += '        <option value="200">200条/页</option>';
                            pageContent += '    </select>';
                            pageContent += '</label>';
                            pageContent += '</li>';

                            pageContent += '<li>跳至<input style="width: 40px" type="text" class="g-input">页<input type="button" onclick="jumpPage()" value="GO"></input></li>';


                            $("#roleData").html(tableContent);
                            $(".pagination").html(pageContent);
                        } else {
                            layer.msg("用户信息分页查询失败", {time: 2000, icon: 5, shift: 6}, function () {
                            });
                        }
                    }
                });
            }

            // 跳转修改页面
            function goUpdatePage(roleId) {
                window.location.href = "${APP_PATH}/role/edit?id=" + roleId;
            }

            // 删除用户
            function deleteRole(id, name) {
                var loadingIndex;
                layer.confirm("删除角色信息【" + name + "】,是否继续", {icon: 3, title: '提示'},
                    function (cindex) {
                        // 确认回调，删除用户信息
                        $.ajax({
                            type: "POST",
                            url: "${APP_PATH}/role/delete",
                            data: {'id': id},
                            beforeSend: function () {
                                loadingIndex = layer.msg("处理中", {icon: 16});
                            },
                            success: function (response) {
                                layer.close(loadingIndex);
                                if (response.success) {
                                    pageQuery(1);
                                } else {
                                    layer.msg("角色信息删除失败", {time: 2000, icon: 5, shift: 6}, function () {
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

            function batchDeleteRoles() {
                var boxes = $("#roleData :checkbox");
                if (boxes.length === 0) {
                    layer.msg("至少要选中一个需要删除的角色", {time: 2000, icon: 5, shift: 6}, function () {
                    });
                } else {
                    var loadingIndex;
                    layer.confirm("删除选中的所有角色信息,是否继续", {icon: 3, title: '提示'},
                        function (cindex) {
                            // 确认回调，删除选择的角色信息
                            $.ajax({
                                type: "POST",
                                url: "${APP_PATH}/role/batchDelete",
                                // 将表单内容序列化为字符串。
                                data: $("#roleForm").serialize(),
                                beforeSend: function () {
                                    loadingIndex = layer.msg("处理中", {icon: 16});
                                },
                                success: function (response) {
                                    layer.close(loadingIndex);
                                    if (response.success) {
                                        pageQuery(1);
                                    } else {
                                        layer.msg("角色信息删除失败", {time: 2000, icon: 5, shift: 6}, function () {
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
            }

            function goAssignPage(roleId) {
                window.location.href = "${APP_PATH}/role/assign?id=" + roleId;
            }
        </script>
    </body>
</html>
