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

    <form:form method="post" commandName="user">

        <c:if test="${op ne '新增'}">
	        <form:hidden path="id"/>
	        <form:hidden path="salt"/>
	        <form:hidden path="locked"/>
            <form:hidden path="password"/>
        </c:if>

        <div class="form-group">
            <form:label path="username">用户名：</form:label>
            <form:input path="username"/>
        </div>

        <c:if test="${op eq '新增'}">
            <div class="form-group">
                <form:label path="password">密码：</form:label>
                <form:password path="password"/>
            </div>
        </c:if>

        <div class="form-group">
            <form:label path="organization.id">所属组织：</form:label>
            <form:hidden id="organizationId"  path="organization.id"/>
            <input type="text" id="organizationName" name="organizationName" value="${user.organization.name}" readonly>
            <a id="menuBtn" href="#">选择</a>
        </div>


        <div class="form-group" >
            <form:label path="roles">角色列表：</form:label>
             <ul id="roleTree" class="ztree" style="margin-top:0; width:160px;"></ul>
        </div>
		<div id="roles"></div>
		
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
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onClick: onClick
                }
            };

            var zNodes =[
                <c:forEach items="${organizationList}" var="o">
                    <c:if test="${not o.isRootNode()}">
                        { id:"${o.id}", pId:"${o.parent.id}", name:"${o.name}"},
                    </c:if>
                </c:forEach>
            ];
            
            var roleTreeSetting = {
                    check: {
                        enable: true ,
                        chkboxType: { "Y": "", "N": "" }
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
            var roleTreezNodes =[
                        <c:forEach items="${roleList}" var="r">
                        	{id:"${r.id}", pId:"0", name:"${r.description}", checked:false},
                        </c:forEach>
                     ];
            $.fn.zTree.init($("#tree"), setting, zNodes);
            $.fn.zTree.init($("#roleTree"), roleTreeSetting,roleTreezNodes);
            $("#menuBtn").click(showMenu);
            
            function onCheck(e, treeId, treeNode){
            	var parentElement = document.getElementById("roles");
            	parentElement.innerHTML ="";
           	  	var zTree = $.fn.zTree.getZTreeObj(treeId),
           		    nodes = zTree.getCheckedNodes(true);
           	  	for (var i=0; i< nodes.length; i++){
	           	  	var input = document.createElement("input");
	            	input.type="hidden";
	            	input.name="roles[" + i + "].id";
                	input.value= nodes[i].id ;
                	parentElement.appendChild(input);
           	  	}
            }
            
            function onClick(e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree"),
                        nodes = zTree.getSelectedNodes(),
                        id = "",
                        name = "";
                nodes.sort(function compare(a,b){return a.id-b.id;});
                for (var i=0, l=nodes.length; i<l; i++) {
                    id += nodes[i].id + ",";
                    name += nodes[i].name + ",";
                }
                if (id.length > 0 ) id = id.substring(0, id.length-1);
                if (name.length > 0 ) name = name.substring(0, name.length-1);
                $("#organizationId").val(treeNode.id);
                $("#organizationName").val(name);
                hideMenu();
            }
            
            function showMenu() {
                var cityObj = $("#organizationName");
                var cityOffset = $("#organizationName").offset();
    //            alert(JSON.stringify(cityOffset));
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
            
        });
    </script>
</body>
</html>