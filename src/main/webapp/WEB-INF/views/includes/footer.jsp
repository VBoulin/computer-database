<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.excilys.formation.java.model.*"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<t:pagination url="dashboard" nbPages="${ nbPages }" pageNumber="${ page.pageNumber }" nbResultsPerPage="${ page.nbResultsPerPage }" sort="${page.sort.column}" order="${page.order.order}" search="${page.search}"></t:pagination>
		</div>
	</footer>
	
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>