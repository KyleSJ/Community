<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 아래 주소에서 static은 폴더를 가리키는게아니라 applicationContext.xml에 정의되어있는 static을 뜻함. -->
<script src="<c:url value="/static/js/jquery-3.3.1.min.js" />"
	type="text/javascript"></script>
<script type="text/javascript">
	$().ready(function() {
		
		<c:if test="${mode == 'modify' && not empty community.displayFilename }">
		$("#file").closest("div").hide();
		</c:if>

		$("#displayFilename").change(function() {
			var isChecked = $(this).prop("checked");
			if (isChecked) {
				$("label[for=displayFilename]").css({
					"text-decoration-line" : "line-through",
					"text-decoration-style" : "double",
					"text-decoration-color" : "#ff0000",
				});
				$("#file").closest("div").show();
			} else {
				$("label[for=displayFilename]").css({
					"text-decoration-line" : "none"
				});
				$("#file").closest("div").hide();
			}
		})

		$("#writeBtn").click(function() {
			var mode = "${mode}"
			console.log("writeBtn 클릭");
			var writeForm = $("#writeForm");

			if (mode == "modify") {
				var url = "<c:url value="/modify/${community.id}"/>"
			} else {
				var url = "<c:url value="/write"/>"
			}

			writeForm.attr({
				"method" : "post",
				"action" : url
			});

			writeForm.submit();
		});

	})
</script>
<style>
input {
	width: 300px;
	height: 20px;
	margin: 5px;
}

textarea {
	width: 300px;
	margin: 5px;
}

span {
	display: inline-block;
	width: 200px;
	q text-align: center;
}

.btn {
	width: 150px;
}

.error {
	color: red;
	display: none;
}

#displayFilename {
	width: 30px;
}
</style>
</head>
<body>

	<div class="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />

		<form:form modelAttribute="writeForm" enctype="multipart/form-data">

			<div class="input-box">
				<span>제목 </span><input type="text" id="title" name="title"
					value="${community.title }" />
				<div>
					<form:errors path="title" />
				</div>
			</div>

			<div class="input-box">
				<span>작성자 </span><input type="text" id="nickname" name="nickname"
					value="${sessionScope.__USER__.nickname }" readonly="readonly" />
			</div>

			<div class="input-box">
				<span>내용 </span>
				<textarea id="body" name="body">${community.body }</textarea>
				<div>
					<form:errors path="body" />
				</div>
			</div>

			<c:if
				test="${mode == 'modify' && not empty community.displayFilename  }">
				<div>
					<input type="checkbox" id="displayFilename" name="displayFilename"
						value="${community.displayFilename }" /> <label
						for="displayFilename"> ${community.displayFilename } </label>
				</div>
			</c:if>

			<div class="input-box">
				<input type="hidden" id="userId" name="userId"
					value="${sessionScope.__USER__.id }" readonly="readonly" />
			</div>

			<div>
				<input class="btn" type="file" id="file" name="file" />
			</div>

		</form:form>

		<div>
			<input class="btn" type="button" id="writeBtn" value="등록">
		</div>

	</div>

</body>
</html>