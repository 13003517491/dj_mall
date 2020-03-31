<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
    <script type="text/javascript">
        $(function(){
            search();
        })

        function search() {
            $.post(
                "<%=request.getContextPath()%>/auth/role/list",
                $("#fm").serialize(),
                function(data){
                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        var r = data.data[i];
                        html += "<tr>"
                        html += "<td><input type = 'checkbox' name = 'id' value = '"+r.roleId+"'>";
                        html += "<td>"+r.roleId+"</td>"
                        html += "<td>"+r.roleName+"</td>"
                        // html += "</td>"
                        <%--html += "<shiro:hasPermission name='role:update'>"--%>
                        <%--html += "<input type='button' value='修改' onclick='updateById("+r.id+")'>"--%>
                        <%--html += "</shiro:hasPermission>"--%>
                        <%--html += "<shiro:hasPermission name='role:del'><input type='button' value='删除' onclick='delById("+r.id+")'></shiro:hasPermission>"--%>
                        <%--html += "<shiro:hasPermission name='role:addRoleRes'><input type='button' value='关联资源' onclick='addRoleResources("+r.id+")'></shiro:hasPermission>"--%>
                        html += "</tr>"
                    }
                    $("#tbd").html(html);
                }
            )
        }
        function delById(){
            var length = $("input[name='id']:checked").length;

            if(length <= 0){
                alert("至少选择一项");
                return;
            }
            if(length > 1){
                alert("只能选择一个");
                return;
            }

            var id = $("input[name='id']:checked").val();

            layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index) {
                var index = layer.load(1,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/auth/role/delById",
                    {"id": id},
                    function (data) {
                        if (data.code != -1) {
                            layer.msg(data.msg, {icon: 6}, function () {
                                window.location.href = "<%=request.getContextPath()%>/auth/role/toList";
                            });
                            return;
                        }
                        layer.msg(data.msg, {icon: 5})
                        layer.close(index);
                    }
                )
            })
            layer.close(index);
        }

        function addRoleResources(){
            var length = $("input[name='id']:checked").length;

            if(length <= 0){
                alert("至少选择一项");
                return;
            }
            if(length > 1){
                alert("只能选择一个");
                return;
            }

            var roleId = $("input[name='id']:checked").val();
            layer.open({
                type: 2,
                title: '关联资源页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '90%'],
                content: '<%=request.getContextPath()%>/auth/role/toRoleResList?roleId='+roleId
            });
        }

        function updateById(){
            var length = $("input[name='id']:checked").length;

            if(length <= 0){
                alert("至少选择一项");
                return;
            }
            if(length > 1){
                alert("只能选择一个");
                return;
            }
            var id = $("input[name='id']:checked").val();
            layer.confirm('确定修改吗?', {icon: 3, title:'提示'}, function(index){
                //do something
                layer.open({
                    type: 2,
                    title: '修改页面',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['380px', '90%'],
                    content: '<%=request.getContextPath()%>/auth/role/toUpdate?id='+id
                });
            });
        }


        function add(){
            layer.confirm('确定新增吗?', {icon: 3, title:'提示'}, function(index){
                //do something
                layer.open({
                    type: 2,
                    title: '资源新增页面',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['380px', '90%'],
                    content: '<%=request.getContextPath()%>/auth/role/toAdd'
                });
            });
        }
    </script>
</head>
<body>
    <shiro:hasPermission name="ROLE_ADD">
        <input type="button" value="新增" onclick="add()">
    </shiro:hasPermission>
    <shiro:hasPermission name="ROLE_UPDATE">
        <input type='button' value='修改' onclick='updateById()'>
    </shiro:hasPermission>
    <shiro:hasPermission name="ROLE_DEL">
        <input type='button' value='删除' onclick='delById()'>
    </shiro:hasPermission>
    <shiro:hasPermission name="ROLE_ROLERESOURCE">
        <input type='button' value='关联资源' onclick='addRoleResources()'>
    </shiro:hasPermission>

<br />
<table border="1px">
    <tr>
        <th></th>
        <th>用户id</th>
        <th>角色名</th>
<%--        <th>操作</th>--%>
    </tr>
    <tbody id="tbd">

    </tbody>
</table>
</body>
</html>