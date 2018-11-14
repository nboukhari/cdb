<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="label.computerDatabase" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
   <header class="navbar navbar-inverse navbar-fixed-top">

   </header>

   <section id="main">
       <div class="container">
           <div class="row">
               <div class="col-xs-8 col-xs-offset-2 box">
                   <h1><fmt:message key="label.loginAuthentification" /></h1>
                   	<c:if test="${displayAlert}">
<div class="alert alert-danger" role="alert">
 	<b><fmt:message key="${alertKey}" /></b>
</div>
</c:if>
                   <form action="login" method="POST">
                       <fieldset>
                           <div class="form-group">
                               <label for="userIdentifier"><fmt:message key="label.loginIdentifer" /></label>
                               <input type="text" class="form-control" id="userIdentifier" name="userIdentifier" placeholder="<fmt:message key="label.loginIdentifer" />"  value="${identifier}">
                           </div>
                           <div class="form-group">
                               <label for="userPassword"><fmt:message key="label.loginPassword" /></label>
                               <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="<fmt:message key="label.loginPassword" />" value="${password}">
                           </div>                
                       </fieldset>
                       <div class="actions pull-right">
                           <input type="submit" value="<fmt:message key="label.loginInscriptionButton" />" name="addUser" class="btn btn-primary">
                            <fmt:message key="label.or" />
                            <input type="submit" value="<fmt:message key="label.loginConnectionButton" />" name="checkUser" class="btn btn-primary">

                       </div>
                   </form>
               </div>
           </div>
       </div>
   </section>
</body>
</html>