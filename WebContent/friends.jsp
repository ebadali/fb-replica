<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
  <head>
    <title>Friends</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  </head>
  <body>
    <jsp:include page="header.jsp"/>
    <h2>Friends</h2>
    <br />

    <table>
    <c:forEach var="person" items="${people}">
	    <tr>
		    <td>
			    <a href="profile?personId=${person.id}"><img src="${person.picture}" height="64"/></a>
		    </td>
		    <td>
			    <a href="profile?personId=${person.id}">${person.firstName} ${person.lastName}</a><br />
		    </td>
		    <% if (request.getParameter("personId") == null) { %>
			    
		      <td>
		  	    &nbsp;<a href="remove_friend?friendId=${person.id}">Remove from friends</a><br />
		      </td>
		    <% } %>
	    </tr>
    </c:forEach>
      </table>
  </body>
</html>