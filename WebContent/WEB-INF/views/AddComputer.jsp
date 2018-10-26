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
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="titleAdd"/></h1>
					<form action="AddComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="name"/></label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="<spring:message code="name"/>" required>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="introduced"/></label> <input
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="discontinued"/></label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="company"/></label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach var="Company" items="${companies}">
										<option value="${Company.id}"><c:out
												value="${Company.name}" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<c:if test="${dateError=='ko'}" var="messageError">
							<div class="alert alert-danger">
				              La date de d�but est sup�rieure � la date de fin.
				                <br/>
				            </div>
						</c:if>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="add"/>" class="btn btn-primary" /> <a
								href="Dashboard?limit=10&page=1" class="btn btn-default"><spring:message code="cancel"/></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>