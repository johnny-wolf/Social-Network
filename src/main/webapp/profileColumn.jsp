<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
	</head>
	<body>
		<div class="col-sm-3 col-md-2 left-menu">
	            <h1>Profile</h1>
	            <br/>
	            	<c:if test="${requestScope.wallOwnerId ne sessionScope.userId}">
	            		<c:set var="contains" value="false" />
						<c:forEach var="friend" items="${people}">
						  <c:if test="${friend.id eq wallOwnerId}">
						    <c:set var="contains" value="true" />
						  </c:if>
						</c:forEach>
						<c:if test="${contains eq 'false'}">
		            	<form action="friendRequest" method="post" role="form" class="facebook-share-box">
		             	<div class="form-group">
		                	<input type="hidden" name="targetId" value="<%= request.getAttribute("wallOwnerId") %>">
		                    <input type="hidden" name="userId" value="${sessionScope.userId}">                                  
		                    <input type="submit" name="submit" value="Add friend" class="btn btn-primary">                               
		                </div>
		            	</form>
		            	</c:if>
				    </c:if>
            	<ul class="nav nav-pills nav-stacked">
                	<li><a href="#"><c:out value="${sessionScope.user}"/></a></li>
                	<li><a href="#">Update Profile</a></li>
           	 	</ul>
        </div>
	</body>
</html>