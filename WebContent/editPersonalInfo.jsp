<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
  <head>
    <title>Edit Personal Information</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  </head>
  <body>
    <jsp:include page="header.jsp"/>
    <h2>Edit Personal Information</h2>
    <form action="update_personal_info" method="post" enctype="multipart/form-data">
      <table cellspacing="2" cellpadding="0" border="0">
        <tbody>
          <tr>
            <td>First Name:</td>
            <td><input type="text" name="firstName" size="25" value="${person.firstName}"/></td>
          </tr>
          <tr>
            <td>Last Name:</td>
            <td><input type="text" name="lastName" size="25"  value="${person.lastName}"/></td>
          </tr>
          <tr>
            <td>Sex:</td>
            <td>
              <select name="sex">
	        <option value="male" ${person.sex ? "SELECTED" : "" }>Male</option>
	        <option value="female" ${person.sex ? "" : "SELECTED" } >Female</option>
	      </select>
	    </td>
          </tr>
          <tr>
            <td>Date of Birth:</td>
            <td>
              <select name="dayOfBirth">
		<% for(int i = 1; i < 32; i++) { %>
	        <option value="<%=i%>"><%=i%></option>
                <% } %>
		<!--
		<option value="35" SELECTED>35</option>
                -->
	      </select>

              <select name="monthOfBirth">
		<% for(int i = 1; i < 13; i++) { %>
	        <option value="<%=i%>"><%=i%></option>
                <% } %>
	      </select>

              <select name="yearOfBirth">
		<% for(int i = 1900; i < 1996; i++) { %>
	        <option value="<%=i%>"><%=i%></option>
                <% } %>
	      </select>

            </td>
          </tr>
          <tr>
            <td>Email:</td>
            <td><input type="text" name="email" size="25" value="${person.email}"/></td>
          </tr>
          <tr>
            <td>Place:</td>
            <td><input type="text" name="place" size="25" value="${person.place}"/></td>
          </tr>
          <tr>
            <td>Website:</td>
            <td><input type="text" name="website" size="25" value="${person.website}"/></td>
          </tr>
          <tr>
            <td>Education:</td>
            <td><input type="text" name="education" size="25" value="${person.education}"/></td>
          </tr>
          <tr>
            <td>Occupation:</td>
            <td><input type="text" name="occupation" size="25" value="${person.occupation}"/></td>
          </tr>
          <tr>
            <td>Employment:</td>
            <td><input type="text" name="employment" size="25" value="${person.employment}"/></td>
          </tr>
          <tr>
            <td>Photo:</td>
            <td><input type="file" name="photo" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><input type="submit" value="Submit" /></td>
          </tr>
        </tbody>
      </table>
    </form>
  </body>
</html>



