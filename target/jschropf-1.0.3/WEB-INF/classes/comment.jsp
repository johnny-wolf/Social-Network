    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">      
        <title>Comments</title>
    </head>
    <body>
		<% if (session.getAttribute("userId") != null) { %>
			<div class = "facebook-comment-box-reply">
				<h2>Add comment</h2>
        		<form action="createComment" method="post">
            		<input type="hidden" name="postId" value="<%=request.getParameter("postId")%>" />
            		<input type="hidden" name="wallOwnerId" value="<%=request.getParameter("wallOwnerId")%>" />
            		<textarea name="text" rows="5" cols="25"></textarea><br />
            		<input type="submit" value="Submit">
        		</form>
        	</div>
		<% } %>
		<div class = "facebook-comment-box-reply">
        	<h2>Existing Comments:</h2>
        </div>
        <c:forEach var="comment" items="${comments}">
        	<div class = "facebook-comment-box-reply">
            	<div>
                	<img src="ref-material-prototype/img/${comment.commenter.picture}" onerror="this.src='ref-material-prototype/img/default.png'" style="width:75px;height:75px;">
                    <a href="wall?ownerId=${comment.commenter.id}">${comment.commenter.fName} ${comment.commenter.lName}</a>
                </div>
                <div class = "facebook-comment-status-reply">
                	<p>${comment.text}</p>
                </div>
        	</div>
        </c:forEach>
    </body>
</html>