<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
	</head>
	<body>
			<div class="col-sm-3 col-md-3 right-menu">
                <h1>Friends</h1>
                <div class="facebook-search-area">
                             <textarea rows="1" placeholder="Hledat..."></textarea> 
                </div>
                <ul>
			    <c:forEach var="person" items="${people}">
                    <li>
                        <a href="wall?ownerId=${person.id}"><img src="./ref-material-prototype/img/${person.picture}" style="width:40px;height:40px;"/></a>
                    </li>
                    <li>
                    	<a href="wall?ownerId=${person.id}">${person.fName} ${person.lName}</a>
                    </li>
			    </c:forEach>
			    </ul>
             </div>
	</body>
</html>