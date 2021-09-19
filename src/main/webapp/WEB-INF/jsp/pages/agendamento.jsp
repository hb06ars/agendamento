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

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>


<style>
* {
  box-sizing: border-box;
  font-family: 'Roboto', sans-serif;
  list-style: none;
  margin: 0;
  outline: none;
  padding: 0;
}

a {
  text-decoration: none;
}

.select2-container--default .select2-selection--single{
	background-color:#2B4450;
}

body, html {
  height: 100%;
}

body {
	font-family: 'Roboto', sans-serif;
}

.container_b {
	align-items: center;
	display: flex;
	height: 100%;
	justify-content: center;
	margin: 0 auto;
	max-width: 100%;
	width: 100%;
}

.calendar_b {
	background: #2b4450;
	border-radius: 4px;
	box-shadow: 0 5px 20px rgba(0, 0, 0, .3);
	height: 100%;
    perspective: 1000;
	transition: .9s;
	transform-style: preserve-3d;
	width: 100%;
}

/* Front - Calendar */
.front_b {
	transform: rotateY(0deg);
}

.current-date_b {
	border-bottom: 1px solid rgba(73, 114, 133, .6);
	display: flex;
	justify-content: space-between;
	padding: 30px 40px;
}

.current-date_b h1 {
	color: #dfebed;
	font-size: 1.4em;
	font-weight: 300;
}

.week-days_b {
	color: #dfebed;
	display: flex;
	justify-content: space-between;
	font-weight: 600;
	padding: 30px 40px;
}

.days_b {
	display: flex;
	flex-wrap: wrap;
	justify-content: space-between;
}

.weeks_b {
	color: #fff;
	display: flex;
	flex-direction: column;
	padding: 0 40px;
}

.weeks_b div {
	display: flex;
	font-size: 1.2em;
	font-weight: 300;
	justify-content: space-between;
	margin-bottom: 20px;
	width: 100%;
}

.last-month_b {
	opacity: .3;
}

.weeks_b span {
	padding: 10px;
}

.weeks_b span.active {
	background: #f78536;
	border-radius: 50%;
}

.weeks_b span:not(.last-month):hover {
	cursor: pointer;
	font-weight: 600;
}

.event_b {
	position: relative;
}

.event_b:after {
	content: '•';
	color: #f78536;
	font-size: 1.4em;
	position: absolute;
	right: -4px;
	top: -4px;
}

/* Back - Event form */

.back_b {
	height: 100%;
	transform: rotateY(180deg);
}

.back_b input {
	background: none;
	border: none;
	border-bottom: 1px solid rgba(73, 114, 133, .6);
	color: #dfebed;
	font-size: 1.4em;
	font-weight: 300;
	padding: 30px 40px;
	width: 100%;
}

.info_b {
	color: #dfebed;
	display: flex;
	flex-direction: column;
	font-weight: 600;
	font-size: 1.2em;
	padding: 30px 40px;
}

.info_b div:not(.observations) {
	margin-bottom: 40px;
}

.info_b span {
	font-weight: 300;
}

.info_b .date {
	display: flex;
	justify-content: space-between;
}

.info_b .date p {
	width: 50%;
}

.info_b .address p {
	width: 100%;
}

.actions_b {
	bottom: 0;
	border-top: 1px solid rgba(73, 114, 133, .6);
	display: flex;
	justify-content: space-between;
	position: absolute;
	width: 100%;
}

.actions_b button {
	background: none;
	border: 0;
	color: #fff;
	font-weight: 600;
	letter-spacing: 3px;
	margin: 0;
	padding: 30px 0;
	text-transform: uppercase;
	width: 50%;
}

.actions_b button:first-of-type {
	border-right: 1px solid rgba(73, 114, 133, .6);
}

.actions_b button:hover {
	background: #497285;
	cursor: pointer;
}

.actions_b button:active {
	background: #5889a0;
	outline: none;
}

/* Flip animation */

.flip_b {
	transform: rotateY(180deg);
}

.front_b, .back_b {
	backface-visibility: hidden;
}

::-webkit-input-placeholder {
   color: red;
}

</style>


<!-- Script -->
<script>
function redirecionar(link){
	window.location.href=link;
}






