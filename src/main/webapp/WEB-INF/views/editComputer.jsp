<jsp:include page="includes/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="com.excilys.formation.java.model.*"%>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1>Edit Computer</h1>
					<span>${error.get("computerId")}</span>
                    <form id="form" action="editcomputer" method="POST">
                        <input type="hidden" name="computerId" value="${computer.id}"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="name" placeholder="Computer name" value="${computer.name}" required="required">
								<span>${error.get("name")}</span>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="${computer.introduced}">
								<span>${error.get("introduced")}</span>                           
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${computer.discontinued}">
 								<span>${error.get("discontinued")}</span>                          
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name ="companyId">
                                    <option value="0">--</option>
                                    
                                    <c:forEach items="${companies}" var="company">
                                    
	                                   	<c:if test="${company.id == computer.idCompany}">
	                                   		<option value="${company.id}" selected="selected"><c:out value="${company.name}"/></option>
	                                   	</c:if>
	                                   	
	                                   	<c:if test="${company.id != computer.idCompany}">
	                                   		<option value="${company.id}"><c:out value="${company.name}"/></option>
	                                   	</c:if>
	                                   	
                                   	</c:forEach>
                                   	
                                </select>
                                <span>${error.get("companyId")}</span>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" id="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    
    <style> .error{ color: red; } </style>
    <style> .valid{ color: green; } </style>
    <script src="js/jquery.min.js"></script>
	<script src="js/jquery.validate.min.js"></script>
	<script src="js/validForm.js"></script>
	
</body>
</html>