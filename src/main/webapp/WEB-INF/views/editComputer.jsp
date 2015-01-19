<jsp:include page="includes/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                    <form action="EditComputer" method="POST">
                        <input type="hidden" name="computerId" value="${computer.id}"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="name" placeholder="Computer name" value="${computer.name}">
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
	                                   	<c:if test="${company.id == computer.company.id}">
	                                   	<option value="${company.id}" selected="selected">${company.name}</option>
	                                   	</c:if>
	                                   	<c:if test="${company.id != computer.company.id}">
	                                   	<option value="${company.id}">${company.name}</option>
	                                   	</c:if>
                                   	</c:forEach>
                                </select>
                                <span>${error.get("companyId")}</span>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="DashBoard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>