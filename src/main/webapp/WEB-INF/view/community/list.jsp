<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
img {
	width: 20px;
}
</style>
</head>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function() {
		$("#logout").click(function() {
			alert("로그아웃 하셨습니다");
		})
	});
</script>
<body>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<div>
			${pageExplorer.totalCount }건의 게시글이 검색되었습니다.
		</div>
		<table>
			<tr>
				<th>ID</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
				<th>추천수</th>
			</tr>
			<c:forEach items="${pageExplorer.list }" var="list">
				<tr>

					<td>${list.id }</td>
					<td><a href='<c:url value="/read/${list.id}"/>'>${list.title }</a>
						<c:if test="${not empty list.displayFilename }">
							<img src="<c:url value="/static/img/file.png"/>">
						</c:if></td>

					<td><c:choose>
							<c:when test="${not empty list.memberVO }">
					${list.memberVO.nickname}(${list.memberVO.email })
					</c:when>
							<c:otherwise>
					탈퇴한 회원
					</c:otherwise>
						</c:choose></td>

					<td>${list.writeDate }</td>
					<td>${list.viewCount }</td>
					<td>${list.recommendCount }</td>

				</tr>
			</c:forEach>
		</table>
		
		<form id="searchForm">
			${pageExplorer.make()}
		</form>
		
		<c:if test="${empty pageExplorer.list}">
			<tr>
				<td colspan="5">등록된 게시글이 없습니다.</td>
			</tr>
		</c:if>
		<div>
			<a href="<c:url value="/write" />">글쓰기</a>
		</div>
		<div>
			<a id="logout" href="<c:url value="/logout" />">로그아웃</a>
		</div>

	</div>


</body>
</html>