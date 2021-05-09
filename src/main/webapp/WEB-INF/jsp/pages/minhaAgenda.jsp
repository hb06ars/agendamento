<!-- HEADER -->
<jsp:include page="includes/header.jsp" />
<!-- HEADER -->
<!-- TAGS -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!-- TAGS -->
<!-- INICIO BODY -->

<!-- DOWNLOAD EXCEL -->
<jsp:include page="includes/jquery/excel/downloadExcel.jsp" />
<!-- DOWNLOAD EXCEL -->


<script>

function acao(valor){
	document.getElementById("acao").value = valor;
}

function cancelar(){

	document.getElementById("data").value = '';
	document.getElementById("inicio").value = '';
	document.getElementById("fim").value = '';
	document.getElementById("acao").value = '';
	document.getElementById("cliente").value = '';
	document.getElementById("servico").value = '';
	document.getElementById("preco").value = '';
	document.getElementById("observacao").value = '';
	document.getElementById("atualizar").style.display = "none";
	document.getElementById("recusar").style.display = "none";
	document.getElementById("salvar").style.display = "block";
	document.getElementById("cancelar").style.display = "none";
}

function editar(id, tipo){
	if(tipo == 'confirmar'){
		document.getElementById("atualizar").style.display = "block";
		document.getElementById("recusar").style.display = "none";
	} else if(tipo == 'recusar'){
		document.getElementById("recusar").style.display = "block";
		document.getElementById("atualizar").style.display = "none";
	}
	document.getElementById("idValor").value = id;
	document.getElementById("acao").value = 'atualizar';
	document.getElementById("salvar").style.display = "none";
	document.getElementById("cancelar").style.display = "block";

	
	var inicio = 'x';
	var fim = 'x';
	<c:forEach items="${consultas }" var="p" varStatus="s">
		if(${p.id} == id){
			var dataFormatada = '${p.data}';
			dataFormatada = dataFormatada.substring(0, 4) + '-' + dataFormatada.substring(5, 7) + '-' + dataFormatada.substring(8, 10);
			document.getElementById("data").value = dataFormatada;
			document.getElementById("inicio").value = '${p.inicioHora}';
			document.getElementById("fim").value = '${p.fimHora}';
			document.getElementById("cliente").value = '${p.cliente} (${p.clienteSistema.nome})';
			document.getElementById("servico").value = '${p.servico.nome}';
			document.getElementById("preco").value = 'R$${p.preco}';
			document.getElementById("observacao").value = '${p.observacoes}';
		}
	</c:forEach>

		
}


</script>


<!-- start: page -->
<c:if test="${usuario.perfil.admin || usuario.perfil.funcionario }">
<div class="row">
<form action="/precos" method="post" accept-charset="utf-8">
	<div class="col-md-12">
		<div data-collapsed="0" class="panel">
			<div class="panel-heading">
				<div class="panel-title">
					<div class="panel-actions">
						<a href="#" class="panel-action panel-action-toggle" data-panel-toggle></a>
						<a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss></a>
					</div>
					<h2 class="panel-title" id="">Consultas </h2>
				</div>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-4 form-group">
						<label>Data:</label>
						<input type="date" placeholder="Nome" name="data" id="data" class="form-control" readonly>
					</div>
					<div class="col-md-4 form-group">
						<label>Inicio:</label>
						<input type="time" placeholder="Nome" name="inicio" id="inicio" class="form-control" readonly>
					</div>
					<div class="col-md-4 form-group">
						<label>Fim:</label>
						<input type="time" placeholder="Nome" name="fim" id="fim" class="form-control" readonly>
					</div>
					<div class="col-md-4 form-group">
						<label>Cliente:</label>
						<input type="text" placeholder="Nome" name="cliente" id="cliente" class="form-control" readonly>
					</div>
					<div class="col-md-4 form-group">
						<label>Servico:</label>
						<input type="text" placeholder="Nome" name="servico" id="servico" class="form-control" readonly>
					</div>
					<div class="col-md-4 form-group">
						<label>Preco:</label>
						<input type="text" placeholder="Nome" name="preco" id="preco" class="form-control" readonly>
					</div>
					<div class="col-md-12 form-group">
						<label>Observacao:</label>
						<input type="text" placeholder="Nome" name="observacao" id="observacao" class="form-control" readonly>
					</div>
					<div class="col-md-2 form-group" id="salvar">
						
					</div>
					<div class="col-md-2 form-group" id="atualizar" style="display:none">
						<input type="button" class="btn btn-primary" onclick="modalConfirmar('consultas')" value="Confirmar">
					</div>
					<div class="col-md-2 form-group" id="recusar" style="display:none">
						<input type="button" class="btn btn-danger" onclick="modalRecusar('consultas')" value="Recusar">
					</div>
					<div class="col-md-2 form-group" id="cancelar" style="display:none">
						<input type="button" class="btn btn-default" onclick="cancelar()" value="Voltar">
					</div>
					
					<input type="hidden" id="idValor" name="idValor" value="">
					<input type="hidden" id="acao" name="acao" value="salvar">
				</div>
			</div>
		</div>
	</div>
