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
    <title>User Wall</title>
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
    	<jsp:include page="header.jsp"/>
    </c:otherwise>
</c:choose>
<!-- <p>Session atributes</p>
<c:forEach items="${sessionScope}" var="attr">
    ${attr.key}=${attr.value}<br>
</c:forEach>
<p>Request atributes</p>
<c:forEach items="${requestScope}" var="attr">
    ${attr.key}=${attr.value}<br>
</c:forEach> -->
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
          	<c:if test="${param.FriendError eq 'noFriend'}">
			    <p>
			        Adding post not permitted!
			    </p>
			</c:if>  
          	<a href="wall?ownerId=${wallOwnerId}">Chronological</a> |
   			<a href="wall?ownerId=${wallOwnerId}&filter=popular">Popular</a>
            <jsp:include page="addPost.jsp"/>
		        <c:forEach var="post" items="${posts}">
		        	<div class = "facebook-comment-box">
	                    <div>
	                    	<img src="./ref-material-prototype/img/${post.poster.picture}" alt="./ref-material-prototype/img/person.gif" style="width:75px;height:75px;">
	                        <a href="wall?ownerId=${post.poster.id}">${post.poster.fName} ${post.poster.lName}</a>
	                    </div>
	                    <h3> ${post.title}</h3>
	                    <div class = "facebook-comment-status">
	                    	<p>${post.text}</p>
	                    </div>
	                    <div>
	                    	<p> ${post.formattedDate} | <a href"">Like</a> ${post.popularity} Liked</p>
	                    	<p><i><a href="comments?postId=${post.id}">View/Add Comments</a></i></p>
	                    </div>
                	</div>
                		                    	<c:if test="${requestScope.commentActive eq post.id}">
							    <p>
							        <jsp:include page="comment.jsp?postId=${post.id}&comments=${requestScope.comments}"/>
							    </p>
							</c:if>
		        </c:forEach>
    
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