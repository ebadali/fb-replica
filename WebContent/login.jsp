<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
  <head>
    <title>Login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  </head>
  <script>
	function clearForms() {
		var i;
		for (i = 0; (i < document.forms.length); i++) {
			document.forms[i].reset();
		}
	}
	function validateForm() {
		var x = document.forms["myForm"]["email"].value;
		if (x == null || x == "") {
			alert("Username must be filled out");
			document.getElementById('email').focus();
			return false;
		}
		var y = document.forms["myForm"]["password"].value;
		if (y == null || y == "") {
			alert("password must be filled out");
			document.getElementById('password').focus();
			return false;
		}
	}
</script>
  <body onLoad="clearForms()" onunload="clearForms()">
    <jsp:include page="header.jsp"/>
    <%= (request.getAttribute("error") == null) ? "" : request.getAttribute("error") %>
    <h2>Login</h2> 

    <form action="login" method="post" onsubmit="return validateForm()">
      <table cellspacing="2" cellpadding="0" border="0">
        <tbody>
          <tr>
            <td>Email:</td>
            <td><input type="text" name="email" size="25"></td>
          </tr>
          <tr>
            <td>Password:</td>
            <td><input type="password" name="password" size="25"></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><input type="submit" value="Submit"></td>
          </tr>
        </tbody>
      </table>
    </form>
<%--     <img alt="" src="${pageContext.request.contextPath}/image/image_1.jpg"> --%>
    
  </body>
</html>

