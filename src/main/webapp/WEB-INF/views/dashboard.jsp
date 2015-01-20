<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.excilys.formation.java.model.Page"%>
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
					<a class="btn btn-success" id="addComputer" href="AddComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="EditComputer"
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
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
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
							<a href="EditComputer?id=${computer.id}" onclick="">${computer.name}</a>
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

	<footer class="navbar-fixed-bottom">
	
		<div class="container text-center">
		
			<ul class="pagination">
			
				<c:if test="${page.pageNumber != 1}">
					<li><a href="DashBoard?page=${page.pageNumber-1}&nbResults=${page.nbResultsPerPage}" 
						aria-label="Previous"> 
						<span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				
				<c:if test="${page.pageNumber-3 > 0}">
					<li>
						<a href="DashBoard?page=${page.pageNumber-3}&nbResults=${page.nbResultsPerPage}">${page.pageNumber-3}</a>
					</li>
				</c:if>
				
				<c:if test="${page.pageNumber-2 > 0}">
					<li>
						<a href="DashBoard?page=${page.pageNumber-2}&nbResults=${page.nbResultsPerPage}">${page.pageNumber-2}</a>
					</li>
				</c:if>
				
				<c:if test="${page.pageNumber-1 > 0}">
					<li>
						<a href="DashBoard?page=${page.pageNumber-1}&nbResults=${page.nbResultsPerPage}">${page.pageNumber-1}</a>
					</li>
				</c:if>
				
				<c:forEach begin="${page.pageNumber}" end="${page.pageNumber+3}" var="i">
				
					<c:if test="${i <= nbPages}">
						<li>
							<a href="DashBoard?page=${i}&nbResults=${page.nbResultsPerPage}" >${i}</a>
						</li>
					</c:if>
					
				</c:forEach>
				
				<c:if test="${page.pageNumber != nbPages}">
					<li>
						<a href="DashBoard?page=${page.pageNumber+1}&nbResults=${page.nbResultsPerPage}"
							aria-label="Next"> 
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				
			</ul>
			
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a type="button" class="btn btn-default" href="DashBoard?page=1&nbResults=10">10</a>
				<a type="button" class="btn btn-default" href="DashBoard?page=1&nbResults=50">50</a>
				<a type="button" class="btn btn-default" href="DashBoard?page=1&nbResults=100">100</a>
			</div>
		</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>