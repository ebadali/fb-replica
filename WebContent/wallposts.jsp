
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="entity.CFile"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Post"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Wall</title>
</head>
<body>

	<jsp:include page="header.jsp" />
	<h2>Wall</h2>
	<a href="wall?ownerId=${wallOwnerId}">Chronological</a> |
	<a href="wall?ownerId=${wallOwnerId}&filter=popular">Popular</a>
	<br />
	<br />
	<a href="addPost.jsp?ownerId=${wallOwnerId}">Post on this wall</a>
	<br />
	<br />



	<%
		try {
			List<Post> listOfPost = (ArrayList<Post>) request.getAttribute("posts");//.getAttribute("posts");
			for (Post post : listOfPost) {
				out.print("<tr>");
				out.print("		<td>");

				CFile file = post.getFile();
				int likes = post.getLikers().size();
				if (file != null) {
					out.print("<h3>" + file.getTitle() + "</h3>");
					out.print("<h3>" + file.getText() + "</h3>");
					if (file.getImage() != null && file.getImage() != "")
						out.print("<a href=''><img src=" + file.getImage() + " width='200' height='200' /></a>");
					if (file.getVideos() != null && file.getVideos() != "")
						out.print("<iframe title='YouTube video player' class='youtube-player'"
								+ "type='text/html' width='640' height='390' src='"
								+ file.getVideos().replace("watch?v=", "v/")
								+ "' frameborder='0' allowFullScreen></iframe>");
							//out.print("<a href=''><img src="+post.getVideos()+" width='200' height='200' /></a>");

					//out.print("<a href='?vid=" + i + "&title=" + t + "' >" + t + "</a> ");
				}
				
				out.println("<p>");
				out.println("<i><a href='comments?postId=" + post.getId() + "'>View/Add Comments</a></i>");
				out.println("</p> <br />");

				out.println("<p>");
				out.println("<i><a href='wall?postId="+String.valueOf(post.getId())+"'  id='myLikes' onclick='MyFunction("+String.valueOf(post.getId())+");'>Like</a></i>");
										
				out.println(likes + " Likes ");
				out.println("</p> <br />");

				out.println("		</td>");
				out.println("</tr>");
				%>
				<script language="javascript">
					function MyFunction(id) {
						
						//alert("Liked successful "+ id);
					}
				</script>
				<%

			}
		} catch (Exception ex) {
		}
	%>
</body>
</html>
