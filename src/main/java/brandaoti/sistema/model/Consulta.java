package brandaoti.sistema.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Consulta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //Esse número é o ID automático gerado.
	
	@Column
	private Boolean ativo = true;
	@Column
	private String cliente;
	@Column
	private LocalDateTime data;
	@Column
	private LocalDateTime inicio;
	@Column
	private LocalDateTime fim;
	@OneToOne
	private Preco servico;
	@OneToOne
	private Usuario profissional;
	@OneToOne
	private Usuario clienteSistema;
	@Column
	private Double preco = 0.0;
	@Column
	private String observacoes;
	@Column
	private Boolean confirmado = false;
	@Column
	private Boolean cancelado  = false;
	
	
	
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	public String getApenasData() {
		String strData="";
		String dia = "";
		String mes = "";
		String ano = "";
		if( data.getDayOfMonth() < 10 ) {
			dia = "0"+data.getDayOfMonth();
		} else {
			dia = ""+data.getDayOfMonth();
		}
		Integer m = data.getMonthValue();
		if( m < 10 ) {
			mes = "0"+m;
		} else {
			mes = ""+m;
		}
		ano = ""+data.getYear();
		strData = dia+"/"+mes+"/"+ano;
		return strData;
	}
	
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public LocalDateTime getInicio() {
		return inicio;
	}
	
	public String getInicioHora() {
		String horario = "";
		String min = "";
		String hr = "";
		if(inicio.getMinute() < 10) {
			min = "0"+inicio.getMinute();
		}else {
			min = ""+inicio.getMinute();
		}
		if(inicio.getHour() < 10) {
			hr = "0"+inicio.getHour();
		} else {
			hr = ""+inicio.getHour();
		}
		horario = hr+":"+min;
		return horario;
	}
	public String getFimHora() {
		String horario = "";
		String min = "";
		String hr = "";
		if(fim.getMinute() < 10) {
			min = "0"+fim.getMinute();
		} else {
			min = ""+fim.getMinute();
		}
		if(fim.getHour() < 10) {
			hr = "0"+fim.getHour();
		} else {
			hr = ""+fim.getHour();
		}
		horario = hr+":"+min;
		return horario;
	}
	
	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}
	public LocalDateTime getFim() {
		return fim;
	}
	public void setFim(LocalDateTime fim) {
		this.fim = fim;
	}
	public Preco getServico() {
		return servico;
	}
	public void setServico(Preco servico) {
		this.servico = servico;
	}
	public Usuario getProfissional() {
		return profissional;
	}
	public void setProfissional(Usuario profissional) {
		this.profissional = profissional;
	}
	public Usuario getClienteSistema() {
		return clienteSistema;
	}
	public void setClienteSistema(Usuario clienteSistema) {
		this.clienteSistema = clienteSistema;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public Boolean getConfirmado() {
		return confirmado;
	}
	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}
	public Boolean getCancelado() {
		return cancelado;
	}
	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	
	
	
	
}
