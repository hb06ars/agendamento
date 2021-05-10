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

<!-- Script -->
<script>
function redirecionar(link){
	window.location.href=link;
}






</script>
<!-- Script -->
			
									    	
<!-- start: page -->


                <!-- Start content -->
                <div class="content">
                    <div class="container">

                        <!-- Page-Title -->
                        <div class="row">
                            <div class="col-sm-12">
                               <h4 class="page-title">Atendimento de Clientes</h4>
                                <p class="text-muted page-title-alt">Olá ${usuario.nome }! Gerencie as reservas de consultas de seus clientes!</p>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 col-lg-3">
                                <div class="widget-bg-color-icon card-box fadeInDown animated">
                                    <div class="bg-icon bg-icon-primary pull-left">
                                        <i class="md md-thumb-up text-primary"></i>
                                    </div>
                                    <div class="text-right">
                                        <h3 class="text-dark"><b class="counter">${confirmada }</b></h3>
                                        <p class="text-muted">Confirmadas</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>

                            <div class="col-md-6 col-lg-3">
                                <div class="widget-bg-color-icon card-box">
                                    <div class="bg-icon bg-icon-pink pull-left">
                                        <i class="md  md-thumb-down text-pink"></i>
                                    </div>
                                    <div class="text-right">
                                        <h3 class="text-dark"><b class="counter">${recusada }</b></h3>
                                        <p class="text-muted">Recusadas</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>

                            <div class="col-md-6 col-lg-3">
                                <div class="widget-bg-color-icon card-box">
                                    <div class="bg-icon bg-icon-warning pull-left">
                                        <i class="md md-warning text-warning"></i>
                                    </div>
                                    <div class="text-right">
                                        <h3 class="text-dark"><b class="counter">${pendentes }</b></h3>
                                        <p class="text-muted">Pendentes</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>

                            <div class="col-md-6 col-lg-3">
                                <div class="widget-bg-color-icon card-box">
                                    <div class="bg-icon bg-icon-success pull-left">
                                        <i class="md md-people text-success"></i>
                                    </div>
                                    <div class="text-right">
                                        <h3 class="text-dark"><b class="counter">${clientes }</b></h3>
                                        <p class="text-muted">Clientes</p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>


                        <!-- end row -->

						<c:if test="${!usuario.perfil.cliente || usuario.perfil.admin}">
	                        <div class="row">
	                        	<div class="col-lg-12">
	                        		<div class="card-box">
	                        			<h4 class="text-dark header-title m-t-0">Atividades</h4>
	                        			<p class="text-muted m-b-30 font-13">
											Calendário das reservas.
										</p>
	                        			<div class="nicescroll p-20" style="height: 295px;">
	                                        <div class="timeline-2">
	                                        
	                                        	<c:forEach items="${consultas }" var="c">
		                                            <div class="time-item">
		                                                <div class="item-info">
		                                                    <div class="text-muted">
		                                                    	<small>${c.apenasData } ${c.inicioHora } até 
		                                                    		<c:if test="${c.confirmado }">${c.fimHora }</c:if> 
		                                                    		<c:if test="${!c.confirmado }">?</c:if> 
		                                                    	</small>
		                                                    </div>
		                                                    <c:if test="${c.confirmado }">
		                                                    	<p>
		                                                    		<strong><a class="text-info">Confirmado</a></strong>
		                                                    		<br>${c.cliente } <c:if test="${c.clienteSistema != null }">(${c.clienteSistema.nome })</c:if>
		                                                    		<br>${c.servico.nome }
																	<br><strong>${c.observacoes }</strong>
		                                                    	</p>
		                                                    </c:if>
		                                                    
		                                                    <c:if test="${!c.confirmado && !c.cancelado }">
		                                                    	<p>
		                                                    		<strong><a class="text-warning">Pendente</a></strong>
		                                                    		<br>${c.cliente } <c:if test="${c.clienteSistema != null }">(${c.clienteSistema.nome })</c:if>
		                                                    		<br>${c.servico.nome }
																	<br><strong>${c.observacoes }</strong>
		                                                    	</p>
		                                                    </c:if>
		                                                    
		                                                    <c:if test="${c.cancelado }">
		                                                    	<p>
		                                                    		<strong><a class="text-danger">Recusado</a></strong>
		                                                    		<br>${c.cliente } <c:if test="${c.clienteSistema != null }">(${c.clienteSistema.nome })</c:if>
		                                                    		<br>${c.servico.nome }
																	<br><strong>${c.observacoes }</strong>
		                                                    	</p>
		                                                    </c:if>
		                                                    
		                                                     
		                                                </div>
		                                            </div>
	                                            </c:forEach>
	                                            
	                                        </div>
	                                    </div>
	                        		</div>
	                        	</div>
	                        </div>
						</c:if>

                    </div> <!-- container -->

                </div> <!-- content -->






<!-- end: page -->




<!-- FOOTER -->
<jsp:include page="includes/footer.jsp" />
<!-- FOOTER -->