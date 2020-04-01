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
                        html += "<td><input type='button' value='修改' onclick='updateById("+b.id+")'></td>"
                        html += "</tr>"
                    }
                    $("#tbd").html(html);
                }
            )
        }

        function add(){
            layer.confirm('确定新增吗?', {icon: 3, title:'提示'}, function(index){
                //do something
                layer.open({
                    type: 2,
                    title: '基础数据新增页面',
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
    分类上级
        <select name="pCode">
            <option value="SYSTEM">SYSTEM</option>
        </select><br />
    分类名称<input type="text" name="name"><br />
    分类code<input type="text" name="code"><br />
    <input type="button" value="新增" onclick="add()">

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