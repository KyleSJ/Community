<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js" />"></script>
<script type="text/javascript">
	$().ready(function() {

		<c:if test="${sessionScope.status eq 'emptyId'}">
		$("#errorID").show();
		</c:if>
		
		<c:if test="${sessionScope.status eq 'emptyPW'}">
		$("#errorPW").show();
		</c:if>
		
		$("#loginBtn").click(function() {

			if ($("#email").val() == "") {
				$("#email").focus();
				$("#errorID").slideDown(300);
				return false;
			} else {
				$("#errorID").slideUp(300);
			}

			if ($("#password").val() == "") {
				$("#password").focus();
				$("#errorPW").slideDown(300);
				return false;
			} else {
				$("#errorPW").slideUp(300);
			}

			$("#loginForm").attr({
				"action" : "<c:url value="/login" />",
				"method" : "post"
			}).submit();

		});

	});
</script>
</head>
<style>
#errorID, #errorPW, #errorID2 {
	display: none;
	color: red;
}
#invalidIdAndPassword{
	color: red;
}
</style>
<body>

	<form id="loginForm">
		<c:if test="${sessionScope.status eq 'fail'}">
			<div id="invalidIdAndPassword">
				<div>아이디 혹은 비밀번호가 잘못되었습니다.</div>
				<div>한번 더 확인 후 시도해 주세요.</div>
			</div>
		</c:if>

		<div>
			<input type="email" id="email" name="email" placeholder="Email" />

		</div>
		<div id="errorID">이메일을 입력하세요</div>

		<div>
			<input type="password" id="password" name="password"
				placeholder="PASSWORD" />
		</div>
		<div id="errorPW">비밀번호를 입력하세요</div>
		<div>
			<input type="button" id="loginBtn" value="Login" />
		</div>
	</form>
	<div>
		<a href="<c:url value="/regist"/>"> 아직 회원이 아니신가요? </a>
	</div>
</body>
</html>