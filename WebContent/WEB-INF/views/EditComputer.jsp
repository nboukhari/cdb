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
			<a class="navbar-brand" href="Dashboard?limit=10&page=1"> Application - Computer
				Database </a>
				<div class="actions pull-right" role="group">
						<a class="navbar-brand" href="?lang=en&id=${id}">
							<button	type="button" class="btn btn-default">
								<spring:message code="eng"/>
							</button>
						</a>
						<a class="navbar-brand" href="?lang=fr&id=${id}">
							<button	type="button" class="btn btn-default">
								<spring:message	code="fr"/>
							</button>
						</a>
				</div>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
						<div class="label label-default pull-right">id: ${computer.id}</div>
						<h1><spring:message code="titleEdit"/></h1>
						<form action="EditComputer" method="POST">
							<input type="hidden" name="id" value="${computer.id}" id="id"/>
							<!-- TODO: Change this value with the computer id -->
							<fieldset>
								<div class="form-group">
									<label for="computerName"><spring:message code="name"/></label> <input
										type="text" class="form-control" id="computerName" name ="computerName"
										placeholder="<spring:message code="name"/>" value ="${computer.name}">
								</div>
								<div class="form-group">
									<label for="introduced"><spring:message code="introduced"/></label> <input
										type="date" class="form-control" id="introduced" name ="introduced"
										placeholder="Introduced date" value="${computer.introduced}">
								</div>
								<div class="form-group">
									<label for="discontinued"><spring:message code="discontinued"/></label> <input
										type="date" class="form-control" id="discontinued" name ="discontinued"
										placeholder="Discontinued date" value="${computer.discontinued}">
								</div>
								<div class="form-group">
									<label for="companyId"><spring:message code="company"/></label> <select
										class="form-control" id="companyId" name ="companyId">
										<option value="${computer.company.id}">${computer.company.name}</option>
										<c:forEach var="Company" items="${companies}">
											
										<c:if test="${computer.company.id != Company.id}">
										<option value="${Company.id}">${Company.name}</option>
										</c:if>
										</c:forEach>
									</select>
								</div>
							</fieldset>
							<c:if test="${messageOk=='ok'}">
								<div class="alert alert-success">
					            	<spring:message code="editMessageOk"/>
					                <br/>
					            </div>
							</c:if>
							
							<c:if test="${dateError=='ko'}" var="messageError">
							<div class="alert alert-danger">
				              <spring:message code="dateErrorMessage"/>
				                <br/>
				            </div>
						</c:if>
						
							<div class="actions pull-right">
								<input type="submit" value="<spring:message code="edit"/>" class="btn btn-primary">
								<a href="Dashboard?limit=10&page=1" class="btn btn-default"><spring:message code="cancel"/></a>
				</div>
				</form>
			</div>
		</div>
		</div>
	</section>
</body>
</html>