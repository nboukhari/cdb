<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
            <a class="navbar-brand" href="Dashboard?limit=10&page=1"> Application - Computer Database </a>
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
            <div class="alert alert-danger">
                 <spring:message code="error404"/>
                <br/>
                <!-- stacktrace -->
            </div>
        </div>
    </section>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/dashboard.js"></script>

</body>
</html>