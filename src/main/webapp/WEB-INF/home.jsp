<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Read Share</title>
</head>
<body>
	<div>
		<h1>Welcome ${thisUser.getUserName()}</h1>
		<p>Books from everyone's shelves:</p>
		<a href="/new">+ Add a book to my shelf!</a>
	</div>
	<a href="/logout">Logout</a>
	
	<div>
		<table>
			<tr>
				<th>ID</th>
				<th>Title</th>
				<th>Author Name</th>
				<th>Posted By</th>
			</tr>
			<c:forEach items = "${allBooks}" var="oneBook">
				<tr>
					<td><c:out value ="${oneBook.getId()}"/></td>
					<td><a href="/show/${oneBook.id }"><c:out value ="${oneBook.getTitle()}"/></a></td>
					<td><c:out value ="${oneBook.getAuthor()}"/></td>
					<td><c:out value ="${oneBook.getPoster().getUserName()}"/></td>
				</tr>
			</c:forEach>
			
		</table>
	</div>
</body>
</html>