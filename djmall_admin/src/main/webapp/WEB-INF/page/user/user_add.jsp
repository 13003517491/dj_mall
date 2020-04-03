<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5/md5-min.js"></script>
    <script type="text/javascript">

        jQuery.validator.addMethod("phone",
            function(value, element) {
                var tel = /^1[3456789]\d{9}$/;
                return tel.test(value)
            }, "请正确填写您的手机号");

        // 判断用户名昵称不同
        jQuery.validator.addMethod("notEqu",
            function(value, element) {
            return value != $("#userName").val();
        }, "用户名昵称需不同!");

        $(function(){
            $("#fm").validate({
                //效验规则
                rules: {
                    username:{
                        required:true,
                        minlength:2,
                        remote: {
                            type: 'POST',
                            url: "<%=request.getContextPath()%>/auth/user/distinct",
                            data:{
                                userName:function() {
                                    return $("#userName").val();
                                },
                                dataType:"json",
                                dataFilter:function(data,type){
                                    if (data == 'true'){
                                        return true;
                                    }else {
                                        return false	;
                                    }
                                }
                            }
                        }
                    },
                    nickname:{
                        required:true,
                        minlength:1,
                        notEqu:true
                    },
                    password:{
                        required:true,
                        minlength:1,
                        digits:true
                    },
                    password2:{
                        required:true,
                        minlength:1,
                        digits:true,
                        equalTo:"#pwd"
                    },
                    phone:{
                        required:true,
                        phone:true,
                        digits:true,
                        remote: {
                            type: 'POST',
                            url: "<%=request.getContextPath()%>/auth/user/distinct",
                            data:{
                                phone:function() {
                                    return $("#phone").val();
                                },
                                dataType:"json",
                                dataFilter:function(data,type){
                                    if (data == 'true'){
                                        return true;
                                    }else {
                                        return false	;
                                    }
                                }
                            }
                        }
                    },
                    email:{
                        required:true,
                        email:5,
                        remote: {
                            type: 'POST',
                            url: "<%=request.getContextPath()%>/auth/user/distinct",
                            data:{
                                email:function() {
                                    return $("#email").val();
                                },
                                dataType:"json",
                                dataFilter:function(data,type){
                                    if (data == 'true'){
                                        return true;
                                    }else {
                                        return false	;
                                    }
                                }
                            }
                        }
                    },
                    levelId:{
                        required:true
                    }
                },
                messages:{
                    userName:{
                        required:"名字必填",
                        minlength:"最少两个字儿"
                    },
                    password:{
                        required:"请填写密码",
                        minlength:"最少5个字儿",
                        digits:"只能是数字"
                    },
                    password2:{
                        required:"请确认密码",
                        minlength:"最少5个字儿",
                        digits:"只能是数字",
                        equalTo:"两次输入密码不同"
                    },
                    phone:{
                        required:"请填写手机号",
                        rangelength:"11位呀",
                        digits:"只能是数字"
                    },
                    email:{
                        required:"请填写你的邮箱",
                        email:"邮箱格式不对"
                    },
                    levelId:{
                        required:"选一个哟"
                    }
                },
                errorPlacement: function (error, element) {
                    if (element.is("[name='levelId']")){ //如果需要验证的元素名为userHobby
                        error.appendTo($("#levelId"));   //错误增加在id为'checkbox-lang'中
                    } else {
                        error.insertAfter(element);//其他的就显示在添加框后
                    }
                }
            })
        })

        $.validator.setDefaults({
            submitHandler: function() {
                var index = layer.load(1,{shade:0.5});
                /* layer.confirm('确定添加吗?', {icon: 3, title:'提示'}, function(index){ */
                var pwd = md5($("#pwd").val());
                var salt = $("#salt").val();
                var md5pwd = md5(pwd + salt);
                $("#pwd").val(md5pwd);
                $.post("<%=request.getContextPath()%>/auth/user/add",
                    $("#fm").serialize(),
                    function(data){
                        if(data.code == -1){
                            layer.close(index);
                            layer.msg(data.msg, {icon: 5});
                            return
                        }
                        layer.msg(data.msg, {
                            icon: 6,
                            time: 2000
                        }, function(){
                            parent.window.location.href = "<%=request.getContextPath()%>/auth/user/toLogin";
                        });
                    }
                )
                /*  });
                 layer.close(index); */
            }
        });

    </script>
    <!-- 错误时提示颜色 -->
    <style>
        .error{
            color:red;
        }
    </style>

</head>
<body>
<form id="fm">
    用户名:<input type="text" name="username" id="userName"><br />
    昵称:<input type="text" name="nickname" id="userName2"><br />
    密码:<input type="password" name="password" id="pwd"><br />
    确认密码:<input type="password" name="password2"><br />
    手机:<input type="text" name="phone" id="phone"><br />
    邮箱:<input type="text" name="email" id="email"><br />
    级别:
    <c:forEach items="${roleList}"  var="r" >
        <input type="radio" name="roleId" value="${r.roleId}"<c:if test="${r.roleId == 1}">checked</c:if>>${r.roleName}
    </c:forEach><br/>
<%--    <input type="radio" value="1" name="roleId" checked>商户--%>
<%--    <input type="radio" value="2" name="roleId">管理员<br>--%>
    性别:<input type="radio" name="sex" value="7" checked>男
         <input type="radio" name="sex" value="8">女<br />
    <input type="hidden" name="status" value="11">
    <input type="hidden" name="isDel" value="1">
    <input type="hidden" name="salt" value="${salt}" id="salt">
    <a href="<%=request.getContextPath()%>/auth/user/toLogin">已有账号?前去登录</a>
    <input type="submit" value="注册">
</form>
</body>
</html>