<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search form</title>
</head>
<body>
<p> Veuillez entrer votre requête: </p>
<form action="ServletRequest" method="get">
  <input type="text" placeholder="Search.." name="search">
  <input type = "submit" value = "Rechercher"/>
</form>
</body>
</html>