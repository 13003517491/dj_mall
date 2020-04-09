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
            $.get(
                "<%=request.getContextPath()%>/auth/base/",
                {},
                function(data){
                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        var b = data.data[i];
                        html += "<tr>"
                        html += "<td>"+b.code+"</td>"
                        html += "<td>"+b.name+"</td>"
                        html += "<td>"+b.pcode+"</td>"
                        html += "<td><shiro:hasPermission name='BASEDATA_UPDATE_BTN'><input type='button' value='修改' onclick='updateById("+b.baseDataId+")'></shiro:hasPermission></td>"
                        html += "</tr>"
                    }
                    $("#tbd").html(html);
                }
            )
        }

        function updateById(id){
            layer.confirm('确定修改吗?', {icon: 3, title:'提示'}, function(){
                //do something
                layer.open({
                    type: 2,
                    title: '基础数据修改页面',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['380px', '90%'],
                    content: '<%=request.getContextPath()%>/auth/base/toUpdate?id='+id
                });
            });
        }

        function add(){

            layer.confirm('确定增加吗?', {icon: 3, title:'提示'}, function(index) {
                var index = layer.load(1,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/auth/base/",
                    $("#fm").serialize(),
                    function (data) {
                        if (data.code != -1) {
                            layer.msg(data.msg, {icon: 6}, function () {
                                window.location.href = "<%=request.getContextPath()%>/auth/base/toList";
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
    </script>
</head>
<body>
    <form id="fm">
        分类上级
        <select name="pCode">
            <option value="SYSTEM">SYSTEM</option>
            <c:forEach items="${baseDataList}"  var="r" >
                <option value="${r.code}">${r.name}</option>
            </c:forEach>
        </select><br />
        分类名称<input type="text" name="name"><br />
        分类code<input type="text" name="code"><br />
        <shiro:hasPermission name="BASEDATA_ADD_BTN">
            <input type="button" value="新增" onclick="add()">
        </shiro:hasPermission>
        <input type="hidden" name="_method" value="post">
    </form>
<br />
<table border="1px">
    <tr>
        <th>CODE</th>
        <th>字典名</th>
        <th>上级CODE</th>
        <th>操作</th>
    </tr>
    <tbody id="tbd">

    </tbody>
</table>
</body>
</html>