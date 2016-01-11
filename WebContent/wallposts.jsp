
<%@page import="entity.Person"%>
<%@page import="dao.UserDAO"%>
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
	<br />
	<br />



	<%
		try {
			String altPath = UserDAO._instance.localPath+"image\not_found.gif";
			List<Post> listOfPost = (ArrayList<Post>) request.getAttribute("posts");//.getAttribute("posts");
			String video = "";
			for (Post post : listOfPost) {
				out.print("<tr>");
				out.print("		<td>");
				
				Person person  = post.getPerson();
				
				if(person != null){
					System.out.println("Person");
					
					out.print("<a href='profile?ownerId="+person.getId()+"'><img src=" + person.getPicture() + "  width='50' height='50' alt="+altPath+" />    "
							+person.getFirstName()+" " + person.getLastName()+"  </a>");
					//out.print("  <a href=''>"+person.getFirstName()+" " + person.getLastName()+"</a>");
				}
				CFile file = post.getFile();
				int likes = post.getLikers().size();
				if (file != null) {
					out.print("<h3>" + file.getTitle() + "</h3>");
					out.print("<h3>" + file.getText() + "</h3>");
					if (file.getImage() != null && file.getImage() != "")
						
						out.print("<a href=''><img src=" + file.getImage() + " width='200' height='200' alt="+altPath+" /></a>");
					if (file.getVideos() != null && file.getVideos() != ""){
						video = file.getVideos();
						if(video.contains("watch?v="))
						{
							video = file.getVideos().replace("watch?v=", "v/");
						}
						out.print("<iframe title='YouTube video player' class='youtube-player'"
								+ "type='text/html' width='640' height='390' src='"
								+ video
								+ "' frameborder='0' allowFullScreen></iframe>");
							//out.print("<a href=''><img src="+post.getVideos()+" width='200' height='200' /></a>");
					}
					//out.print("<a href='?vid=" + i + "&title=" + t + "' >" + t + "</a> ");
				}
				
				out.println("<p>");
				
				out.println("</p> <br />");

				out.println("<p>");
				out.println("<a href='wall?postId="+String.valueOf(post.getId())+"&ownerId="+post.getOwnerId()+"&share=false'  id='myLikes' onclick='MyFunction("+String.valueOf(post.getId())+");'>Like</a>");										
				out.println(likes );
				out.println("   <a href='wall?postId="+String.valueOf(post.getId())+"&ownerId="+post.getOwnerId()+"&share=true'  id='myLikes' >Share</a>");										
				
				out.println("</p> <br />");
				out.println("<a>-----------------------------------------------------------------</a>");
				out.println("		</td>");

				out.println("<br />");
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
