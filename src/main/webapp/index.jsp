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
	    <title></title>
	</head>
	<body>
		<c:if test="${not empty sessionScope.user}">
			<c:redirect url="wall?ownerId=${userId}" />
		</c:if>
		<jsp:include page="/header.jsp"/>
		<c:if test="${not empty requestScope.err}">
		    <p>
		        Error: ${requestScope.err}
		    </p>
		</c:if>
		<div class="container">
	        <div class="col-sm-6 col-md-6 index-left-menu">
	        	<h1>Register</h1>
	            <p>ITNet was, is and will be always for free</p>
	            <p>* Credentials</p>
	            <form name="new_user_form" action="register" method="post" enctype="multipart/form-data">
	                <div>
	                    <div class="form-group">
	                        <label for="username">*User name:</label>
	                        <input class="form-control" type="text" id="username" name="username" value="<c:out value="${requestScope.username}" />"/>
	                    </div>
	                    <div class="form-group">
	                        <label for="pass">*Password:</label>
	                        <input class="form-control" type="password" id="pass" name="password"/>
	                    </div>
	                    <div class="form-group">
	                        <label for="pass2">*Confirm password:</label>
	                        <input class="form-control" type="password" id="pass2" name="confirmPwd"/>
	                    </div>
						<div class="form-group">
	                        <label for="username">First name:</label>
	                        <input class="form-control" type="text" id="fname" name="fname" value="<c:out value="${requestScope.fname}" />"/>
	                    </div>
	                    <div class="form-group">
	                        <label for="username">Last name:</label>
	                        <input class="form-control" type="text" id="lname" name="lname" value="<c:out value="${requestScope.lname}" />" />
	                    </div>
	                    <div class="form-group">
	                        <label for="birthdate">Birth date:</label>
							<input class="form-control" type="date" id="birthdate" name="birthdate" placeholder="dd.MM.yyyy" value=""/>  
	                    </div>
	                    <div class="form-group">
	                        <label for="picture">Profile picture:</label>
	                        <input class="form-control" type="file" id="picture" name="picture"/>
	                    </div>
	                    <div class="form-group">
	                        <label for="username">What is 3 + 1? :</label>
	                        <input class="form-control" type="text" id="testQ" name="testQ" value=""/>
	                    </div>
	                    <div class="form-group">
	                      <p>I agree with the information above</p><input type="radio" name="agreement" value="yes"  /> yes
						  <input type="radio" name="agreement" value="no" checked/>no <br><br> 
	                    </div>
	                    
	                    <div>
		                    <button class="btn btn-primary" type="submit" value="Potvrdit">Submit</button>
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
