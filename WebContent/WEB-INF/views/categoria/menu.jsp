<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/" var="raiz" />
<c:url value="/assets" var="assets" />
<c:url value="/app/adm/categoria" var="urlEditarCategoria" />
<c:url value="/app/adm/categoria/salvar" var="urlSalvarCategoria" />

<!DOCTYPE html>
<html>
<head>
	<c:import url="../templates/head.jsp"/>
</head>
<body>
	<c:import url="../templates/header.jsp"/>
	<main>
		<h1>Categorias de Ocorr�ncia</h1>
		<section id="sectionCategorias" class="container">
		<div class="flex-grid">
			<div class="row">
				<div class="col flex-1">
					<h2>Nova Categoria</h2>
					<form:form action="${urlSalvarCategoria}" method="post" modelAttribute="categoria">
						<form:hidden path="id"/>
						<div class="flex-grid">
							<div class="row">
								<div class="col flex-1">
									<label>
										Nome
										<form:input path="nome" type="text"/>
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<button type="submit" class="btn">SALVAR</button>
								</div>
							</div>
						</div>
					</form:form>
				</div>
				<div class="col flex-1">
					<h2>Categorias Cadastradas</h2>
					<%-- Tabela de ocorr�ncias --%>
					<table id="tabelaOcorrencias" class="table container read-container ma-t-l">
						<thead>
							<tr>
								<th>Categorias</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${categorias}" var="categoria">
								<tr>
									<td>
										<a href="${urlEditarCategoria}?id=${categoria.id}">${categoria.nome}</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		</section>
	</main>
	<c:import url="../templates/botoesFlutuantes.jsp" />
</body>
</html>