<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search form</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" ></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" ></script>

</head>
<body>
<form action="ServletRequest" >
<h1 class="text-center"> Search Engine </h1>
<div class="text-center">
<img class="rounded float-center" placholder="wikilogo" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRICHytvMRU7HwEY5Ygbmrgr8zkRECoc3I9rlStGN9BzLhD9z3l">
</div>
<div class="form-group">
    <div class="input-group input-group-lg icon-addon addon-lg">
       
          <input class="form-control form-control-sm mr-3 w-75"  placeholder="Search" name="search" id="schbox" type="text" aria-label="Search">
  			<i class="fas fa-search" aria-hidden="true"></i>
        <span class="input-group-btn">
            <button name="sendRequest" type="submit" class="btn btn-inverse">Rechercher</button>
        </span>
    </div>
     
     <input type="hidden" name="currentPage" value="1">
     <input type="hidden" name="recordsPerPage" value="50">
   
</div>

</form>

</body>
</html>