<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.exedit.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
	<script type="text/javascript">
		<%--var setting = {--%>
		<%--	async: {--%>
		<%--		enable: true,//接收json格式数据--%>
		<%--		url:"<%=request.getContextPath()%>/resource/list",//路径--%>
		<%--		autoParam:["id"],//返回的参数--%>
		<%--	},--%>
		<%--	data: {--%>
		<%--		simpleData: {--%>
		<%--			enable: true,--%>
		<%--			pIdKey: "pId"--%>

		<%--		},--%>
		<%--		key: {--%>
		<%--			name: "resourceName",--%>
		<%--			url: "xUrl",--%>
		<%--			isParent: "parent"--%>
		<%--		}--%>
		<%--	},--%>
		<%--	callback: {--%>
		<%--		onClick: function (event, treeId, treeNode) {--%>
		<%--			if (!treeNode.isParent) {--%>
		<%--				parent.right.location.href = "<%=request.getContextPath()%>" + treeNode.url;--%>
		<%--			}--%>
		<%--		}--%>
		<%--	}--%>
		<%--};--%>

		<%--$(document).ready(function(){--%>
		<%--	$.fn.zTree.init($("#treeDemo"), setting);--%>
		<%--});--%>

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
					url: "noexist",

				}
			},
			callback: {
				onClick: function (event, treeId, treeNode) {
					if (!treeNode.isParent) {
						parent.right.location.href = "<%=request.getContextPath()%>" + treeNode.url;
					}
				}
			}

		};

		$(function () {
			$.get(
					"<%=request.getContextPath()%>/auth/resource/list",
					function (data) {
						treeObj = $.fn.zTree.init($("#treeDemo"), setting, data.data);
					}
			)
		})
	</script>
</head>
<body>
<div id="treeDemo" class="ztree">

</div>
</body>
</html>