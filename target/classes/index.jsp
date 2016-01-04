<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<c:redirect url="/Wall.jsp" />
</c:if>
<jsp:include page="header.jsp"/>
<c:if test="${not empty requestScope.err}">
    <p>
        Error: ${requestScope.err}
    </p>
</c:if>
<div class="container">
        <div class="col-sm-6 col-md-6 index-left-menu">
        	<h1>Zaregistrovat se</h1>
            <p>ITNet byl, je a bude zdarma</p>
            <p>* povinné údaje</p>
            <!-- TODO: Style the form into two column layout, so that each group/fieldset is one column -->

            <form name="new_user_form" action="register" method="post">
                <div>
                    <div class="form-group">
                        <label for="username">*Uživatelské jméno:</label>
                        <input class="form-control" type="text" id="username" name="username"/>
                    </div>
                    <div class="form-group">
                        <label for="pass">*Heslo:</label>
                        <input class="form-control" type="password" id="pass" name="password"/>
                    </div>
                    <div class="form-group">
                        <label for="pass2">*Heslo znovu:</label>
                        <input class="form-control" type="password" id="pass2" name="confirmPwd"/>
                    </div>
					<div class="form-group">
                        <label for="username">Jméno:</label>
                        <input class="form-control" type="text" id="fname" name="fname"/>
                    </div>
                    <div class="form-group">
                        <label for="username">Příjmení:</label>
                        <input class="form-control" type="text" id="lname" name="lname"/>
                    </div>
                    <div class="form-group">
                        <label for="birthdate">Datum narození:</label>
                        <input class="form-control" type="date" id="birthdate" name="birthdate" placeholder="dd/MM/yyyy"/>
                    </div>
                    <%--<div class="form-group">
                        <label for="email">E-mail:</label>
                        <input class="form-control" type="text" id="email" name="email"/>
                    </div> --%>
                    <div>
                    <button class="btn btn-primary" type="submit" value="Potvrdit">Potvrdit</button>
                    <button class="btn btn-default" type="reset" value="Reset">Reset</button>
                	</div>
                </div>
            </form>	
        </div>
        <div class="col-sm-6 col-md-6">
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
