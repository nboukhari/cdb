<!DOCTYPE html>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="?limit=10&page=1"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<c:if test="${messageCreate=='ok'}" var="messageCreate">
				<c:out value="L'ordinateur a bien été crée." />
				<br />
			</c:if>
			<h1 id="homeTitle">${nbComp} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="AddComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="DeleteComputer?limit=${limit}&page=${page}" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="Computer" items="${computers}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${Computer.id}"></td>
							<td><a href="EditComputer?id=${Computer.id}" onclick=""><c:out
										value="${Computer.name}" /></a></td>
							<td><c:out value="${Computer.introduced.orElse(null)}" /></td>
							<td><c:out value="${Computer.discontinued.orElse(null)}" /></td>
							<td><c:out value="${Computer.companyName.orElse(null)}" /></td>

						</tr>

					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

 

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				
				<c:if test="${nbPage!=1}">
					<li><a href="?limit=${limit}&page=1">
						<span aria-hidden="true">First</span>
					</a></li>
				</c:if>
				
				<c:if test="${nbPageMinusOne>0}">
					<li><a href="?limit=${limit}&page=${nbPageMinusOne}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>
				
				<c:if test="${nbPageMinusTwo>0}">
					<li><a href="?limit=${limit}&page=${nbPageMinusTwo}">${nbPageMinusTwo}</a></li>
				</c:if>
				
				<c:if test="${nbPageMinusOne>0}">
					<li><a href="?limit=${limit}&page=${nbPageMinusOne}">${nbPageMinusOne}</a></li>
				</c:if>
				
				<li><a style="font-weight: bold; color: red;" href="?limit=${limit}&page=${nbPage}">${nbPage}</a></li>
				
				<c:if test="${nbPageMoreOne<=nbPages}">
					<li><a href="?limit=${limit}&page=${nbPageMoreOne}">${nbPageMoreOne}</a></li>
				</c:if>
				
				<c:if test="${nbPageMoreTwo<=nbPages}">
				<li><a href="?limit=${limit}&page=${nbPageMoreTwo}">${nbPageMoreTwo}</a></li>
				</c:if>
				
				<c:if test="${nbPageMoreOne<=nbPages}">
				<li><a href="?limit=${limit}&page=${nbPageMoreOne}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
				</a></li>
				</c:if>
				<c:if test="${nbPage!=nbPages}">
					<li><a href="?limit=${limit}&page=${nbPages}">
						<span aria-hidden="true">Last</span>
					</a></li>
				</c:if>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="?limit=10&page=1"><button type="button" class="btn btn-default">10</button></a> 
				<a href="?limit=50&page=1"><button type="button" class="btn btn-default">50</button></a> 
				<a href="?limit=100&page=1"><button type="button" class="btn btn-default">100</button></a>
			</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>