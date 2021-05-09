<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>



<!--  DELETAR PERFIL -->
<script>
function modalConfirmar(campo ){
	var id = document.getElementById("idValor").value;
	document.getElementById("tabelaSolicitada").value = campo;
	document.getElementById("idAltera").value = id;
	
	document.getElementById("formConfirmar").submit();
}

function modalRecusar(campo ){
	var id = document.getElementById("idValor").value;
	document.getElementById("tabelaSolicitada_Rec").value = campo;
	document.getElementById("idAltera_Rec").value = id;
	document.getElementById("formRecusar").submit();
}

</script>
<form action="/confirmar" method="post" id="formConfirmar" accept-charset="utf-8">
	<input type="hidden" name="tabelaSolicitada" id="tabelaSolicitada">
	<input type="hidden" name="idAltera" id="idAltera">
</form>

<form action="/recusar" method="post" id="formRecusar" accept-charset="utf-8">
	<input type="hidden" name="tabelaSolicitada" id="tabelaSolicitada_Rec">
	<input type="hidden" name="idAltera" id="idAltera_Rec">
</form>

<!--  DELETAR PERFIL -->
