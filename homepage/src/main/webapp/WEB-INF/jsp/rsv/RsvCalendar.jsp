<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/template/header.do" charEncoding="utf-8">
	<c:param name="title" value="예약"/>
</c:import>

<div id="schedule">
	<div class="month">
		<c:url var="prevMonth" value="/rsv/selectCalendar.do">
			<c:param name="searchDate" value="${prevSearchDate}"/>
		</c:url>
		<a href="${prevMonth }"><img src="/asset/LYTTMP_0000000000000/images/calendar/btn_mprev.gif" alt="이전달"/></a>
		<strong>
			<c:out value="${currYear }"/> - <fmt:formatNumber value="${currMonth }" pattern="00"/>
		</strong>
		<c:url var="nextMonth" value="/rsv/selectCalendar.do">
			<c:param name="searchDate" value="${nextSearchDate}"/>
		</c:url>
		<a href="${nextMonth }"><img src="/asset/LYTTMP_0000000000000/images/calendar/btn_mnext.gif" alt="다음달"/></a>
	</div>
	
	<div id="calendar">
		<table class="mcal_chart">
			<caption><c:out value="${currYear }"/>년 <fmt:formatNumber value="${currMonth }" pattern="00"/>월 ${tableName} 신청 일정</caption>
			<thead>
			<tr>
				<th class="sun" scope="col">일</th>
				<th scope="col">월</th>
				<th scope="col">화</th>
				<th scope="col">수</th>
				<th scope="col">목</th>
				<th scope="col">금</th>
				<th class="sat" scope="col">토</th>
			</tr>
			</thead>
			<tbody>
				<c:set var="boxCnt" value="1"/>
				<c:set var="day" value="0"/>
				<c:set var="endWeek" value="0"/>
				<c:forEach var="weekRow" begin="1" end="6">
					<c:if test="${endWeek ne '1'}">
						<tr>
							<c:forEach var="weekCol" begin="1" end="7">
								<c:choose>
									<c:when test="${firstDayOfMonth <= boxCnt and lastDayOfMonth > day}">
										<c:set var="day" value="${day + 1 }"/>
										<td class="${weekCol eq 1?'sun' : weekCol eq 7?'sat':'' }">
											<c:set var="exprnDate">
												<c:out value="${currYear}"/><fmt:formatNumber value="${currMonth}" pattern="00"/><fmt:formatNumber value="${day}" pattern="00"/>
											</c:set>
											
											<em class="day"><c:out value="${day}"/></em>
											
											<div class="alC">
												<ul class="process_box">
													<c:forEach var="result" items="${resultList}">
														<c:set var="useBeginDt" value="${fn:replace(result.useBeginDt, '-', '')}"/>
														<c:set var="useEndDt" value="${fn:replace(result.useEndDt, '-', '')}"/>
														<c:if test="${useBeginDt <= exprnDate and useEndDt >= exprnDate}">
															<li>
																<c:url var="viewUrl" value="/rsv/rsvSelect.do${_BASE_PARAM}">
																	<c:param name="resveId" value="${result.resveId}"/>
																	<c:param name="listType" value="calendar"/>
																	<c:param name="searchDate" value="${searchVO.searchDate}"/>
																</c:url>
																<strong class="apply" style="display:inline-block;"><a href="${viewUrl}"><c:out value="${result.resveSj}"/> 신청</a></strong>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</c:when>
									<c:otherwise>
										<td class="${weekCol eq 1?'sun' : weekCol eq 7?'sat':'' }"></td>
									</c:otherwise>
								</c:choose>
								
								<c:set var="boxCnt" value="${boxCnt + 1 }"/>
							</c:forEach>
						</tr>
					</c:if>
					<c:if test="${lastDayOfMonth eq day}"> <c:set var="endWeek" value="1"/> </c:if>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
           
<c:import url="/template/footer.do" charEncoding="utf-8"/>