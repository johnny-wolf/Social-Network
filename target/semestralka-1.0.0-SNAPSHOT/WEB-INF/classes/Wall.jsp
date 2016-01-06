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
    <title></title>
</head>
<body>

<c:if test="${not empty requestScope.err}">
    <p>
        Error: ${requestScope.err}
    </p>
</c:if>
<c:choose>
    <c:when test="${not empty sessionScope.user}">
		<jsp:include page="headerLogged.jsp"/>
    </c:when>
    <c:otherwise>
    	<jsp:include page="headerLogged.jsp"/>
    </c:otherwise>
</c:choose>
<c:forEach items="${sessionScope}" var="attr">
    ${attr.key}=${attr.value}<br>
</c:forEach>
 <!-- zacatek obsahu -->
    <div class="container">
    	<div class="row">
        	 <!-- Left bar -->
             <div class="col-sm-3 col-md-2 left-menu">
             	<h1>Profil</h1>
            	<ul class="nav nav-pills nav-stacked">
                	<li><a href="#"><c:out value="${sessionScope.user}"/></a></li>
                	<li><a href="#">Upravit profil</a></li>
           	 	</ul>
        	 </div>
             <!-- status window -->
          <div class="col-sm-5 col-md-6">
            <jsp:include page="addPost.jsp"/>     
			</div>>
				<!-- right column -->
                <div class="col-sm-3 col-md-3 right-menu">
                <h1>Přátelé</h1>
                <div class="facebook-search-area">
                             <textarea rows="1" placeholder="Hledat..."></textarea> 
                </div>
                <ul>
                    <li>
                        <img src="./ref-material-prototype/img/prof2.png" alt="Mountain View" style="width:40px;height:40px;"> <a href="">Annie </a>
                    </li>
                </ul>
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