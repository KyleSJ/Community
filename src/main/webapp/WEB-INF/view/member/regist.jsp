<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/button.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/input.css"/>">
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function() {
		
		$("#registBtn").click(function() {

			if ($("#email").val() == "") {
				alert("이메일을 입력하세요");
				$("#email").addClass("invalid");
				$("#email").focus;
				return false;
			}
			if ($("#nickname").val() == "") {
				alert("닉네임을 입력하세요");
				$("#nickname").focus;
				$("#nickname").addClass("invalid");
				return false;
			}
			if ($("#password").val() == "") {
				alert("비밀번호를 입력하세요");
				$("#password").focus;
				$("#password").addClass("invalid");
				return false;
			}
			
			if( $("#email").hasClass("invalid") ){
				alert("작성한 email은 사용할 수 없습니다.");
				$("#email").focus();
				return false;
			} else{
				$.post("<c:url value="/api/exists/email"/>" , {
					email: $("#email").val()
				} , function(response) {
					console.log(response.response)
					if(response.response){
						alert("email이 중복되었습니다!");
						$("#email").removeClass("valid");
						$("#email").addClass("invalid");
						return false;
					} else{
						$("#email").removeClass("invalid");
						$("#email").addClass("valid");
						
						$.post("<c:url value="/api/exists/nickname"/>",{
							nickname : $("#nickname").val()
						},function(response){
							console.log(response.response)
							if(response.response){
								alert("닉네임이 중복됩니다!");
								$("#nickname").focus();
								$("#nickname").removeClass("valid");
								$("#nickname").addClass("invalid");
								return false;
							}else{
								$("#nickname").removeClass("invalid");
								$("#nickname").addClass("valid");
								
								$("#registForm").attr({
									"method" : "post" ,
									"action" : "<c:url value="/regist" />"
								}).submit();
							}
						})
					}
				})
			}
		});

		$("#email").keyup(function() {
			var value = $(this).val();
			if (value != "") {
				
				//Ajax Call (http://localhost:8080/api/exists/email)
				$.post("<c:url value="/api/exists/email"/>" , {
					email: value
				} , function(response) {
					console.log(response.response)
					if(response.response){
						$("#email").removeClass("valid");
						$("#email").addClass("invalid");
					} else{
						$("#email").removeClass("invalid");
						$("#email").addClass("valid");
					}
				})
			} else {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
		});

		$("#nickname").keyup(function() {
			var value = $(this).val();
			if (value != "") {
				
				$.post("<c:url value="/api/exists/nickname"/>",{
					nickname : $("#nickname").val()
				},function(response){
					console.log(response.response);
					if(response.response){
						$("#nickname").focus();
						$("#nickname").removeClass("valid");
						$("#nickname").addClass("invalid");
					}else{
						$("#nickname").removeClass("invalid");
						$("#nickname").addClass("valid");
					}
				})
			} else {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
		})

		$("#password").keyup(function() {
			var value = $(this).val();
			if (value != "") {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			} else {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}

			var password = $("#passwordConfirm").val();

			if (value != password) {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#passwordConfirm").removeClass("valid");
				$("#passwordConfirm").addClass("invalid");
			} else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#passwordConfirm").removeClass("invalid");
				$("#passwordConfirm").addClass("valid");

			}
		})

		$("#passwordConfirm").keyup(function() {
			var value = $(this).val();
			var password = $("#password").val();

			if (value != password) {
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password").removeClass("valid");
				$("#password").addClass("invalid");
			} else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password").removeClass("invalid");
				$("#password").addClass("valid");

			}
		})

	});
</script>
</head>
<body>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp"></jsp:include>
		<form:form modelAttribute="registForm">
			<div>
				<%-- TODO Email 중복검사 하기 --%>
				<input type="email" id="email" name="email" placeholder="email" value="${registForm.email }">
				<div>
					<form:errors path="email" />
				</div>
			</div>
			<div>
				<%-- TODO nickname 중복검사 하기 --%>
				<input type="text" id="nickname" name="nickname" value="${registForm.nickname }"
					placeholder="nickname">
				<div>
					<form:errors path="nickname" />
				</div>
			</div>
			<div>
				<input type="password" id="password" name="password"
					placeholder="password">
				<div>
					<form:errors path="password" />
				</div>
			</div>
			<div>
				<input type="password" id="passwordConfirm"
					placeholder="passwordConfirm">
			</div>
		</form:form>
		<div class="">
			<div id="registBtn" class="button">회원가입</div>
		</div>
	</div>


</body>
</html>