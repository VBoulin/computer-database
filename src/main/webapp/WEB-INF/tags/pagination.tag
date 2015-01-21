<%@ tag language="java" pageEncoding="UTF-8" description="Pagination"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="url" required="true"%>
<%@ attribute name="nbPages" required="true"%>
<%@ attribute name="pageNumber" required="true"%>
<%@ attribute name="nbResultsPerPage" required="true"%>


<ul class="pagination">
	<c:if test="${pageNumber != 1}">
		<li><a
			href="${url}?page=${pageNumber-1}&nbResults=${nbResultsPerPage}"
			aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
		</a></li>
	</c:if>

	<c:if test="${pageNumber-3 > 0}">
		<li>
			<t:link url="${url}" pageNumber="${pageNumber-3}" nbResultsPerPage="${nbResultsPerPage}">${pageNumber-3}</t:link>
		</li>
	</c:if>

	<c:if test="${page.pageNumber-2 > 0}">
		<li>
			<t:link url="${url}" pageNumber="${pageNumber-2}" nbResultsPerPage="${nbResultsPerPage}">${pageNumber-2}</t:link>
		</li>
	</c:if>

	<c:if test="${page.pageNumber-1 > 0}">
		<li>
			<t:link url="${url}" pageNumber="${pageNumber-1}" nbResultsPerPage="${nbResultsPerPage}">${pageNumber-1}</t:link>
		</li>
	</c:if>

	<c:forEach begin="${pageNumber}" end="${pageNumber+3}"
		var="i">

		<c:if test="${i <= nbPages}">
			<c:if test="${pageNumber == i}">
				<li>
					<t:link url="${url}" pageNumber="${i}" nbResultsPerPage="${nbResultsPerPage}"><Strong>${i}</Strong></t:link>
				</li>
			</c:if>
			<c:if test="${pageNumber != i}">
				<li>
					<t:link url="${url}" pageNumber="${i}" nbResultsPerPage="${nbResultsPerPage}">${i}</t:link>
				</li>
			</c:if>
		</c:if>

	</c:forEach>

	<c:if test="${pageNumber != nbPages}">
		<li><a
			href="${url}?page=${pageNumber+1}&nbResults=${nbResultsPerPage}"
			aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</c:if>
</ul>

<div class="btn-group btn-group-sm pull-right" role="group">
	<t:link classes="btn btn-default" url="${url}" nbResultsPerPage="10">10</t:link>
	<t:link classes="btn btn-default" url="${url}" nbResultsPerPage="50">50</t:link>
	<t:link classes="btn btn-default" url="${url}" nbResultsPerPage="100">100</t:link>
</div>