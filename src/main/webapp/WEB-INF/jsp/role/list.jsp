<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="zhangfn" uri="http://github.com/zhangkaitao/tags/zhang-functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
</head>
<body>

<c:if test="${not empty msg}">
    <div class="message">${msg}</div>
</c:if>

<shiro:hasPermission name="role:create">
    <a href="${pageContext.request.contextPath}/role/create">角色新增</a><br/>
</shiro:hasPermission>
<table class="table">
    <thead>
        <tr>
            <th>角色名</th>
            <th>角色描述</th>
            <th>操作</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${roleList}" var="role">
            <tr>
                <td>${role.name}</td>
                
                <td>${role.description}</td>
                <td>
                	<c:if test="${role.name ne 'Admin'}">
	                    <shiro:hasPermission name="role:update">
	                        <a href="${pageContext.request.contextPath}/role/${role.id}/update">修改</a>
	                    </shiro:hasPermission>
	                    <shiro:hasPermission name="role:delete">
	                        <a href="${pageContext.request.contextPath}/role/${role.id}/delete">删除</a>
	                    </shiro:hasPermission>
	                 </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>