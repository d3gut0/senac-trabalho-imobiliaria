<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Imobiliária</title>
        <jsp:include page="includes/header.jsp" />
    </head>
    <body>
        <jsp:include page="includes/navbar.jsp" />
        <div class="container">
            <h1>Imóveis</h1>
            <div class="well form-filter clearfix">
                <form action="" method="get" class="form form-inline" role="form">
                    <div class="row">
                        <div class="form-group col-xs-12 col-sm-6 col-md-6">
                            <label for="search" class="sr-only">Busca</label>
                            <input type="text" class="form-control" id="search" name="search" placeholder="Informe a descrição para pesquisar...">
                        </div>
                        <div class="form-group col-xs-12 col-md-3">
                            <label for="order" class="sr-only">Ordenar</label>
                            <%--@elvariable id="order" type="java.lang.String"--%>
                            <select id="order" name="order" class="form-control">
                                <option value="lower-value">Ordenar por menor valor</option>
                                <option value="highest-value"${order == "highest-value" ? 'selected="selected"' : ''}>Ordenar por maior valor</option>
                            </select>
                        </div>
                        <div class="form-group col-xs-12 col-md-3">
                            <button type="submit" class="btn btn-default">OK</button>
                            <a href="<c:url value="/"/> " class="btn btn-default">Limpar</a>
                        </div>
                    </div>
                </form>
            </div>
            <section class="row properties">
                <%--@elvariable id="error" type="java.lang.String"--%>
                <c:if test="${error != null}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <%--@elvariable id="properties" type="java.util.List<com.fred.domain.PropertyDomain>"--%>
                <c:if test="${properties == null}">
                    <p class="text-center">Nenhum imóvel disponível</p>
                </c:if>
                <fmt:setLocale value="pt-BR" />
                <c:forEach items="${properties}" var="p">
                <div class="col-xs-6 col-sm-4 col-md-3 property">
                    <!--img src="http://placehold.it/300x200" width="300" height="200" alt="" class="img-responsive"-->
                    <img src="<c:url value="/property/image"/>?id=${p.id}&amp;width=300&amp;height=200" width="300" height="200" alt="" class="img-responsive">
                    <p class="title">${p.title}</p>
                    <p class="id">Cód: ${p.code}</p>
                    <div class="description">${p.description}</div>
                    <p class="value">Valor: <strong><fmt:formatNumber value="${p.value}" minFractionDigits="2" type="currency"/></strong></p>
                </div>
                </c:forEach>
            </section>
        </div>
        <jsp:include page="includes/footer.jsp" />
    </body>
</html>