</form>
</div>
</c:if>














<section class="panel">
							<header class="panel-heading">
								<div class="panel-actions">
									<a href="#" class="panel-action panel-action-toggle" data-panel-toggle></a>
									<a href="#" class="panel-action panel-action-dismiss" data-panel-dismiss></a>
								</div>
						
								<h2 class="panel-title">Registro dos Agendamentos</h2>
							</header>
							<div class="panel-body" style="overflow:auto">
								<table class="table table-bordered table-striped mb-none" id="datatable-default" style="overflow:auto">
									<thead>
										<tr>
											<c:if test="${usuario.perfil.admin || usuario.perfil.funcionario }">
												<th>Confirmar</th>
												<th>Falar</th>
												<th>Cancelar</th>
											</c:if>
											<th>Data</th>
											<th>Inicio</th>
											<th>Fim</th>
											<th>Confirmado</th>
											<th>Cliente</th>
											<th>Cliente Sistema</th>
											<th>Profissional</th>
											<th>Servico</th>
											<th>Preco</th>
											<th>Observacao</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${consultas }" var="p">
											<tr class="gradeX">
												<c:if test="${usuario.perfil.admin || usuario.perfil.funcionario }">
													<td> <i style="color:#9AFE2E" class="fa fa-check-circle" onclick="editar(${p.id }, 'confirmar') "></i>
													<td> <a style="color:#BCF5A9" class="fa fa-whatsapp" href="https://wa.me/55${p.clienteSistema.celular }"></a>
													<td> <i style="color:#F78181" class="fa fa-trash" onclick="editar(${p.id }, 'recusar') "></i> &nbsp </i>
												</c:if>
												<td>${p.apenasData }</td>
												<td>${p.inicioHora }</td>
												<td>${p.fimHora }</td>
												<td>
												<c:if test="${p.confirmado && !p.cancelado}"><span style="color:#81DAF5">Sim</span></c:if>
												<c:if test="${!p.confirmado && !p.cancelado}"><span style="color:#F5A9A9">Não</span></c:if>
												<c:if test="${!p.confirmado && p.cancelado}"><span style="color:#F5A9A9">Recusado</span></c:if>
												</td>
												<td>${p.cliente }</td>
												<td>${p.clienteSistema.nome }</td>
												<td>${p.profissional.nome }</td>
												<td>${p.servico.nome }</td>
												<td>R$${p.preco }</td>
												<td>${p.observacoes }</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="panel-footer">
								<button type="button" class="btn btn-primary" onclick="tableToExcel('datatable-default', 'Documento')">Download</button>
							</div>
						</section>





<!-- end: page -->
	</section>
</div>







<!-- FIM BODY -->
</div>
<!-- FIM BODY -->
<!-- MAIN NO HEADER -->
</main>
<!-- HEADER -->
<jsp:include page="includes/footer.jsp" />
<!-- HEADER -->