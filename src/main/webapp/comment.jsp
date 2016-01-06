    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comments</title>
    </head>
    <body>
	<% if (session.getAttribute("userId") != null) { %>
		<h2>Add comment</h2>
		<div class = "facebook-comment-box-reply">
        <form action="createComment" method="post">
            <input type="hidden" name="postId" value="<%=request.getParameter("postId")%>" />
            <textarea name="text" rows="5" cols="25"></textarea><br />
            <input type="submit" value="Submit">
        </form>
        </div>
	<% } %>
        <h2>Existing Comments:</h2>
         <c:forEach var="comment" items="${comments}">
         	<div class = "facebook-comment-box-reply">
                    <div>
                    	<img src="./ref-material-prototype/img/${comment.commenter.picture}" alt="./ref-material-prototype/img/person.gif" style="width:75px;height:75px;">
                       <a href="wall?ownerId=${post.poster.id}">${post.poster.fName} ${post.poster.lName}</a>
                    </div>
                    <div class = "facebook-comment-status-reply">
                    	<p>${comment.text}</p>
                    </div>
                    <div>
                    	<a href"">To se mi líbí</a> <a>Sdílet</a>
                    </div>
                    <hr />
        	</div>
        </c:forEach>
        <!--<table cellspacing="2" cellpadding="0" border="0">
            <tbody>
                <c:forEach var="comment" items="${comments}">
                    <tr>
                        <td>
                            <img src="./ref-material-prototype/img/${comment.commenter.picture}" height="100"/><br />
                            <i>${comment.commenter.fName} ${comment.commenter.lName}</i>
                        </td>
                        <td>
                            <p style="font-size:0.9em;">${comment.text}</p>
                            <br /><br />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table> -->
    </body>
</html>