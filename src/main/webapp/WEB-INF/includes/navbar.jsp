<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default navbar-inverse">
    <div class="container">
        <ul class="nav navbar-nav">
            <li class="active"><a href="<c:url value="/"/>" title="Home">Home</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/<c:url value="panel"/>" title="Painel administrativo">Painel</a></li>
            <c:if test="${sessionScope.user != null}">
            <li>
                <a href="/<c:url value="panel/logout"/>" title="Sair (${sessionScope.user.name})">Sair</a>
            </li>
            </c:if>
        </ul>
    </div>
</nav>