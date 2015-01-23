<%@ tag language="java" pageEncoding="UTF-8" description="Pagination"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="url" required="true"%>
<%@ attribute name="nbPages" type="java.lang.Integer" required="true"%>
<%@ attribute name="pageNumber" type="java.lang.Integer" required="true"%>
<%@ attribute name="nbResultsPerPage" required="true"%>
<%@ attribute name="search" required="false"%>
<%@ attribute name="sort" required="false"%>
<%@ attribute name="order" required="false"%>



<ul class="pagination">
	<c:if test="${pageNumber > 1}">
		<li>
			<t:link url="${url}" pageNumber="${pageNumber-1}" nbResultsPerPage="${nbResultsPerPage}" sort="${sort}" order="${order}" search="${search}"><span aria-hidden="true">&laquo;</span></t:link>
		</li>
	</c:if>

	<c:if test="${pageNumber-3 > 0}">
		<li>
			<t:link url="${url}" pageNumber="${pageNumber-3}" nbResultsPerPage="${nbResultsPerPage}" sort="${sort}" order="${order}" search="${search}">${pageNumber-3}</t:link>
		</li>
	</c:if>

	<c:if test="${page.pageNumber-2 > 0}">
		<li>
			<t:link url="${url}" pageNumber="${pageNumber-2}" nbResultsPerPage="${nbResultsPerPage}" sort="${sort}" order="${order}" search="${search}">${pageNumber-2}</t:link>
		</li>
	</c:if>

	<c:if test="${page.pageNumber-1 > 0}">
		<li>
			<t:link url="${url}" pageNumber="${pageNumber-1}" nbResultsPerPage="${nbResultsPerPage}" sort="${sort}" order="${order}" search="${search}">${pageNumber-1}</t:link>
		</li>
	</c:if>

	<c:forEach begin="${pageNumber}" end="${pageNumber+3}" var="i">

		<c:if test="${i <= nbPages}">
			<c:if test="${pageNumber == i}">
				<li>
					<t:link url="${url}" pageNumber="${i}" nbResultsPerPage="${nbResultsPerPage}" sort="${sort}" order="${order}" search="${search}"><Strong>${i}</Strong></t:link>
				</li>
			</c:if>
			<c:if test="${pageNumber != i}">
				<li>
					<t:link url="${url}" pageNumber="${i}" nbResultsPerPage="${nbResultsPerPage}" sort="${sort}" order="${order}" search="${search}">${i}</t:link>
				</li>
			</c:if>
		</c:if>

	</c:forEach>

	<c:if test="${pageNumber < nbPages}">
		<li>
			<t:link url="${url}" pageNumber="${pageNumber+1}" nbResultsPerPage="${nbResultsPerPage}" sort="${sort}" order="${order}" search="${search}"><span aria-hidden="true">&raquo;</span></t:link>
		</li>
	</c:if>
</ul>

<div class="btn-group btn-group-sm pull-right" role="group">
	<t:link classes="btn btn-default" url="${url}" nbResultsPerPage="10" sort="${sort}" order="${order}" search="${search}">10</t:link>
	<t:link classes="btn btn-default" url="${url}" nbResultsPerPage="50" sort="${sort}" order="${order}" search="${search}">50</t:link>
	<t:link classes="btn btn-default" url="${url}" nbResultsPerPage="100" sort="${sort}" order="${order}" search="${search}">100</t:link>
</div>