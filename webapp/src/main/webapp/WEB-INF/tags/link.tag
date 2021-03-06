<%@ tag body-content="scriptless" %>
<%@ attribute name="classes" required="false" %>
<%@ attribute name="url" required="true" %>
<%@ attribute name="pageNumber" required="false"  type="java.lang.Integer"%>
<%@ attribute name="nbResultsPerPage" required="false"  type="java.lang.Integer"%>
<%@ attribute name="order" required="false" %>
<%@ attribute name="sort" required="false" %>
<%@ attribute name="search" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:doBody var="content" />

<c:if test="${pageNumber == null}">
	<c:set var="pageNumber" value="1"/>
</c:if>
<c:if test="${nbResultsPerPage == null }">
	<c:set var="nbResultsPerPage" value="10"/>
</c:if>
<c:if test="${search != null }">
	<c:set var="search" value="&search=${search}"/>
</c:if>
<c:if test="${sort != null }">
	<c:set var="sort" value="&sort=${sort}"/>
	<c:if test="${order != null }">
		<c:set var="sort" value="${sort},${order}"/>
	</c:if>
</c:if>

<a class="${classes}" href="${url}?page=${pageNumber}&size=${nbResultsPerPage}${sort}${search}">${content}</a>

