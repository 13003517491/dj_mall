<%--
  Created by IntelliJ IDEA.
  User: ZhangJQ
  Date: 2020/1/29
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
    <script type="text/javascript">
        $(function(){
            search();
        })

        function search() {
            $.post(
                "<%=request.getContextPath()%>/auth/user/list",
                $("#fm").serialize(),
                function(data){
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var u = data.data.list[i];
                        html += "<tr>"
                        html += "<td><input type = 'checkbox' name = 'id' value = '"+u.userId+"'>";
                        html += "<td>"+u.userId+"</td>"
                        html += "<td>"+u.username+"</td>"
                        html += "<td>"+u.nickname+"</td>"
                        html += "<td>"+u.phone+"</td>"
                        html += "<td>"+u.email+"</td>"
                        html += "<td>"+u.sexShow+"</td>"
                        html += "<td>"+u.roleShow+"</td>"
                        html += "<td>"+u.statusShow+"</td>"
                        html += "<td>"+u.createTime+"</td>"
                        html += u.lastLoginTime != null ? "<td>"+u.lastLoginTime+"</td>" : "<td>未登录过</td>"
                        html += "</tr>"
                    }
                    // var html1 = "角色:"
                    // for(var i = 0; i < data.data.roleList.length; i++) {
                    //     html1 += "<input type='radio' name='roleId' value='"+data.data.roleList[i].roleId+"'>"+data.data.roleList[i].roleName+""
                    // }
                    // $("#tbd1").html(html1);
                    $("#tbd").html(html);
                    var pageNo = $("#pageNo").val();
                    var pageHtml = "<input type='button' value='上一页' onclick='page("+data.data.pages+", "+(parseInt(pageNo) - 1)+")'>";
                    pageHtml += "<input type='button' value='下一页' onclick='page("+data.data.pages+", "+(parseInt(pageNo) + 1)+")')'>";
                    $("#pageInfo").html(pageHtml);
                }
            )
        }


        function page(pages, page) {

            if (page < 1) {
                layer.msg('已经到首页啦!', {icon:0});
                return;
            }
            if (page > pages) {
                layer.msg('已经到尾页啦!!', {icon:0});
                return;
            }
            $("#pageNo").val(page);
            search();

        }
        //去修改
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

                layer.close(index);

                layer.open({
                    type: 2,
                    title: '修改页面',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['380px', '90%'],
                    content: '<%=request.getContextPath()%>/auth/user/toUpdate/'+id
                });
            });
        }

        //激活
        function updateStatusById(){
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
            layer.confirm('确定激活吗?', {icon: 3, title:'提示'}, function(index){
                var index = layer.load(1,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/auth/user/updateStatusById",
                    {"id": id},
                    function(data){
                        if (data.code != -1) {
                            layer.msg(data.msg, {icon: 6}, function(){
                                window.location.href = "<%=request.getContextPath()%>/auth/user/toList";
                            });
                            return;
                        }
                        layer.msg(data.msg, {icon: 5})
                        layer.close(index);
                    }
                )
            });

        }



        //批量刪除
        function delByIds(){
            var length = $("input[name='id']:checked").length;
            if(length <= 0){
                layer.msg('请至少选择一个!', {icon:0});
                return;
            }
            var str = "";
            $("input[name='id']:checked").each(function (index, item) {
                if ($("input[name='id']:checked").length-1==index) {
                    str += $(this).val();
                } else {
                    str += $(this).val() + ",";
                }
            });
            var id = $("input[name='id']:checked").val();
            layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
                var index = layer.load(1,{shade:0.5});
                $.post("<%=request.getContextPath()%>/auth/user/delByIds",
                    {"ids":str,"isDel": -1},
                    function(data){
                        if(data.code == 200){
                            layer.msg(data.msg, {
                                icon: 6,
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            }, function(){
                                window.location.href = "<%=request.getContextPath()%>/auth/user/toList";
                            });
                            return;
                        }
                        layer.msg(data.msg, {icon:5});
                        layer.close(index);
                    });
            });
            layer.close(index);
        }

        //授权
        function updateUserRoleById(){
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
            layer.open({
                type: 2,
                title: '修改页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '90%'],
                content: '<%=request.getContextPath()%>/auth/user/toUpdateRole/'+id
            });
        }

        //删除
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
            var index = layer.load(1,{shade:0.5});
            layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
                //do something
                $.post(
                    "<%=request.getContextPath()%>/auth/user/delById",
                    {"id": id},
                    function(data){
                        if (data.code != -1) {
                            layer.msg(data.msg, {icon: 6}, function(){
                                window.location.href = "<%=request.getContextPath()%>/auth/user/toList";
                            });
                            return;
                        }
                        layer.msg(data.msg, {icon: 5})
                        layer.close(index);
                    }
                )
            });
            layer.close(index);
        }


        //重置密码
        function updatePasswordById(){
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
            var index = layer.load(1,{shade:0.5});
            layer.confirm('确定重置密码吗?', {icon: 3, title:'提示'}, function(index){
                //do something
                layer.msg('邮件发送较慢,请稍等片刻......', {
                    icon: 7,
                    time: 5000, //2秒关闭（如果不配置，默认是3秒）
                    shade: [0.8, '#393D49']
                }, function () {
                $.post(
                    "<%=request.getContextPath()%>/auth/user/updatePasswordById",
                    {"id": id},
                    function(data){
                        if (data.code != -1) {
                            window.location.href = "<%=request.getContextPath()%>/auth/user/toList";
                            <%--layer.msg(data.msg, {icon: 6}, function(){--%>
                            <%--    window.location.href = "<%=request.getContextPath()%>/auth/user/toList";--%>
                            <%--});--%>
                            return;
                        }
                        layer.msg(data.msg, {icon: 5})
                        layer.close(index);
                    }
                )
                });
            })
            layer.close(index);
        }
    </script>
