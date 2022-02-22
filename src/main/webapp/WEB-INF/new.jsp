<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book Share</title>
</head>
<body>
	<a href="/home">back to the shelves</a>
	<h1>Add a Book to Your Shelf!</h1>
	<div>
		<form:form action="/create/book" method="post" 
					modelAttribute="newBook">
			<form:input type="hidden" path="poster" value="${thisUser.id }"/>
			<div>
				<label>Title</label>
				<form:input type="text" path="title" />
				<form:errors path="title"/>
			</div>
			<div>
				<label>Author</label>
				<form:input type="text" path="author"/>
				<form:errors path="author"/>
			</div>
			<div>
				<label>My Thoughts</label>
				<form:textarea path="thoughts" cols="30" rows="10"/>
				<form:errors path="thoughts"/>
			</div>
			<button>Submit</button>
		</form:form>
	</div>
</body>
</html>