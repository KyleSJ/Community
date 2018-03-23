<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${community.title }</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/alert.css"/>">
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/static/js/alert.js"/>"></script>
<script type="text/javascript">
$().ready(function() {

	function loadReplies(scrollTop){
    $.get("<c:url value='/api/reply/${community.id}'/>", {},
        function(response) {
            for (var i in response) {
                appendReplies(response[i]);
            }
            
            $(window).scrollTop(scrollTop);
        });
	}
	
    loadReplies(0);

    $("#writeReplyBtn").click(function() {
    		console.log("btn click")
        $.post("<c:url value="/api/reply/${community.id}"/>",
            $("#writeReplyForm").serialize(),
            function(response) {
                if (response.status) {
                    show("댓글 등록됨");
                    
                    $("#parentReplyId").val(0);
                    $("#body").val("");
                    $("#createReply").appendTo("#createReplyDiv");
                    
                    var scrollTop = $(window).scrollTop();
                    
                    $("#replies").html("");
                    loadReplies(scrollTop);
                    
                    //appendReplies(response.reply);
                } else {
                    alert("등록에 실패했습니다. 잠시 후에 다시 시도하세요!")
                }
            })
    })
	
    /* re-reply는 shadow DOM이므로 다르게 접근하여야 한다!!!!! */
    $("#replies").on("click", ".re-reply", function(){
    		var parentReplyId = $(this).closest(".reply").data("id");
    		$("#parentReplyId").val(parentReplyId);
    		
    		$("#createReply").appendTo($(this).closest(".reply"))
    })

    function appendReplies(reply) {
        var replyDiv = $("<div class='reply' style='padding-left:" + (reply.level-1)*20 + "px;' data-id='" + reply.id + "'></div>");
        var nickname = reply.memberVO.nickname + "(" + reply.memberVO.email + ")";
        var top = $("<span class='writer'></span>" + nickname + "<span class='regist-date'>" + reply.registDate + "</span>");
        replyDiv.append(top);

        var body = $("<div class='body'>" + reply.body + "</div>");
        replyDiv.append(body);

        var registReReply = $("<div class='re-reply'>댓글달기</div>");
        replyDiv.append(registReReply);
        $("#replies").append(replyDiv);

    }
})
</script>

</head>
<div class=wrapper>
	<jsp:include page="/WEB-INF/view/template/menu.jsp" />
	
	<h1>${community.title }</h1>
	<h3>
	<c:choose>
         <c:when test="${not empty community.memberVO}">
            ${community.memberVO.nickname}(${community.memberVO.email}) ${community.requestIp}
         </c:when>
         <c:otherwise>
            탈퇴한 회원 ${community.requestIp}
         </c:otherwise>
      </c:choose>
	</h3>
	<p>${community.viewCount}   |   ${community.recommendCount} |   ${community.writeDate}</p>
   <p></p>
   <c:if test="${not empty community.displayFilename}">
      <p>
         <a href="<c:url value="/get/${community.id}"/>">
            ${community.displayFilename}
         </a>
      </p>
   </c:if>
   <p>
      ${community.body}
   </p>
   
   <hr/>
	<div id="replies">
   </div>
   <div id="createReplyDiv">
   <div id="createReply">
      <form id="writeReplyForm">
         <input type="hidden" id="parentReplyId" name="parentReplyId" value="0"/>
         <div>
            <textarea id="body" name="body"></textarea>
         </div>
         <div>
            <input type="button" id="writeReplyBtn" value="등록"/>
         </div>
      </form>
   </div>
   </div>
   
   <p>
      <a href="<c:url value="/"/>">뒤로가기</a>
      <a href="<c:url value="/recommend/${community.id}"/>">추천하기</a>
      <c:if test="${community.memberVO.id == sessionScope.__USER__.id }">         
         <a href="<c:url value="/modify/${community.id}"/>">수정하기</a>
         <a href="<c:url value="/delete/${community.id}"/>">삭제하기</a>
      </c:if>
   </p>
   <%-- 
   
	<a href="<c:url value="/recommend/${community.id }"/>">추천하기</a>
	<div>제목 : ${community.title}</div>
	<div>사용자 명 :
	<c:choose>
	<c:when test="${not empty community.memberVO }">
	${community.memberVO.nickname}(${community.memberVO.email })
	</c:when>
	<c:otherwise>
	탈퇴한 사용자입니다.
	</c:otherwise>
	</c:choose>
	</div>
	<div>작성일 : ${community.writeDate }</div>

	<c:if test="${not empty community.displayFilename }">
		<div>
			파일이름 : <a href="<c:url value="/get/${community.id}" />">
				${community.displayFilename }</a>
		</div>
	</c:if>
	<div>내용 : ${community.body}</div>
	
	
	
	<div>조회소 : ${community.viewCount }</div>
	<div>추천수 : ${community.recommendCount }</div>
	<c:if test="${community.id > 1 }">
		<a href="<c:url value="/detail/${community.id - 1 }"/>">이전</a>
	</c:if>
	<a href="<c:url value="/"/>">목록</a>
	<c:if test="${not isLast }">
		<a href='<c:url value="/detail/${community.id + 1 }"/>'>다음</a>
	</c:if>
	<c:if test="${sessionScope.__USER__.id == community.memberVO.id }">
	<a href="<c:url value="/modify/${community.id}"/>">수정하기</a>
	<a href="<c:url value="/remove/${community.id}"/>">삭제하기</a>
	</c:if> --%>
</div>
</body>
</html>