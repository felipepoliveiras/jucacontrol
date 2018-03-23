<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url value="/" var="raiz" />
<c:url value="/assets" var="assets" />
<c:url value="/app/ocorrencia" var="urlVisualizarOcorrencia" />
<c:url value="/app/ocorrencia/nova" var="urlNovaOcorrencia" />
<c:url value="/app/ocorrencia/assumir" var="urlAssumirOcorrencia" />
<c:url value="/app/ocorrencia/encerrar" var="urlEncerrarOcorrencia" />


<!DOCTYPE html>
<html>
<head>
	<c:import url="../templates/head.jsp"/>
	<style>
	#tabelaOcorrencias img{
		background-image: linear-gradient(to left bottom, #2432cc, #cc29cc, #cc2525);
		padding: 16px;
		width: 100px;
	}
	</style>
</head>
<body>
	<c:import url="../templates/header.jsp"/>
	<main class="container">
		<a href="${urlNovaOcorrencia}" class="btn btn-red d-block ma-l-auto ma-t-s" style="max-width: 220px"> Abrir ocorr�ncia</a>
		<h1 class="fx-slide-in">Ocorr�ncias</h1>
		<section id="sectionOcorrencias">
			<h2>Classificar por: </h2>
			<%--Filtros de busca --%>
			<form>
				<select name="filtro">
					<option value="TODOS">Todos</option>
					<option value="EM_ATENDIMENTO">Em atendimento</option>
					<option value="AGUARDANDO">Aguardando resposta</option>
				</select>
			</form>
			
			<%-- Tabela de ocorr�ncias --%>
			<table id="tabelaOcorrencias" class="table container read-container">
				<thead>
					<tr>
						<th>#</th>
						<th>Ocorr�ncias</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ocorrencias}" var="ocorrencia">
						<tr>
							<%--- Sinalizador de status --%>
							<td></td>
							<%-- Descri��o da ocorr�ncia --%>
							<td>
								<p class="ocorrencia-id">
									<a href="${urlVisualizarOcorrencia}/editar?id=${ocorrencia.id}">
										#<fmt:formatNumber value="${ocorrencia.id}" pattern="###0"/>
									</a>
								</p>
								<h4>${ocorrencia.titulo}</h4>
								<p class="ocorrencia-detalhe"><b class="color-pink">Data de abertura: </b>
									<fmt:formatDate value="${ocorrencia.dataCadastro}" pattern="dd/MM/yyyy hh:mm:ss"/>
								</p>
								<p class="ocorrencia-detalhe"><b class="color-pink">�ltima modifica��o: </b>
									<fmt:formatDate value="${ocorrencia.dataModificacao}" pattern="dd/MM/yyyy hh:mm:ss"/>
								</p>
								<p class="ocorrencia-detalhe"><b class="color-pink">Data de abertura: </b>
									<fmt:formatDate value="${ocorrencia.dataConclusao}" pattern="dd/MM/yyyy hh:mm:ss"/>
								</p>
							</td>
							<%--Quem atendeu ocorrencia/link de atendimento--%>
							<td>
								<c:choose>
									<c:when test="${empty ocorrencia.tecnico}">
										<a href="${urlAssumirOcorrencia}?id=${ocorrencia.id}">Assumir</a>
									</c:when>
									<c:otherwise>
										<img rel="Imagem do t�cnico" title="Atendida por ${ocorrencia.tecnico.nome} ${ocorrencia.tecnico.sobrenome}"
										src="${ocorrencia.tecnico.caminhoFoto}">
										
										<c:if test="${ocorrencia.tecnico.id eq usuarioLogado.id }">
											<a href="${urlEncerrarOcorrencia}?id=${ocorrencia.id}">Encerrar</a>
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</section>
	</main>
	<c:import url="../templates/botoesFlutuantes.jsp"/>
</body>
</html>