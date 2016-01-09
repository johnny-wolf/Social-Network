<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<nav class="navbar navbar-default">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#mynavigation"
                        aria-expanded="false" aria-controls="mynavigation">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">ITNet</a>
            </div>
            <div class="collapse navbar-collapse" id="mynavigation">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#help">Pomoc</a></li>
                    <li>
                        <form class="navbar-form navbar-right" method="post" role="form" action="login">
                        <div class="form-group">
                            <input type="text" class="form-control" name="username" placeholder="Username">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" name="password" placeholder="Password">
                        </div>
                        <button type="submit" class="btn btn-default">Sign In</button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</body>
</html>