<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/template/header.do" charEncoding="utf-8">
	<c:param name="title" value="게시판"/>
</c:import>

<%-- 게시판 타입 --%>
<%-- 
<c:set var="boardType">
<c:choose>
	<c:when test="${not empty searchVO.boardType}">
		<c:out value="${searchVO.boardType}"></c:out>
	</c:when>
	<c:otherwise>
		NORMAL
	</c:otherwise>
</c:choose>
</c:set>
 --%>
<%-- 기본 URL --%>
<c:url var="_BASE_PARAM" value="">
	<%-- <c:param name="boardType" value="${boardType}" /> --%>
	<c:param name="pageIndex" value="${searchVO.pageIndex}" />
  	<c:if test="${not empty searchVO.searchCondition}"><c:param name="searchCondition" value="${searchVO.searchCondition}" /></c:if>
  	<c:if test="${not empty searchVO.searchKeyword}"><c:param name="searchKeyword" value="${searchVO.searchKeyword}" /></c:if>
</c:url>

<div class="container">
	<div id="contents">
		<div id="bbs_wrap">
			<div class="board_view">				
				<dl class="tit_view">
					<dt>제목</dt>
					<dd><c:out value="${result.boardSj}"/></dd>
				</dl>
				<dl class="info_view2">
					<dt>작성자ID</dt>
					<dd><c:out value="${result.frstRegisterId}"/></dd>
					<dt>작성일</dt>
					<dd><fmt:formatDate value="${result.frstRegistPnttm}" pattern="yyyy-MM-dd"/></dd>
					<dt>조회수</dt>
					<dd><c:out value="${result.inqireCo}" /></dd>
				</dl>
				
				<dl class="tit_view">
					<dt>첨부파일목록</dt>
					<dd>
						<c:import url="/cmm/fms/selectFileInfo.do" charEncoding="utf-8">
		                    <c:param name="param_atchFileId" value="${result.atchFileId}" />
		                </c:import>
					</dd>
				</dl>
				
				<div class="view_cont">
					<c:out value="${result.boardCn}" escapeXml="false" />
				</div>
			</div>
			
			<div class="btn-cont ar">
			    <c:choose>
					<c:when test="${not empty searchVO.boardId}">
						<c:url var="uptUrl" value="/board/boardRegist.do${_BASE_PARAM}">
							<c:param name="boardId" value="${result.boardId}"/>
						</c:url>
						<a href="${uptUrl}" class="btn">수정</a>
						
						<c:url var="delUrl" value="/board/delete.do${_BASE_PARAM}">
							<c:param name="boardId" value="${result.boardId}"/>
						</c:url>
			    		<a href="${delUrl}" id="btn-del" class="btn"><i class="ico-del"></i> 삭제</a>
					</c:when>
					<c:otherwise>
						<a href="#none" id="btn-reg" class="btn spot">등록</a>
					</c:otherwise>
				</c:choose>
				<c:url var="listUrl" value="/board/selectList.do${_BASE_PARAM}"/>
			    <a href="${listUrl}" class="btn">목록</a>
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function(){
	//게시 글 삭제
	$("#btn-del").click(function(){
		if(!confirm("삭제하시겠습니까?")){
			return false;
		}
	});
});
</script>
                  
<c:import url="/template/footer.do" charEncoding="utf-8"/>