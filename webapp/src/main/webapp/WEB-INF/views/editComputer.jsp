<jsp:include page="includes/header.jsp" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.excilys.formation.java.model.*"%>

    <section id="main">
        <div class="container">
        	<span style="float: right"> 
				<a href="?id=${computer.id}&lang=en" class="btn btn-primary">en</a>
				<a href="?id=${computer.id}&lang=fr" class="btn btn-primary">fr</a>
			</span>
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1><spring:message code="title.edit"/></h1>
                    <form:form id="form" action="editcomputer" method="POST" commandName="computer">
                        <form:input type="hidden" path="id" value="${computer.id}"/>
                        <form:errors path="id" cssClass="error"/>
                        <fieldset>
                        	<div class="form-group">
                                <label for="computerName"><spring:message code="label.name"/></label>
                                <form:input path="name" type="text" class="form-control" id="computerName" placeholder="Computer name" value="${computer.name}" required="required"/>
                            	<form:errors path="name" cssClass="error"/>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="label.introduced"/></label>
                                <form:input path="introduced" type="date" class="form-control" id="introduced" placeholder="Introduced date : yyyy-MM-dd" value="${computer.introduced}"/>
                            	<form:errors path="introduced" cssClass="error"/>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="label.discontinued"/></label>
                                <form:input path="discontinued" type="date" class="form-control" id="discontinued" placeholder="Discontinued date : yyyy-MM-dd" value="${computer.discontinued}"/>
                            	<form:errors path="discontinued" cssClass="error"/>
                            </div>
                            <div class="form-group">
                                <label for="company"><spring:message code="label.company"/></label>
                                <form:select path="idCompany" class="form-control" id="idCompany">
                                    <option value="0">--</option>
                                    <c:forEach items="${companies}" var="company">
	                                   	<c:if test="${company.id == computer.idCompany}">
	                                   		<option value="${company.id}" selected="selected"><c:out value="${company.name}"/></option>
	                                   	</c:if>
	                                   	<c:if test="${company.id != computer.idCompany}">
	                                   		<option value="${company.id}"><c:out value="${company.name}"/></option>
	                                   	</c:if>
                                   	</c:forEach>
                                </form:select>
                                <form:errors path="idCompany" cssClass="error"/>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" id="submit" value="<spring:message code="button.edit"/>" class="btn btn-primary">
                            <spring:message code="text.or"/>
                            <a href="dashboard" class="btn btn-default"><spring:message code="button.cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    
    <style> .error{ color: red; } </style>
    <style> .valid{ color: green; } </style>
    <script src="js/jquery.min.js"></script>
<!-- 	<script src="js/jquery.validate.min.js"></script> -->
<!-- 	<script src="js/validForm.js"></script> -->
	
</body>
</html>