</head>
<body>
<form id="fm">
    <input type="text" name="username" value="" placeholder="请输入用户名/手机号/邮箱"><br />
    角色:
    <c:forEach items="${roleList}" var="r" >
        <input type="radio" name="roleId" value="${r.roleId}">${r.roleName}
    </c:forEach><br>
<%--    <span id="tbd1">--%>

<%--    </span><br>--%>
    性别:
    <input type="radio" name="sex" value="7">男
    <input type="radio" name="sex" value="8">女<br />
    <select name = "status">
        <option value="">请选择</option>
        <option value="11">未激活</option>
        <option value="10">正常</option>
    </select>
    <input type="button" value="查询" onclick="search()"><br />
    <input type="hidden" value="1" id="pageNo" name="pageNo">
</form>
    <shiro:hasPermission name="USER_UPDATE_BTN">
        <input type="button" value="修改" onclick="updateById()">&nbsp;&nbsp;
    </shiro:hasPermission>
    <shiro:hasPermission name="USER_ACTIVE_BTN">
        <input type="button" value="激活" onclick="updateStatusById()">&nbsp;&nbsp;
    </shiro:hasPermission>
    <shiro:hasPermission name="USER_RESETPWD_BTN">
        <input type="button" value="重置密码" onclick="updatePasswordById()">&nbsp;&nbsp;
    </shiro:hasPermission>
    <shiro:hasPermission name="USER_DEL_BTN">
        <input type="button" value="删除" onclick="delByIds()">&nbsp;
    </shiro:hasPermission>
    <shiro:hasPermission name="USER_AUTH_BTN">
        <input type="button" value="授权" onclick="updateUserRoleById()">
    </shiro:hasPermission>

    <table border="1px">
        <tr>
            <th></th>
            <th>用户id</th>
            <th>用户名</th>
            <th>昵称</th>
            <th>手机号</th>
            <th>邮箱</th>
            <th>性别</th>
            <th>级别</th>
            <th>状态</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
        </tr>
        <tbody id="tbd">

        </tbody>
    </table>
<div id="pageInfo">

</div>
</body>
</html>
