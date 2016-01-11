<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entity.Person"%>
<html>
<head>
<title>Everybody</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<jsp:include page="header.jsp" />
	<h2>Everybody</h2>
	
	<br />
	<br />


	<%
		out.print("<table>");
		try {
			List<Person> listOfPost = (ArrayList<Person>) request.getAttribute("people");//.getAttribute("posts");
			for (Person person : listOfPost) {
				out.print("<tr>");
				out.print("<td>");
				
				out.print("<a href='profile?personId=" + person.getId()
						+ "' ><img src='"+person.getPicture()+"' width='128' height='128' /></a>");

				out.print("</td>");

				out.print("<td>");
				out.print("<a href='profile?ownerId=" + person.getId() + "' >" + person.getFirstName() + " "
						+ person.getLastName() + "</a>");
				out.print("</td>");

				// their requests are pending.
				out.print("<td>");
				System.out.println(person.getId()  +"   ,  "+person.getStatus());
				if (person.getStatus() == 1) {
					out.println("<p>Friend</p>");
				} else if (person.getStatus() == 0) {
					out.println("<p>Pending</p>");

				} else if (person.getStatus() == -1) {
					out.print("<a href='all_people?friendId=" + person.getId() + "' >Send a Friend Request</a>");

				}
				out.print("</td>");

				out.println("</tr>");
				// 				out.println("<p>");
				// 				out.println("<i><a href='comments?postId=" + post.getId() + "'>View/Add Comments</a></i>");
				// 				out.println("</p> <br />");

				// 				out.println("<p>");
				// 				out.println("<i><a href='wall?postId="+String.valueOf(post.getId())+"'  id='myLikes' onclick='MyFunction("+String.valueOf(post.getId())+");'>Like</a></i>");

				// 				out.println(likes + " Likes ");
				// 				out.println("</p> <br />");

			}
		} catch (Exception ex) {
		}
		out.print("</table>");
	%>

	<script language="javascript">
		function MyFunction(id) {

			//alert("Liked successful "+ id);
		}
	</script>

</body>
</html>




