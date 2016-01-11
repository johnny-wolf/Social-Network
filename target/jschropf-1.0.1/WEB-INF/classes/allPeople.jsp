<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <!-- Latest compiled and minified CSS -->
	    <link rel="stylesheet" href="./bootstrap-3.3.5-dist/css/bootstrap.min.css" />
	    <link rel="stylesheet" href="./bootstrap-3.3.5-dist/css/status-style.css" />
	    <title>ITNet</title>
	</head>
	<body>
		<c:choose>
		    <c:when test="${not empty sessionScope.user}">
				<jsp:include page="/headerLogged.jsp"/>
		    </c:when>
		    <c:otherwise>
		    	<jsp:include page="/header.jsp"/>
		    </c:otherwise>
		</c:choose>
		<c:if test="${not empty requestScope.err}">
		    <p>
		        Error: ${requestScope.err}
		    </p>
		</c:if>
		<!-- 
	 	<p>Session atributes</p>
		<c:forEach items="${sessionScope}" var="attr">
		    ${attr.key}=${attr.value}<br>
		</c:forEach>
		<p>Request atributes</p>
		<c:forEach items="${requestScope}" var="attr">
		    ${attr.key}=${attr.value}<br>
		</c:forEach>
		 -->
	 	<!-- zacatek obsahu -->
	    <div class="container">
	    	<div class="row">
	       		<!-- Left bar -->
	           	<jsp:include page ="/profileColumn.jsp?ownerId=${wallOwnerId}"/>
	             	
	           	<!-- status window -->
	       		<div class="col-sm-5 col-md-6">
	       			<h2>All people</h2>
			       	<c:forEach var="person" items="${allPeople}">
			       		<div class = "facebook-comment-box">
		               		<div>
		                   		<img src="ref-material-prototype/img/${person.picture}" onerror="this.src='ref-material-prototype/img/default.png'" style="width:75px;height:75px;">
		                       	<a href="wall?ownerId=${person.id}">${person.username} (${person.fName} ${person.lName})</a>
		                   	</div>
	               		</div>
			       	</c:forEach>
				</div>
					
				<!-- right column -->
				<div>
	               	<jsp:include page ="/friendColumn.jsp?people=${requestScope.friends}"/>
	           	</div>
			</div>
		</div>
		
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"
	        integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ=="
	        crossorigin="anonymous"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$('.status').click(function() { $('.arrow').css("left", 5);});
				$('.photos').click(function() { $('.arrow').css("left", 72);});
			});
		</script>
	</body>
</html>