<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
	</head>
	<body>
		<c:if test="${not empty sessionScope.user}">
			<jsp:include page="wall?<%= session.getAttribute("ownerId") %>"/>
		</c:if>
	</body>
</html>