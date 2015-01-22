<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.excilys.formation.java.model.*"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<jsp:include page="includes/header.jsp" />

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${page.nbResults} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
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
                        <th>
                        	<t:link url="dashBoard" pageNumber="${page.pageNumber}" nbResultsPerPage="${page.nbResultsPerPage}" sort="name" order="${page.order.order}" search="${page.search}" >Computer name</t:link>
                        </th>
                        <!-- Table header for Introduced date -->
                        <th>
                        	<t:link url="dashBoard" pageNumber="${page.pageNumber}" nbResultsPerPage="${page.nbResultsPerPage}" sort="introduced" order="${page.order.order}" search="${page.search}">Introduced date</t:link>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                        	<t:link url="dashBoard" pageNumber="${page.pageNumber}" nbResultsPerPage="${page.nbResultsPerPage}" sort="discontinued" order="${page.order.order}" search="${page.search}">Discontinued date</t:link>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                        	<t:link url="dashBoard" pageNumber="${page.pageNumber}" nbResultsPerPage="${page.nbResultsPerPage}" sort="company_name" order="${page.order.order}" search="${page.search}">Company</t:link>
                        </th>

                    </tr>
                </thead>

				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${page.list}" var="computer">
						<tr>
							<td class="editMode">
								<input type="checkbox" name="cb" class="cb" value="${computer.id}">
							</td>
							<td>
								<a href="editComputer?id=${computer.id}" onclick="">${computer.name}</a>
							</td>
							<td>
								${computer.introduced}
							</td>
							<td>
								${computer.discontinued}
							</td>
							<td>
								${computer.company.name}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

<jsp:include page="includes/footer.jsp" />