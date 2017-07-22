<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="zhangfn" uri="http://github.com/zhangkaitao/tags/zhang-functions" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/JQuery zTree v3.5.15/css/zTreeStyle/zTreeStyle.css">
    <style>
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:200px;overflow-y:scroll;overflow-x:auto;}
    </style>

</head>
<body>

    <form:form method="post" commandName="role">
        <!--<form:hidden path="id"/> -->
        <form:hidden path="available"/>

        <div class="form-group">
            <form:label path="name">角色名：</form:label>
            <form:input path="name"/>
        </div>

        <div class="form-group">
            <form:label path="description">角色描述：</form:label>
            <form:input path="description"/>
        </div>


        <div class="form-group" id="role_resoueces">
            <form:label path="resources">拥有的资源列表：</form:label>
           <!--   <form:hidden path="resources"/> -->
            <input type="text" id="resourceName" name="resourceName" value="${zhangfn:resourceNames(role.resources)}" readonly>
            <a id="menuBtn" href="#">选择</a>
        </div>

        <form:button>${op}</form:button>

    </form:form>


    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
        <ul id="tree" class="ztree" style="margin-top:0; width:160px;"></ul>
    </div>

    <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/JQuery zTree v3.5.15/js/jquery.ztree.all-3.5.min.js"></script>
    <script>
        $(function () {
            var setting = {
                check: {
                    enable: true ,
                    chkboxType: { "Y": "ps", "N": "ps" }
                },	
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: onCheck
                }
            };

            var zNodes =[
                <c:forEach items="${resourceList}" var="r">
                <c:if test="${not r.isRootNode()}">
                { id:"${r.id}", pId:"${r.parent.id}", name:"${r.name}", checked:"${zhangfn:in(role.resources, r)}"},
                </c:if>
                </c:forEach>
            ];

            function onCheck(e, treeId, treeNode) {
            	var para = document.getElementById("role_resoueces");
                var zTree = $.fn.zTree.getZTreeObj("tree"),
                        nodes = zTree.getCheckedNodes(true),
                        id = "",
                        name = "";
                nodes.sort(function compare(a,b){return a.id-b.id;});
                for (var i=0, l=nodes.length; i<l; i++) {
                	var input = document.createElement("input");
                	input.type="hidden";
                	input.name="resources[" + i + "].id";
                	input.value= nodes[i].id ;
                	para.appendChild(input);
                }
                $("#resourceName").val(name);
        //        hideMenu();
            }

            function showMenu() {
                var cityObj = $("#resourceName");
                var cityOffset = $("#resourceName").offset();
                $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

                $("body").bind("mousedown", onBodyDown);
            }
            function hideMenu() {
                $("#menuContent").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
            function onBodyDown(event) {
                if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                    hideMenu();
                }
            }

            $.fn.zTree.init($("#tree"), setting, zNodes);
            $("#menuBtn").click(showMenu);
        });
    </script>


</body>
</html>