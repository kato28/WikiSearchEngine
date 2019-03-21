<%@page import="servlet.EjbResult"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>search result</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Latest compiled Ja	vaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

</head>
<body class="m-3">
	<form action="ServletRequest">
		<h1 class="text-center">Search Engine</h1>
		<div class="text-center">
			<img class="rounded float-center" placholder="wikilogo"
				src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRICHytvMRU7HwEY5Ygbmrgr8zkRECoc3I9rlStGN9BzLhD9z3l">
		</div>
		<div class="form-group">
			<div class="input-group input-group-lg icon-addon addon-lg">
				<input type="text" placeholder="Search" name="search" id="schbox"
					class="form-control input-lg"> <i class="icon icon-search"></i>
				<span class="input-group-btn">
					<button name="sendRequest" type="submit" class="btn btn-inverse">Rechercher</button>
				</span>
			</div>
			<input type="hidden" name="currentPage" value="1"> <input
				type="hidden" name="recordsPerPage" value="50">

		</div>

	</form>

	<h2>
		search for
		<% out.println(request.getAttribute("search"));%>
	</h2>

	<div class="form-group">
		<table class="table table-striped table-bordered table-sm">
			<tr>
				<th>#</th>
				<th>Result</th>
				<th>PageRank</th>
			</tr>

			<c:forEach items="${results}" var="result" varStatus="i">
				<tr>
					<td><c:out
							value="${(i.index+1) + ((currentPage-1)*recordsPerPage) }"></c:out></td>
					<td><a href="${result.getUrl()}">${result.getTitle()}</a></td>
					<td><c:out value="${result.getPageRank()}"></c:out></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<nav aria-label="Navigation for pages" class="center">
		<ul class="pagination">
			<c:if test="${currentPage != 1}">
				<li class="page-item"><a class="page-link"
					href="ServletRequest?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&search=${search}">Previous</a>
				</li>
			</c:if>

			<c:forEach begin="1" end="${noOfPages}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<li class="page-item active"><a class="page-link"> ${i} <span
								class="sr-only">(current)</span></a></li>
					</c:when>
					<c:otherwise>
						<c:if test="${i lt 5 }">
							<li class="page-item"><a class="page-link"
								href="ServletRequest?recordsPerPage=${recordsPerPage}&currentPage=${i}&search=${search}">${i}</a>
							</li>
						</c:if>
						<c:if test="${i eq 10 }">
							<li class="disabled"><span>.....</span></li>
							<li class="page-item"><a class="page-link"
								href="ServletRequest?recordsPerPage=${recordsPerPage}&currentPage=${noOfPages-2}&search=${search}">${noOfPages-2}</a>
							</li>
							<li class="page-item"><a class="page-link"
								href="ServletRequest?recordsPerPage=${recordsPerPage}&currentPage=${noOfPages-1}&search=${search}">${noOfPages-1}</a>
							</li>
							<li class="page-item"><a class="page-link"
								href="ServletRequest?recordsPerPage=${recordsPerPage}&currentPage=${noOfPages}&search=${search}">${noOfPages}</a>
							</li>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:if test="${currentPage lt noOfPages}">
				<li class="page-item"><a class="page-link"
					href="ServletRequest?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&search=${search}">Next</a>
				</li>
				<li class="page-item"><a class="page-link"
					href="ServletRequest?recordsPerPage=${recordsPerPage}&currentPage=${noOfPages}&search=${search}">last</a>
				</li>
			</c:if>
		</ul>
	</nav>

	<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>


</body>
</html>