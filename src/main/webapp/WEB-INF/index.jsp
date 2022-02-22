<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Read Share</title>
</head>
<body>
<h1>Welcome!</h1>
<h3>Join our growing community.</h3>

<div>
	<form:form action = "/register" method = "post" modelAttribute ="newUser">
		<h2>Register</h2>
		<p>
			<form:label path = "userName">User Name</form:label>
			<form:input type="text" path ="userName" />
			<form:errors path="userName"/>
		</p>
		<p>
			<form:label path="email">Email:</form:label>
			<form:input type="text" path = "email"></form:input>	
			<form:errors path="email"/>	
		</p>
		<p>
			<form:label path="password">Password</form:label>
			<form:input type="password" path = "password"></form:input>		
			<form:errors path="password"/>
		</p>
		<p>
			<form:label path="confirm">Confirm PW:</form:label>
			<form:input type="password" path = "confirm"></form:input>
			<form:errors path="confirm"/>		
		</p>
		<button>Register</button>
	</form:form>
</div>
<div>
	<h2>Login</h2>
	<form:form action = "/login" method="post" modelAttribute="newLogin">
		<p>
			<form:label path="email">Email:</form:label>
			<form:input type="text" path = "email"></form:input>		
			<form:errors path="email"/>
		</p>
		<p>
			<form:label path="password">Password</form:label>
			<form:input type="password" path = "password"></form:input>		
			<form:errors path="password"/>
		</p>
		<button>Login</button>
	</form:form>
</div>
</body>
</html>