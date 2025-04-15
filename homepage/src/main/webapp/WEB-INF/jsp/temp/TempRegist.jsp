<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="ko" >
<title>데이터 가져오기~</title>
</head>
<body>
<c:choose>
	<c:when test="${not empty result.tempId}">
		<c:set var="actionUrl" value="/temp/update.do"/>
		<c:set var="pageName" value="수정"/>
	</c:when>
	<c:otherwise>
		<c:set var="actionUrl" value="/temp/insert.do"/>
		<c:set var="pageName" value="등록"/>
	</c:otherwise>
</c:choose>

* 등록폼
<c:out value="${pageName}"/>폼
<form action="${actionUrl}" method="post" name="tempVO">
<%-- <form action="/temp/insert.do" method="post" name="tempVO"> --%>
	<%-- <input type="hidden" name="tempId" value="${result.tempId}"/> --%>
	<label for="tempVal">값 정보 : </label> 
	<input type="text" id="tempVal" name="tempVal" value="${result.tempVal}"/>
	<br/>
	<button type="submit">등록</button>
	<%-- 
	<c:choose>
		<c:when test="${not empty result.tempId}">
			<button type="submit">수정</button>
		</c:when>
		<c:otherwise>
			<button type="submit">등록</button>
		</c:otherwise>
	</c:choose>
	 --%>
</form>
</body>
</html>

