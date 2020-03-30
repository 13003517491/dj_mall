<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.exedit.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
    <script type="text/javascript">
        var treeObj;
        var setting = {
            // check: {
            //     enable: true
            // },
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "pid",
                    idKey: "resourceId"
                },
                key: {
                    name: "resourceName",
                    url: "resourceUrl"

                }
            },

        };

        $(function () {
            $.post(
                "<%=request.getContextPath()%>/auth/resource/show",
                function (data) {
                    treeObj = $.fn.zTree.init($("#tree"), setting, data.data);
                }
            )
        })

        <%--//增一级节点--%>
        <%--function saveOne() {--%>
        <%--    layer.open({--%>
        <%--        type: 2,--%>
        <%--        titel: "新增",--%>
        <%--        area: ["400px", "500px"],--%>
        <%--        content: "<%=request.getContextPath()%>/resource/toAdd/0",--%>
        <%--        end: function () {--%>
        <%--            location.reload();--%>
        <%--        }--%>
        <%--    });--%>
        <%--}--%>

        //增二级节点，现在已改，一级二级都可以增
        function saveTwo() {
            var treeOb = $.fn.zTree.getZTreeObj("tree");
            var selectNodes = treeOb.getSelectedNodes();
            // if (selectNodes.length <= 0) {
            //     alert("请选择父节点");
            //     return false;
            // }
            // var pId = selectNodes[0].id;
            if (selectNodes.length > 0) {
                var pId = selectNodes[0].id;
            } else {
                var pId = 0;
            }
            layer.open({
                type: 2,
                titel: "新增",
                area: ["400px", "500px"],
                content: "<%=request.getContextPath()%>/auth/resource/toAdd/" + pId,
                end: function () {
                    location.reload();
                }
            });
        }

        function updateRes(value) {
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var selectNode = treeObj.getSelectedNodes();
            if (selectNode.length <= 0) {
                layer.msg("至少选择一个节点操作");
                return false;
            }
            if (selectNode.length > 1) {
                layer.msg("只能选择一个节点操作");
                return false;
            }
            var value = selectNode[0].resourceId;
            layer.open({
                type: 2,
                titel: "修改",
                area: ["400px", "500px"],
                content: "<%=request.getContextPath()%>/auth/resource/toUpdate?resourceId=" + value,
            });
        }

        function deleRes() {
            // 获取选中的节点
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var selectNode = treeObj.getSelectedNodes()[0];
            if (selectNode == null) {
                layer.msg("至少选择一个节点操作");
                return false;
            }
            if (selectNode.length > 1) {
                layer.msg("只能选择一个节点操作");
                return false;
            }
            // 如果当前选中的节点是 父节点 要获取全部的后代节点信息
            var ids = "";
            if (selectNode.isParent) {
                ids = getChildNode(selectNode);
            }
            ids += selectNode.id;
            var id = selectNode[0].resourceId;

            layer.msg('确定删除?', {
                time: 0 //不自动关闭
                , btn: ['确定', '取消']
                , yes: function () {
                    $.post(
                        "<%=request.getContextPath() %>/auth/resource/delById",
                        {"id": id},
                        function (data) {
                            if (data.code == 200) {
                                layer.msg(data.msg, {
                                    icon: 6,
                                }, function () {
                                    window.location.href = "<%=request.getContextPath()%>/auth/resource/toList";
                                });
                            } else {
                                layer.msg(data.msg, {icon: 5});
                            }
                        })
                }
            })
        }

        //递归自我调用
        function getChildNode(parentNode) {
            var ids = "";
            // 获取子节点
            var childs = parentNode.children;
            for (var i = 0; i < childs.length; i++) {
                ids += childs[i].id + ",";
                if (childs[i].isParent) {
                    ids += getChildNode(childs[i]);
                }
            }
            return ids;
        }


    </script>
</head>
<body>
<%--<input type="button" value="新增一级资源" onclick="saveOne()">--%>
    <shiro:hasPermission name="resource:add">
        <input type="button" value="新增" onclick="saveTwo()">
    </shiro:hasPermission>
    <shiro:hasPermission name="resource:update">
        <input type="button" value="修改" onclick="updateRes()">
    </shiro:hasPermission>
    <shiro:hasPermission name="resource:del">
        <input type="button" value="删除" onclick="deleRes()">
    </shiro:hasPermission>

    <div id="tree" class="ztree">

</div>
</body>
</html>