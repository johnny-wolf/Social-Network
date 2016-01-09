<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
	</head>
	<body>
		<form action="createPost" method="post" role="form" enctype="multipart/form-data" class="facebook-share-box">
                    <ul class="post-types">
                        <li class="post-type">
                            <a class="status" title="" href="#"><i class="icon icon-file"></i> State </a>
                        </li>
                        <li class="post-type">
                            <a class="photos" href="#"><i class="icon icon-camera"></i>Add picture</a>
                        </li>
                    </ul>
                    <div class="share">
                        <div class="arrow"></div>
                        <div class="panel panel-default">
                              <div class="panel-heading"><i class="fa fa-file"></i> State</div>
                              <div class="panel-body">
                                <div class="">
                                	<textarea name="title" cols="1" rows="10" id="status_title" class="form-control message" style="height: 62px; overflow: hidden;" placeholder="Title"></textarea>
                                    <textarea name="message" cols="40" rows="10" id="status_message" class="form-control message" style="height: 62px; overflow: hidden;" placeholder="What's on your mind ?"></textarea> 
                                </div>
                              </div>
                                <div class="panel-footer">
                                        <div class="row">
                                            <div class="col-md-7">
                                                <div class="form-group">
                                                    <select name="privacy" class="form-control privacy-dropdown pull-left input-sm">
                                                        <option value="1" selected="selected">Veřejný</option>
                                                        <option value="2">Já a mí přátelé</option>
                                                        <option value="3">Jen já</option>
                                                    </select>  
                                                    <input type="hidden" name="ownerId" value="<%= request.getParameter("ownerId") %>">
                                                    <input type="hidden" name="userId" value="<%= request.getParameter("userId") %>">                                  
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