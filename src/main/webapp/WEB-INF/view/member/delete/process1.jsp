<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link	type="text/css" rel="stylesheet" href="<c:url value="/static/css/common.css"/>">
<link	type="text/css" rel="stylesheet" href="<c:url value="/static/css/input.css"/>">
<link	type="text/css" rel="stylesheet" href="<c:url value="/static/css/button.css"/>">
<link	type="text/css" rel="stylesheet" href="<c:url value="/static/css/delete.css"/>">
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/> "></script>
<script type="text/javascript">
	$().ready(function(){
		$("#verifyBtn").click(function(){
			
			if ($("#password").val == "") {
				alert("비밀번호를 입력하세요");
				$("#password").focus();
				return false;
			}
			
			$("#verifyForm").attr({
				"method" : "post",
				"action" : "<c:url value="/delete/process2"/>"
			}).submit();
		})
		
	})
</script>
</head>
<body>

	<div id="wrapper">
	
		<jsp:include page="/WEB-INF/view/template/menu.jsp"></jsp:include>
		<div id="progress">
		
			<ul>
				<li class="active">본인확인</li>
				<li>게시글 삭제</li>
				<li>탈퇴 완료</li>
			</ul>
		
		</div>
		
		<div class="box">
		
			<p>탈퇴를 위해 비밀번호를 입력하세요.</p>
			<div>
				<form:form modelAttribute="verifyForm">
					<input type="password"	id="password"	name="password"	placeholder="비밀번호" />
					<div class="button" id="verifyBtn">확인</div>
					</form:form>
			</div>
		</div>
	
	</div>

</body>
</html>