<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Imobiliária - Login</title>
        <jsp:include page="../includes/header.jsp" />
    </head>
    <body>
        <jsp:include page="../includes/navbar.jsp" />
        <div class="container">
            <form action="/<c:url value="panel/authenticate"/>" method="post" class="form-signin">

                <%--@elvariable id="error" type="java.lang.String"--%>
                <c:if test="${error != null}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <h2 class="form-signin-heading">Login</h2>
                <label for="email" class="sr-only">E-mail</label>
                <input type="email" id="email" name="email" class="form-control" placeholder="E-mail" required autofocus>
                <label for="password" class="sr-only">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>

                <button class="btn btn-lg btn-primary btn-block" type="submit">OK</button>
            </form>
        </div>
        <jsp:include page="../includes/footer.jsp" />
    </body>
</html>