<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.excilys.formation.java.model.*"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<t:pagination url="dashboard" nbPages="${ page.totalPages }" pageNumber="${ page.number }" nbResultsPerPage="${ page.size }" sort="${sort}" order="${order}" search="${search}"></t:pagination>
		</div>
	</footer>
</body>
</html>