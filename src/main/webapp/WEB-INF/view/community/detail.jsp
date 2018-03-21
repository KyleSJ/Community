<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<div class=wrapper>
	<jsp:include page="/WEB-INF/view/template/menu.jsp" />
	<a href="<c:url value="/recommend/${detail.id }"/>">추천하기</a>
	<div>제목 : ${detail.title}</div>
	<div>사용자 명 :
	<c:choose>
	<c:when test="${not empty detail.memberVO }">
	${detail.memberVO.nickname}(${detail.memberVO.email })
	</c:when>
	<c:otherwise>
	탈퇴한 사용자입니다.
	</c:otherwise>
	</c:choose>
	</div>
	<div>작성일 : ${detail.writeDate }</div>

	<c:if test="${not empty detail.displayFilename }">
		<div>
			파일이름 : <a href="<c:url value="/get/${detail.id}" />">
				${detail.displayFilename }</a>

		</div>
	</c:if>
	<div>내용 : ${detail.body}</div>
	<div>조회소 : ${detail.viewCount }</div>
	<div>추천수 : ${detail.recommendCount }</div>
	<c:if test="${detail.id > 1 }">
		<a href="<c:url value="/detail/${detail.id - 1 }"/>">이전</a>
	</c:if>
	<a href="<c:url value="/"/>">목록</a>
	<c:if test="${not isLast }">
		<a href='<c:url value="/detail/${detail.id + 1 }"/>'>다음</a>
	</c:if>
	<c:if test="${sessionScope.__USER__.id == detail.memberVO.id }">
	<a href="<c:url value="/modify/${detail.id}"/>">수정하기</a>
	<a href="<c:url value="/remove/${detail.id}"/>">삭제하기</a>
	</c:if>
</div>
</body>
</html>