</script>
<!-- Script -->
			
									    	
<!-- start: page -->
<form class="form-horizontal m-t-20" action="/agendamento" id="salvarAgendamento" enctype="multipart/form-data" method="post" accept-charset="utf-8">
<div class="row">
	<div class="col-lg-12">
		<div class="row">
			<div class="col-md-12">
				<div class="container_b">
			      <div class="calendar_b">
			        <div class="front_b" id="calendario" style="display:block">
			          <div class="current-date_b">
			            <h1>${hoje }</h1>
			            <h1>${mes } ${ano }</h1>	
			          </div>
			          <div class="current-month">
			            <ul class="week-days_b">
			              <li>DOM</li>
			              <li>SEG</li>
			              <li>TER</li>
			              <li>QUA</li>
			              <li>QUI</li>
			              <li>SEX</li>
			              <li>SAB</li>
			            </ul>
				
						<c:set var = "cont" value = "1"/>
			            <div class="weeks_b">
			              <div class="first">
			              	<c:if test="${diaPrimeiroSemana == 'DOM' }"> 
			              		<span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span> 
			              	</c:if>
			              	<c:if test="${diaPrimeiroSemana == 'SEG' }">
			                	<span class="last-month" style="color:#2B4450">00</span>
			                	<span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			                </c:if>
			              	<c:if test="${diaPrimeiroSemana == 'TER' }">
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
							</c:if>
			              	<c:if test="${diaPrimeiroSemana == 'QUA' }">
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
							</c:if>
			              	<c:if test="${diaPrimeiroSemana == 'QUI' }">
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
							</c:if>
			              	<c:if test="${diaPrimeiroSemana == 'SEX' }">
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
			              		<c:set var = "cont" value = "${cont + 1 }"/><span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
							</c:if>
			              	<c:if test="${diaPrimeiroSemana == 'SAB' }">
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span class="last-month" style="color:#2B4450">00</span>
								<span onclick="valorData('0${cont}/${mesNumero}/${ano}')" >0${cont }</span>
								<c:set var = "cont" value = "${cont + 1 }"/>
							</c:if>
			              </div>
			              <c:if test="${diaPrimeiroSemana != 'SAB' }">
			              	<c:set var = "cont" value = "${cont + 1 }"/>
			              </c:if>
			              <div class="second">
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> <c:if test="${cont < 10 }">0</c:if>${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> <c:if test="${cont < 10 }">0</c:if>${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> <c:if test="${cont < 10 }">0</c:if>${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> <c:if test="${cont < 10 }">0</c:if>${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> <c:if test="${cont < 10 }">0</c:if>${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> <c:if test="${cont < 10 }">0</c:if>${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> <c:if test="${cont < 10 }">0</c:if>${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			              </div>
			
			              <div class="third">
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> <c:if test="${cont < 10 }">0</c:if>${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			              </div>
			
			              <div class="fourth">
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			                <span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if>> ${cont }<c:set var = "cont" value = "${cont + 1 }"/></span>
			              </div>
			
			              <div class="fifth">
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450" >00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450" >00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450" >00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450" >00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450" >00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450" >00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450" >00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			              </div>
			              
			              <div class="sixth">
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450">00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450">00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450">00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450">00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450">00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450">00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			                <c:if test="${cont > maxDiasMes}"><span class="last-month" style="color:#2B4450">00</span></c:if> <c:if test="${cont <= maxDiasMes}"><span onclick="valorData('<c:if test="${cont < 10 }">0</c:if>${cont}/${mesNumero}/${ano}')" <c:if test="${cont == dia }">class="active"</c:if> >${cont }<c:set var = "cont" value = "${cont + 1 }"/></span></c:if>
			              </div>
			            </div>
			          </div>
			        </div>	
			
			        <div class="back_b" id="dados" style="display:none">
			        
			        <div class="row">
						<div class="col-lg-6">
							<c:if test="${usuario.perfil.funcionario }">
								<input name="nomeCliente" id="nomeCliente" placeholder="Insira o nome do Cliente! *" value="" required autofocus>
							</c:if>
							<c:if test="${!usuario.perfil.funcionario }">
								<input name="nomeCliente" id="nomeCliente" placeholder="Insira seu nome." value="${usuario.nome }" required autofocus>
							</c:if>
			          <div class="info_b">
			            <div class="date_b">
			              <p class="info-date">
			              Data: <span id="dataTexto"> - </span>
			              </p>
						  <p class="info-date">
			              <span>
							<label>Hora:</label>
							<input onchange="verDisponibilidade()" style="font-size:18px; background-color:#2B4450; max-width: 180px; max-height: 1px;" type="time" id="horaEscolhida" name="horaEscolhida" required>
							<i id="disponibilidadeTexto" style="display:none; color:#74DF00">Dispon&iacute;vel</i>
							<i id="indisponibilidadeTexto" style="display:none; color:#F78181">Indispon&iacute;vel</i>
						  </span>
			            </div>
			            <div class="address">
				             <p>
				                Servi&ccedil;o: 
				                <select class="form-control select2" id="servicoSelecionado" name="servicoSelecionado" onchange="atualizaPreco()">
					                <option>Selecione o Servi&ccedil;o</option>
				                    	<c:forEach items="${precos }" var="p">
				                    		<option value="${p.id }">${p.nome } (R$${p.preco })</option>
				                    	</c:forEach>
				                </select>
				             </p>
			            </div>
			            <div class="profissional">
			              <p>
			                Profissional: 
			                <select class="form-control select2" name="profissionalSelecionado" id="profissionalSelecionado" style="background-color:#2B4450" onchange="atualizaDisponibilidade()">
				                		<option value="" <c:if test="${!usuario.perfil.funcionario }">selected</c:if> >Qualquer um.</option>
			                    	<c:forEach items="${funcionarios }" var="f">
			                    		<option value="${f.id }" <c:if test="${f.id == usuario.id }">selected</c:if> >${f.nome }</option>
			                    	</c:forEach>
			                </select>
			              </p>
			            </div>
			            <div class="pagamento">
			              <p>
			                Pre&ccedil;o: <span id="precoTexto"> - </span>
			              </p>
			            </div>
			            <div class="observations">
			              <p>
			                Observa&ccedil;&otilde;es: 
			                <span>
								<input style="background-color:#2B4450; font-size:18px; max-height: 1px;" type="text" id="obs" name="obs" required>
							</span>
			              </p>
			            </div>
			          </div>
						</div>
						<div class="col-lg-6">
							<input value="Disponibilidade" readonly>
							<i id="pessoasNaFrente" style="display:block; color:#F78181"></i>
							<div class="p-20">
								<table class="table m-0" id="tabDisponibilidade">
									<thead>
										<tr>
											<th>In&iacute;cio</th>
											<th>Fim</th>
											<th>Profissional</th>
											<th>Status</th>
										</tr>
									</thead>
										<tbody>
											<tr>
											</tr>
										</tbody>
								</table>
							</div>
							<br>
						</div>
			        </div>
			        
			        <div>
			            &nbsp&nbsp&nbsp&nbsp<button id="botaoSalvar" onclick="return submitForm()" class="btn btn-default">
			              Salvar <i class="ion-checkmark"></i>
			            </button>
			            &nbsp&nbsp&nbsp&nbsp<span class="btn btn-danger" onclick="cancela()">
			              Cancelar <i class="ion-android-close"></i>
			            </span>
			            <br>
			            &nbsp
			        </div>
			      </div>
			    </div>
			  </div>				
			</div> <!-- end col -->
		</div>  <!-- end row -->
	</div>
<!-- end col-12 -->
</div> <!-- end row -->

<input type="hidden" name="salvar" id="salvar" value="1" />
<input type="hidden" id="dataSubmit" name="dataSubmit">
<input type="hidden" id="precoSubmit" name="precoSubmit">
</form>

<br>
<div class="row">
	<div class="col-lg-12">
		<div class="container_b">
			<input type="button" style="cursor:pointer" class="btn btn-primary" id="btProximo" onclick="mudaMes('anterior')" value="Anterior" > &nbsp&nbsp
			<input type="button" style="cursor:pointer" class="btn btn-primary" id="btAnterior" onclick="mudaMes('proximo')" value="Pr&oacute;ximo">
		</div>
	</div>
</div>

<form action="/agendamento" id="mudaMesSub" enctype="multipart/form-data" method="post" accept-charset="utf-8">
	<input type="hidden" name="proximo" id="proximo" value="0" />
	<input type="hidden" name="anterior" id="anterior" value="0" />
	<input type="hidden" name="mesAtual" value="${mesSelecionado }" />
</form>

<script>

function submitForm(){
	var servico = document.getElementById("servicoSelecionado").value;
	var horario = document.getElementById("horaEscolhida").value;
	var nomeCliente = document.getElementById("nomeCliente").value;
	
	if(!servico.includes("Selecione o Servi") && horario != "" && nomeCliente != ""){
		document.getElementById("salvarAgendamento").submit();	
	} else {
		document.getElementById("mensagemModal").innerHTML = 'Preencha ao menos o nome do Cliente, Hora e Servi&ccedil;o!';
		$("#modalMensagem").modal().show();
	}
	return false
}


function valorData(data){
	document.getElementById("dataTexto").innerHTML=data;
	document.getElementById("dataSubmit").value=data;
}

function reset(){
	document.getElementById("horaEscolhida").value = "--:--";
	document.getElementById("disponibilidadeTexto").style.display = "none";
	document.getElementById("indisponibilidadeTexto").style.display = "none";
	document.getElementById("botaoSalvar").disabled = true;
}

function compararHora(horaEscolhida, horaInicio, horaFim)
{
	horaEscolhida = horaEscolhida.split(":");
	horaInicio = horaInicio.split(":");
	horaFim = horaFim.split(":");
    var d = new Date();
    
    var dataEscolhida = new Date(d.getFullYear(), d.getMonth(), d.getDate(), horaEscolhida[0], horaEscolhida[1]);
    var dataInicio = new Date(d.getFullYear(), d.getMonth(), d.getDate(), horaInicio[0], horaInicio[1]);
    var dataFim = new Date(d.getFullYear(), d.getMonth(), d.getDate(), horaFim[0], horaFim[1]);
    
    var validacao = true;
    
    if(horaEscolhida >= horaInicio && horaEscolhida <= horaFim){
    	validacao = false;
    }
    
    return validacao;
};

function verDisponibilidade(){
	document.getElementById("botaoSalvar").disabled = false;
	document.getElementById("disponibilidadeTexto").style.display = "block";
	
	var valido = true;
	var profissional = document.getElementById("profissionalSelecionado").value;
	var horaEscolhida = document.getElementById("horaEscolhida").value;
	var data = document.getElementById("dataTexto").innerHTML;
	<c:forEach items="${consultas }" var="c">
		if('${c.profissional.id}' == profissional){
			var dataFormatada = '${c.data}';
			dataFormatada = dataFormatada.substring(8, 10) + '/' + dataFormatada.substring(5, 7) + '/' + dataFormatada.substring(0, 4);
			if(dataFormatada == data){
				if(valido){
					valido = compararHora(horaEscolhida, '${c.inicioHora}', '${c.fimHora}');	
				}
				if(valido){
					document.getElementById("disponibilidadeTexto").style.display = "block";
					document.getElementById("indisponibilidadeTexto").style.display = "none";
					document.getElementById("botaoSalvar").disabled = false;
				} else{
					document.getElementById("disponibilidadeTexto").style.display = "none";
					document.getElementById("indisponibilidadeTexto").style.display = "block";
					document.getElementById("botaoSalvar").disabled = true;
				}
			}
		}
	</c:forEach>

	
}

function atualizaDisponibilidade(){
	// TABELA
	var pessoasNaFrente = 0;
	reset();
	var table = document.getElementById("tabDisponibilidade");
	var linhas = parseInt(table.getElementsByTagName('tr').length);
	//Limpando
	if(linhas > 1){
		for(var i = 1; i <= (linhas - 1); i++){
			table.deleteRow(i);
		}
	}
	//Inserindo
	var cont=1;
	var profissional = document.getElementById("profissionalSelecionado").value;
	var data = document.getElementById("dataTexto").innerHTML;
	<c:forEach items="${consultas }" var="c">
		if('${c.profissional.id}' == profissional){
			var dataFormatada = '${c.data}';
			dataFormatada = dataFormatada.substring(8, 10) + '/' + dataFormatada.substring(5, 7) + '/' + dataFormatada.substring(0, 4);
			if(dataFormatada == data){
				var row = table.insertRow(cont);
				var cell0 = row.insertCell(0);
				var cell1 = row.insertCell(1);
				var cell2 = row.insertCell(2);
				var cell3 = row.insertCell(3);
				cell0.innerHTML = '${c.inicioHora}';
				if(${c.confirmado}){
					cell1.innerHTML = '${c.fimHora}';	
				} else{
					cell1.innerHTML = '?';
					pessoasNaFrente = pessoasNaFrente + 1;
				}
				
				cell2.innerHTML = '${c.profissional.nome}';
				if(${c.confirmado}){
					cell3.innerHTML = 'Ocupado';	
					cell3.style.color = "#F5A9A9";
				} else{
					cell3.innerHTML = 'Aguardando';
					cell3.style.color = "#FAAC58";
				}
				cell0.style.color = "#FFFFFF";
				cell1.style.color = "#FFFFFF";
				cell2.style.color = "#FFFFFF";
				cont++;
			}
			
			
			
		}	
	</c:forEach>
	
	if(pessoasNaFrente == 1){
		document.getElementById("pessoasNaFrente").innerHTML = '&nbsp&nbsp&nbsp&nbsp&nbsp'+pessoasNaFrente + ' pessoa na sua frente aguardando confirmar.';	
	}
	if(pessoasNaFrente > 1){
		document.getElementById("pessoasNaFrente").innerHTML = '&nbsp&nbsp&nbsp&nbsp&nbsp'+pessoasNaFrente + ' pessoas na sua frente aguardando confirmar.';	
	}
	if(pessoasNaFrente == 0){
		document.getElementById("pessoasNaFrente").innerHTML = '';	
	}
	
}

function atualizaPreco(){
	var preco;
	var servicoSelecionado = document.getElementById("servicoSelecionado").value;
	<c:forEach items="${precos}" var="p">
		if('${p.id}' == servicoSelecionado){
			preco = document.getElementById("precoTexto").innerHTML='${p.preco}';
			preco = preco.replace('.',',');
			document.getElementById("precoTexto").innerHTML='R$'+preco;
			document.getElementById("precoSubmit").value=preco;
		}
	</c:forEach>
	
}

function mudaMes(mes){
	if(mes == 'proximo'){
		document.getElementById("proximo").value=1;
		document.getElementById("anterior").value=0;
	}else{
		document.getElementById("proximo").value=0;
		document.getElementById("anterior").value=1;
	}
	document.getElementById("mudaMesSub").submit();
}

function cancela(){
	document.getElementById("calendario").style.display = "block";
	document.getElementById("dados").style.display = "none";
	document.getElementById("btProximo").style.display = "block";
	document.getElementById("btAnterior").style.display = "block";
	app.swap();
}


var app = {
	settings: {
		container: $('.calendar_b'),
		calendar: $('.front_b'),
		days: $('.weeks_b span'),
		form: $('.back_b'),
		input: $('.back_b input'),
		buttons: $('.back_b button')
	},

	init: function() {
		instance = this;
		settings = this.settings;
		this.bindUIActions();
	},

	swap: function(currentSide, desiredSide) {
		settings.container.toggleClass('flip_b');
		currentSide.fadeOut(900);
		currentSide.hide();
		desiredSide.show();
	},

	bindUIActions: function() {
		settings.days.on('click', function(){
			atualizaDisponibilidade();
			document.getElementById("calendario").style.display = "none";
			document.getElementById("dados").style.display = "block";
			document.getElementById("btProximo").style.display = "none";
			document.getElementById("btAnterior").style.display = "none";
			instance.swap(settings.calendar, settings.form);
			settings.input.focus();
			atualizaDisponibilidade();
			
		});

		settings.buttons.on('click', function(){
			document.getElementById("calendario").style.display = "block";
			document.getElementById("dados").style.display = "none";
			instance.swap(settings.form, settings.calendar);
		});
	}
}

app.init();
</script>


<!-- end: page -->




<!-- FOOTER -->
<jsp:include page="includes/footerSemData.jsp" />
<!-- FOOTER -->