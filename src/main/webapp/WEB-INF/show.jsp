<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><c:out value= "${book.title}"/></title>
</head>
<body>
	<a href="/home">back to the shelves</a>
	<h1><c:out value= "${book.title}"/></h1>
	<c:choose>
		<c:when test="${book.poster.id == thisUser.id }">
			<h3>You read <c:out value= "${book.title}"/> by <c:out value= "${book.author}"/></h3>
			<p>Here are your thoughts:</p>
		</c:when>
		<c:otherwise>
			<h3><c:out value= "${book.poster.userName}"/> read <c:out value= "${book.title}"/> by <c:out value= "${book.author}"/></h3>
			<p>Here are <c:out value= "${book.poster.userName}"/>'s thoughts</p>
		</c:otherwise>
	</c:choose>
	<p><c:out value= "${book.thoughts}"/></p>
	
	<c:choose>
		<c:when test="${book.poster.id == thisUser.id }">
			<div>
				<a href="/edit/${book.id}"><button>Edit</button></a>
			</div>
			<div>
				<form action="/delete/${book.id}" method="post">
    				<input type="hidden" name="_method" value="delete">
    				<input type="submit" value="Delete">
				</form>
			</div>
			
		</c:when>
	</c:choose>
</body>
</html>