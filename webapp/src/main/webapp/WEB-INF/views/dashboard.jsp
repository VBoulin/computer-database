<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.excilys.formation.java.model.*"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="includes/header.jsp" />

	<section id="main">
		<div class="container">
        	<span style="float: right"> 
				<a href="?lang=en" class="btn btn-primary">en</a>
				<a href="?lang=fr" class="btn btn-primary">fr</a>
			</span>
			<h1 id="homeTitle">${page.totalElements} <spring:message code="title.dashboard"/></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<spring:message code="placeholder.search"/>" /> <input
							type="submit" id="searchsubmit" value="<spring:message code="button.filter"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addcomputer" href="addcomputer"><spring:message code="button.addComputer"/></a> 
					<a class="btn btn-default" id="editcomputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="button.edit"/></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="delete" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <!-- Table header for Computer name -->
                        <c:choose>
							<c:when test="${order.equalsIgnoreCase(\"desc\") }">
		                        <th>
		                        	<t:link url="dashboard" pageNumber="${page.number}" nbResultsPerPage="${page.size}" sort="name" order="asc" search="${search}" ><spring:message code="label.name"/></t:link>
		                        </th>
		                        <c:set var="order" value="DESC"/>
                        	</c:when>
							<c:otherwise>
		                        <th>
		                        	<t:link url="dashboard" pageNumber="${page.number}" nbResultsPerPage="${page.size}" sort="name" order="desc" search="${search}" ><spring:message code="label.name"/></t:link>
								</th>
								<c:set var="order" value="ASC"/>
							</c:otherwise>
						</c:choose>
                        <!-- Table header for Introduced date -->
                        <c:choose>
							<c:when test="${order.equalsIgnoreCase(\"DESC\") }">
		                        <th>
		                        	<t:link url="dashboard" pageNumber="${page.number}" nbResultsPerPage="${page.size}" sort="introduced" order="asc" search="${search}" ><spring:message code="label.introduced"/></t:link>
		                        </th>
                        	</c:when>
							<c:otherwise>
		                        <th>
		                        	<t:link url="dashboard" pageNumber="${page.number}" nbResultsPerPage="${page.size}" sort="introduced" order="desc" search="${search}" ><spring:message code="label.introduced"/></t:link>
								</th>
							</c:otherwise>
						</c:choose>
                        <!-- Table header for Discontinued Date -->
                        <c:choose>
							<c:when test="${order.equalsIgnoreCase(\"DESC\") }">
		                        <th>
		                        	<t:link url="dashboard" pageNumber="${page.number}" nbResultsPerPage="${page.size}" sort="discontinued" order="asc" search="${search}" ><spring:message code="label.discontinued"/></t:link>
		                        </th>
                        	</c:when>
							<c:otherwise>
		                        <th>
		                        	<t:link url="dashboard" pageNumber="${page.number}" nbResultsPerPage="${page.size}" sort="discontinued" order="desc" search="${search}" ><spring:message code="label.discontinued"/></t:link>
								</th>
							</c:otherwise>
						</c:choose>
                        <!-- Table header for Company -->
                        <c:choose>
							<c:when test="${order.equalsIgnoreCase(\"DESC\") }">
		                        <th>
		                        	<t:link url="dashboard" pageNumber="${page.number}" nbResultsPerPage="${page.size}" sort="company.name" order="asc" search="${search}" ><spring:message code="label.company"/></t:link>
		                        </th>
                        	</c:when>
							<c:otherwise>
		                        <th>
		                        	<t:link url="dashboard" pageNumber="${page.number}" nbResultsPerPage="${page.size}" sort="company.name" order="desc" search="${search}" ><spring:message code="label.company"/></t:link>
								</th>
							</c:otherwise>
						</c:choose>

                    </tr>
                </thead>

				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${page.content}" var="computer">
						<tr>
							<td class="editMode">
								<input type="checkbox" name="cb" class="cb" value="${computer.id}">
							</td>
							<td>
								<a href="editcomputer?id=${computer.id}" onclick="">${computer.name}</a>
							</td>
							<td>
								${computer.introduced}
							</td>
							<td>
								${computer.discontinued}
							</td>
							<td>
								${computer.companyName}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

<jsp:include page="includes/footer.jsp" />