<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
				<div class="actions pull-right" role="group">
						<a class="navbar-brand" href="?lang=en">
							<button	type="button" class="btn btn-default">
								<spring:message code="eng"/>
							</button>
						</a>
						<a class="navbar-brand" href="?lang=fr">
							<button	type="button" class="btn btn-default">
								<spring:message	code="fr"/>
							</button>
						</a>
				</div>
		</div>
		
	</header>

	<section id="main">
		<div class="container">
			<c:if test="${messageCreate=='ok'}" var="messageCreate">
				<div class="alert alert-success">
					<spring:message code="addMessage"/>
				</div>
			</c:if>
			<h1 id="homeTitle">${nbComp}
				<spring:message code="comp"/>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<spring:message code="search"/>" /> <input
							type="submit" id="searchsubmit" value="<spring:message code="filter"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="AddComputer">
						<spring:message	code="add"/>
					</a> 
					<a class="btn btn-default"	id="editComputer" href="#" onclick="$.fn.toggleEditMode();">
							<spring:message	code="delete"/>
					</a>
				</div>
			</div>
		</div>

		<form id="deleteForm"
			action="DeleteComputer?limit=${limit}&page=${page}" method="POST">
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
						<th><spring:message code="name"/></th>
						<th><spring:message code="introduced"/></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="discontinued"/></th>
						<!-- Table header for Company -->
						<th><spring:message code="company"/></th>

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
							<td><c:out value="${Computer.introduced}" /></td>
							<td><c:out value="${Computer.discontinued}" /></td>
							<td><c:out value="${Computer.company.name}" /></td>

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
					<li><a href="?search=${search}&limit=${limit}&page=1"> <span
							aria-hidden="true"><spring:message code="first"/></span>
					</a></li>
				</c:if>

				<c:if test="${nbPageMinusOne>0}">
					<li><a
						href="?search=${search}&limit=${limit}&page=${nbPageMinusOne}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>

				<c:if test="${nbPageMinusTwo>0}">
					<li><a
						href="?search=${search}&limit=${limit}&page=${nbPageMinusTwo}">${nbPageMinusTwo}</a></li>
				</c:if>

				<c:if test="${nbPageMinusOne>0}">
					<li><a
						href="?search=${search}&limit=${limit}&page=${nbPageMinusOne}">${nbPageMinusOne}</a></li>
				</c:if>

				<li><a style="font-weight: bold; color: red;"
					href="?limit=${limit}&page=${nbPage}">${nbPage}</a></li>

				<c:if test="${nbPageMoreOne<=nbPages}">
					<li><a
						href="?search=${search}&limit=${limit}&page=${nbPageMoreOne}">${nbPageMoreOne}</a></li>
				</c:if>

				<c:if test="${nbPageMoreTwo<=nbPages}">
					<li><a
						href="?search=${search}&limit=${limit}&page=${nbPageMoreTwo}">${nbPageMoreTwo}</a></li>
				</c:if>

				<c:if test="${nbPageMoreOne<=nbPages}">
					<li><a
						href="?search=${search}&limit=${limit}&page=${nbPageMoreOne}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
				<c:if test="${nbPage!=nbPages}">
					<li><a href="?search=${search}&limit=${limit}&page=${nbPages}">
							<span aria-hidden="true"><spring:message code="last"/></span>
					</a></li>
				</c:if>
			</ul>
		
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="?search=${search}&limit=10&page=1"><button
						type="button" class="btn btn-default">10</button></a> <a
					href="?search=${search}&limit=50&page=1"><button type="button"
						class="btn btn-default">50</button></a> <a
					href="?search=${search}&limit=100&page=1"><button type="button"
						class="btn btn-default">100</button></a>
			</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>