<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
	</head>
	<body>
		<form action="createPost" method="post" role="form" enctype="multipart/form-data" class="facebook-share-box">
        	<div class="share">
            	<div class="panel panel-default">
                	<div class="panel-heading"><i class="fa fa-file"></i> State</div>
                    	<div class="panel-body">
                        	<textarea name="title" cols="1" rows="10" id="status_title" class="form-control message" style="height: 62px; overflow: hidden;" placeholder="Title"></textarea>
                            <textarea name="message" cols="40" rows="10" id="status_message" class="form-control message" style="height: 62px; overflow: hidden;" placeholder="What's on your mind ?"></textarea> 
                        </div>
                        <div class="panel-footer">
                        	<div class="row">
                            	<div class="col-md-7">
                                	<div class="form-group">
                                    	<input type="hidden" name="ownerId" value="<%= request.getParameter("ownerId") %>">
                                        <input type="hidden" name="userId" value="${sessionScope.userId}">                                  
                                        <input type="submit" name="submit" value="Přidat příspěvek" class="btn btn-primary">                               
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </form>
	</body>
</html>