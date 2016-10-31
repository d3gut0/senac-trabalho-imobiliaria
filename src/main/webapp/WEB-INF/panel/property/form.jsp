<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Imobiliária - Imóvel</title>
        <jsp:include page="../../includes/header.jsp" />
    </head>
    <body>
        <jsp:include page="../../includes/navbar.jsp" />
        <div class="container">
            <h1>Imóvel</h1>

            <%--@elvariable id="error" type="java.lang.String"--%>
            <c:if test="${error != null}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    ${error}
                </div>
            </c:if>
            <%--@elvariable id="success" type="java.lang.String"--%>
            <c:if test="${success != null}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    ${success}
                </div>
            </c:if>

            <%--@elvariable id="property" type="com.fred.domain.PropertyDomain"--%>
            <form enctype="multipart/form-data" method="post" action="/<c:url value="panel/property/form"/>">
                <div class="row">
                    <div class="form-group col-sm-4 col-md-3">
                        <label for="code">Código</label>
                        <input type="text" id="code" name="code" class="form-control" maxlength="10" required value="${property.code}">
                    </div>
                    <div class="form-group col-sm-8 col-md-9">
                        <label for="title">Título</label>
                        <input type="text" id="title" name="title" class="form-control" maxlength="100" required value="${property.title}">
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-xs-12">
                        <label for="description">Descrição</label>
                        <input type="text" id="description" name="description" class="form-control" maxlength="200" required value="${property.description}">
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-sm-6">
                        <label for="image">Imagem</label>
                        <c:if test="${property != null}">
                        <div class="input-group">
                        </c:if>
                            <input type="file" id="image" name="image" class="form-control">
                            <c:if test="${property != null}">
                            <span class="input-group-btn">
                                <a href="<c:url value="/property/image"/>?id=${property.id}" class="btn btn-default" target="_blank">Ver imagem</a>
                            </span>
                            </c:if>
                        <c:if test="${property != null}">
                        </div>
                        </c:if>
                    </div>
                    <div class="form-group col-sm-6">
                        <label for="value">Valor</label>
                        <input type="number" id="value" name="value" class="form-control" step="0.01" required value="${property.value}">
                    </div>
                </div>
                <input type="hidden" id="id" name="id" value="${property.id}">
                <button type="submit" name="save" class="btn btn-success btn-save">Salvar</button>
                <a href="/<c:url value="panel"/>" class="btn btn-default" title="Voltar">Voltar</a>
            </form>

        </div>
        <jsp:include page="../../includes/footer.jsp" />
    </body>
</html>