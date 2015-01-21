<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.excilys.formation.java.model.Page"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<t:pagination url="dashBoard" nbPages="${ nbPages }" pageNumber="${ page.pageNumber }" nbResultsPerPage="${ page.nbResultsPerPage }"></t:pagination>
		</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>