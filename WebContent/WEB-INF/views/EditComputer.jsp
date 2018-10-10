<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
						<div class="label label-default pull-right">id: ${computer.id}</div>
						<h1>Edit Computer</h1>
						<form action="EditComputer" method="POST">
							<input type="hidden" name="id" value="${computer.id}" id="id"/>
							<!-- TODO: Change this value with the computer id -->
							<fieldset>
								<div class="form-group">
									<label for="computerName">Computer name</label> <input
										type="text" class="form-control" id="computerName" name ="computerName"
										placeholder="Computer name" value ="${computer.name}">
								</div>
								<div class="form-group">
									<label for="introduced">Introduced date</label> <input
										type="date" class="form-control" id="introduced" name ="introduced"
										placeholder="Introduced date" value="${computer.introduced.orElse(null)}">
								</div>
								<div class="form-group">
									<label for="discontinued">Discontinued date</label> <input
										type="date" class="form-control" id="discontinued" name ="discontinued"
										placeholder="Discontinued date" value="${computer.discontinued.orElse(null)}">
								</div>
								<div class="form-group">
									<label for="companyId">Company</label> <select
										class="form-control" id="companyId" name ="companyId">
										<option value="${idCompany}">${computer.companyName.orElse(null)}</option>
										<c:forEach var="Company" items="${companies}">
											<option value="${Company.id}">${Company.name}</option>
										</c:forEach>
									</select>
								</div>
							</fieldset>
							<c:if test="${messageOk=='ok'}">
								<div class="alert alert-success">
					              L'ordinateur a bien été modifié.
					                <br/>
					            </div>
							</c:if>
							
							<c:if test="${dateError=='ko'}" var="messageError">
							<div class="alert alert-danger">
				              La date de début est supérieure à la date de fin.
				                <br/>
				            </div>
						</c:if>
						
							<div class="actions pull-right">
								<input type="submit" value="Edit" class="btn btn-primary">
								or <a href="Dashboard?limit=10&page=1" class="btn btn-default">Cancel</a>
				</div>
				</form>
			</div>
		</div>
		</div>
	</section>
</body>
</html>