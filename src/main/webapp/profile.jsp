<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
		<div class="container">
			<div class="col-sm-6 col-md-6 index-left-menu">
		     	<h1>Edit Profile</h1>
		        <div class="form-group">
		        	<img src="ref-material-prototype/img/${requestScope.picture}" onerror="this.src='ref-material-prototype/img/default.png'" style="width:100px;height:100px;">
		        	<p>Age: ${requestScope.age}</p>
		        </div>
		        <form name="profileEdit" action="editProfile" method="post" enctype="multipart/form-data">
		    	    <div>
		        	    <div class="form-group">
		                <label for="pass">New password:</label>
		                <input class="form-control" type="password" id="pass" name="password"/>
		            </div>
		            <div class="form-group">
		            	<label for="pass2">Confirm password:</label>
		                <input class="form-control" type="password" id="pass2" name="confirmPwd"/>
		            </div>
					<div class="form-group">
		            	<label for="username">First name:</label>
		                <input class="form-control" type="text" id="fname" name="fname" value="${requestScope.fname}"/>
		            </div>
		            <div class="form-group">
		            	<label for="username">Last name:</label>
		                <input class="form-control" type="text" id="lname" name="lname" value="${requestScope.lname}" />
		            </div>
		            <div class="form-group">
		            	<label for="birthdate">Birth date:</label>
						<input class="form-control" type="date" id="birthdate" name="birthdate" placeholder="dd.MM.yyyy" value="${requestScope.birthdate}"/>  
		            </div>
		            <div class="form-group">
		                <label for="picture">Profile picture:</label>
		                <input class="form-control" type="file" id="picture" name="picture"/>
		            </div>
		            <div>
		            	<button class="btn btn-primary" type="submit" value="Potvrdit">Edit</button>
		                <button class="btn btn-default" type="reset" value="Reset">Reset</button>
		            </div>
		            </div>
		        </form>	
		    </div>
		</div>
		
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"
		        integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ=="
		        crossorigin="anonymous"></script>
	</body>
</html>