<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Imobiliária - Painel</title>
        <jsp:include page="../../includes/header.jsp" />
    </head>
    <body>
        <jsp:include page="../../includes/navbar.jsp" />
        <div class="container">
            <h1>Painel - Imóveis</h1>

            <%--@elvariable id="error" type="java.lang.String"--%>
            <c:if test="${error != null}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <%--@elvariable id="success" type="java.lang.String"--%>
            <c:if test="${success != null}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <form method="post" action="/<c:url value="panel/property/delete"/>">
                <a href="<c:url value="panel/property/form"/>" class="btn btn-primary" title="Novo">Novo</a>
                <button type="submit" class="btn btn-danger">Excluir selecionados</button>

                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th width="40"></th>
                        <th width="100" class="text-center">Cód</th>
                        <th width="200">Título</th>
                        <th>Descrição</th>
                        <th width="150" class="text-right">Valor</th>
                        <th width="50" class="text-center">Editar</th>
                    </tr>
                    </thead>
                    <tbody class="table-data">
                        <%--@elvariable id="properties" type="java.util.List<com.fred.domain.PropertyDomain>"--%>
                        <c:if test="${properties == null}">
                            <tr>
                                <td colspan="4" class="text-center">Nenhum imóvel cadastrado</td>
                            </tr>
                        </c:if>
                        <fmt:setLocale value="pt-BR" />
                        <c:forEach items="${properties}" var="p">
                            <tr>
                                <td class="text-center"><input type="checkbox" name="id[]" value="${p.id}"></td>
                                <td class="text-center">${p.code}</td>
                                <td>${p.title}</td>
                                <td>${p.description}</td>
                                <td class="text-right"><fmt:formatNumber value="${p.value}" minFractionDigits="2" type="currency"/></td>
                                <td class="text-center">
                                    <a href="<c:url value="panel/property/form"/>?id=${p.id}" class="btn btn-default btn-xs" title="Editar">
                                        <i class="glyphicon glyphicon-edit"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
        <jsp:include page="../../includes/footer.jsp" />
    </body>
</html>