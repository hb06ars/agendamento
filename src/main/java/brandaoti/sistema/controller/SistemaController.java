package brandaoti.sistema.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;

import org.apache.xmlbeans.impl.store.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import brandaoti.sistema.dao.AssuntoDao;
import brandaoti.sistema.dao.ConsultaDao;
import brandaoti.sistema.dao.PrecoDao;
import brandaoti.sistema.dao.PerfilDao;
import brandaoti.sistema.dao.UsuarioDao;
import brandaoti.sistema.excel.ProcessaExcel;
import brandaoti.sistema.excel.Tabela;
import brandaoti.sistema.model.Objeto;
import brandaoti.sistema.model.Perfil;
import brandaoti.sistema.model.Assunto;
import brandaoti.sistema.model.Consulta;
import brandaoti.sistema.model.Preco;
import brandaoti.sistema.model.Usuario;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class SistemaController {
		
		@Autowired
		private UsuarioDao usuarioDao;
		@Autowired
		private PerfilDao perfilDao;
		@Autowired
		private PrecoDao precoDao;
		@Autowired
		private AssuntoDao assuntoDao;
		@Autowired
		private ConsultaDao consultaDao;
		
		public static Usuario usuarioSessao;
		public static String atualizarPagina = null;
		public static Boolean logado = false;
		public static String itemMenu = "home";
		public static String paginaAtual = "Dashboard";
		public static String iconePaginaAtual = "fa fa-home";
		public static Integer mesSelecionado;
		public static Integer anoSelecionado;
		public static String senhaIncorreta = "";
		
		
		public String gerarChamado() {
			Random gerador = new Random();
	    	Calendar data = Calendar.getInstance();
	    	int ano = data.get(Calendar.YEAR);
	    	int mes = data.get(Calendar.MONTH);
	    	mes++;
	    	int m = mes;
	    	int dia = data.get(Calendar.DAY_OF_MONTH);
	        int hora = data.get(Calendar.HOUR_OF_DAY); 
	        int min = data.get(Calendar.MINUTE);
	        int seg = data.get(Calendar.SECOND);
	        int numero = gerador.nextInt(100);
	        String chamado = ""+ano+m+dia+hora+min+seg+numero+usuarioSessao.getId();
	        return chamado;
		}
		
		public String gerarMatricula() {
			Random gerador = new Random();
	    	Calendar data = Calendar.getInstance();
	    	int ano = data.get(Calendar.YEAR);
	    	int mes = data.get(Calendar.MONTH);
	    	mes++;
	    	int m = mes;
	    	int dia = data.get(Calendar.DAY_OF_MONTH);
	        int hora = data.get(Calendar.HOUR_OF_DAY); 
	        int min = data.get(Calendar.MINUTE);
	        int seg = data.get(Calendar.SECOND);
	        int numero = gerador.nextInt(100);
	        String matricula = ""+ano+m+dia+hora+min+seg+numero+usuarioSessao.getId();
	        return matricula;
		}
		
		public void resetaMes() {
			Calendar calendar = new GregorianCalendar();
			mesSelecionado = calendar.get(Calendar.MONTH);
			mesSelecionado = mesSelecionado+1;
			anoSelecionado = calendar.get(Calendar.YEAR);
		}
		
		@RequestMapping(value = {"/","/login"}, produces = "text/plain;charset=UTF-8", method = RequestMethod.GET) // Pagina de Vendas
		public ModelAndView login(@RequestParam(value = "nome", required = false, defaultValue = "Henrique Brandão") String nome) throws SQLException { //Funcao e alguns valores que recebe...
			//Caso não haja registros
			List<Usuario> u = usuarioDao.buscarTudo();
			List<Perfil> p = perfilDao.buscarTudo();
			List<Preco> pl = precoDao.buscarTudo();
			List<Assunto> as = assuntoDao.buscarTudo();
			List<Consulta> consultas = consultaDao.buscarTudo();
			
			usuarioSessao = null;
			
			Calendar calendar = new GregorianCalendar();
			mesSelecionado = calendar.get(Calendar.MONTH);
			anoSelecionado = calendar.get(Calendar.YEAR);
			mesSelecionado = mesSelecionado+1;
		    
			if(p == null || p.size() == 0) {
				Perfil perfil = new Perfil();
				perfil.setAtivo(true);
				perfil.setCodigo("1");
				perfil.setNome("Administrador");
				perfil.setAdmin(true);
				perfil.setFuncionario(true);
				perfil.setCliente(true);
				perfilDao.save(perfil);
				
				perfil = new Perfil();
				perfil.setAtivo(true);
				perfil.setCodigo("2");
				perfil.setNome("Cliente");
				perfil.setAdmin(false);
				perfil.setFuncionario(false);
				perfil.setCliente(true);
				perfilDao.save(perfil);
				
				perfil = new Perfil();
				perfil.setAtivo(true);
				perfil.setCodigo("3");
				perfil.setNome("Funcionário");
				perfil.setAdmin(false);
				perfil.setFuncionario(true);
				perfil.setCliente(false);
				perfilDao.save(perfil);
				
				
			}
			if(pl == null || pl.size() == 0) {
				Preco pr = new Preco();
				pr.setCodigo("1");
				pr.setNome("Progressiva");
				pr.setPreco(99.99);
				pr.setAtivo(true);
				precoDao.save(pr);
				pr = new Preco();
				pr.setCodigo("2");
				pr.setNome("Unha");
				pr.setAtivo(true);
				pr.setPreco(99.99);
				precoDao.save(pr);
				pr = new Preco();
				pr.setCodigo("3");
				pr.setNome("Pé");
				pr.setAtivo(true);
				pr.setPreco(99.99);
				precoDao.save(pr);
				pr = new Preco();
				pr.setCodigo("4");
				pr.setNome("Mão");
				pr.setAtivo(true);
				pr.setPreco(99.99);
				precoDao.save(pr);
			}
			
			
			// Excluir ----------------------------------------------------------------------------------------------------
			if(u == null || u.size() == 0) {
				// Henrique
				Usuario h = new Usuario();
				h.setAtivo(true);
				h.setMatricula("adm");
				h.setCpf("22233344455");
				h.setEmail("teste@teste.com");
				h.setSenha("adm");
				h.setNome("Henrique");
				h.setTelefone("(11)98931-6271");
				h.setCelular("(11)98931-6271");
				h.setEndereco("Teste...");
				h.setCep("00000-000");
				h.setBairro("Jd da Alegria");
				h.setDataNascimento(LocalDate.now());
				h.setBairro("São Paulo");
				h.setEstado("SP");
				h.setPerfil(perfilDao.buscarAdm().get(0));
				usuarioDao.save(h);
				
				Usuario d = new Usuario();
				d.setAtivo(true);
				d.setMatricula("123");
				d.setCpf("11122233344");
				d.setEmail("teste@teste.com");
				d.setSenha("123");
				d.setNome("Douglas");
				d.setTelefone("(11)99999-9999");
				d.setCelular("(11)99999-9999");
				d.setEndereco("Teste...");
				d.setCep("00000-000");
				d.setBairro("Jd da Alegria");
				d.setCidade("São Paulo");
				d.setEstado("SP");
				d.setDataNascimento(LocalDate.now());
				d.setPerfil(perfilDao.buscarFuncionario().get(0));
				d.setPathImagem("https://instagram.fcgh11-1.fna.fbcdn.net/v/t51.2885-19/s150x150/121259006_145932033910851_5986443377023843247_n.jpg?_nc_ht=instagram.fcgh11-1.fna.fbcdn.net&_nc_ohc=K7QuPMx_HTsAX9F8mer&tp=1&oh=4f66c284e537eb8c6a37a16ecfa2d339&oe=605329B4");
				usuarioDao.save(d);
				
				// Rafael
				Usuario r = new Usuario();
				r.setAtivo(true);
				r.setMatricula("456");
				r.setCpf("11122233355");
				r.setEmail("teste@teste.com");
				r.setSenha("456");
				r.setNome("Rafael");
				r.setTelefone("(11)99999-9999");
				r.setCelular("(11)99999-9999");
				r.setEndereco("Teste...");
				r.setCep("00000-000");
				r.setBairro("Jd da Alegria");
				r.setCidade("São Paulo");
				d.setDataNascimento(LocalDate.now());
				r.setEstado("SP");
				r.setPathImagem("https://instagram.fcgh11-1.fna.fbcdn.net/v/t51.2885-19/s150x150/147640101_432656427934162_7502532548051698688_n.jpg?_nc_ht=instagram.fcgh11-1.fna.fbcdn.net&_nc_ohc=1AwA0-HsIfEAX_ePNx8&tp=1&oh=c494c72522c18470b00e66fa92c9e1b7&oe=6052EE90");
				r.setPerfil(perfilDao.buscarFuncionario().get(0));
				usuarioDao.save(r);

			}
			
			//Horario
			if(consultas.size() == 0) {
				Consulta hor = new Consulta();
				hor.setCliente("Einstein");
				hor.setData(LocalDateTime.now());
				hor.setInicio(LocalDateTime.now());
				hor.setFim(LocalDateTime.now());
				hor.setObservacoes("Alguma...");
				hor.setPreco(10.33);
				hor.setProfissional(usuarioDao.buscarFuncionarios().get(0));
				hor.setServico(precoDao.buscarTudo().get(0));
				consultaDao.save(hor);
			}
			// Excluir ----------------------------------------------------------------------------------------------------
			
			logado = false;
			String link = "index";
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irÃ¡ acessar
			if(!senhaIncorreta.equals("")) {
				modelAndView.addObject("senhaIncorreta", senhaIncorreta);
				senhaIncorreta = "";
			}
			
			return modelAndView; //retorna a variavel
		}
		
		public String verificaLink(String link) {
			atualizarPagina = null;
			String direcao = "/pages/deslogar";
			if(logado) {
				direcao = link;
			} else {
				direcao = "/pages/deslogar";
			}
			return direcao;
		}
		
		@RequestMapping(value = "/deslogar", method = {RequestMethod.POST, RequestMethod.GET}) // Link que irÃ¡ acessar...
		public ModelAndView deslogar() { //Funcao e alguns valores que recebe...
			ModelAndView modelAndView = new ModelAndView("/pages/deslogar"); //JSP que irao acessar
			usuarioSessao = null;
			logado = false;
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/deletando", method = {RequestMethod.GET, RequestMethod.POST}) // Pagina de Alteração de Perfil
		public ModelAndView deletando(String tabela,Integer id) { //Função e alguns valores que recebe...
			paginaAtual = "Clientes";
			iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
			String link = verificaLink("pages/clientes");
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			
			if(logado) {
				//Caso esteja logado.
				if(tabela.equals("usuario")) {
					modelAndView = new ModelAndView(link);
					paginaAtual = "Clientes";
					Usuario objeto = usuarioDao.findById(id).get();
					objeto.setAtivo(false);
					usuarioDao.save(objeto);
					List<Usuario> usuarios = usuarioDao.buscarTudo();
					modelAndView.addObject("usuarios", usuarios);
					List<Preco> grupos = precoDao.buscarTudo();
					modelAndView.addObject("grupos", grupos);
					atualizarPagina = "/clientes";
				}
				if(tabela.equals("funcionario")) {
					modelAndView = new ModelAndView(link);
					paginaAtual = "Funcionários";
					Usuario objeto = usuarioDao.findById(id).get();
					objeto.setAtivo(false);
					usuarioDao.save(objeto);
					List<Usuario> usuarios = usuarioDao.buscarFuncionarios();
					modelAndView.addObject("usuarios", usuarios);
					List<Preco> grupos = precoDao.buscarTudo();
					modelAndView.addObject("grupos", grupos);
					atualizarPagina = "/funcionarios";
				}
				if(tabela.equals("precos")) {
					link = verificaLink("pages/precos");
					modelAndView = new ModelAndView(link);
					paginaAtual = "Cadastrar novo Preço";
					precoDao.delete(precoDao.findById(id).get());
					List<Preco> pl = precoDao.buscarTudo();
					modelAndView.addObject("precos", pl);
					atualizarPagina = "/precos";
				}
				if(tabela.equals("consultas")) {
					link = verificaLink("pages/minhaAgenda");
					modelAndView = new ModelAndView(link);
					paginaAtual = "Minha Agenda";
					consultaDao.delete(consultaDao.findById(id).get());
					List<Consulta> pl = consultaDao.buscarTudo();
					modelAndView.addObject("consultas", pl);
					atualizarPagina = "/minhaAgenda";
				}
			}
			modelAndView.addObject("atualizarPagina", atualizarPagina);
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			return modelAndView; 
		}
		
		
		
		/* SALVAR EXCEL */
		@RequestMapping(value = "/upload/excel", method = {RequestMethod.POST, RequestMethod.GET}) // Pagina de Alteração de Perfil
		public ModelAndView uploadExcel(Model model, String tabelaUsada, @ModelAttribute MultipartFile file) throws Exception, IOException { //Função e alguns valores que recebe...
			paginaAtual = "Home";
			iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
			String link = verificaLink("pages/home");
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			if(logado) {
				/* USAR DEPOIS
				ProcessaExcel processaExcel = new ProcessaExcel();
				List<Tabela> tabelas = processaExcel.uploadExcel(file);
		    	String conteudo="";
		   		Integer finalLinha = 0;
		   		Aula a = new Aula();
		   		int coluna = 0;
				switch (tabelaUsada) {  
				 case "aulas" : // CASO SEJA AULAS ---------------------
					 	link = verificaLink("pages/aulas");
					 	modelAndView = new ModelAndView(link);
					 	paginaAtual = "Aulas";
						iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
					 	try {
					 		aulaDao.deleteAll();
					 		String inicio="";
			   				String fim="";
				   			for(int i=0; i < tabelas.size(); i++) {
				   				coluna = tabelas.get(i).getColuna();
				   				conteudo = tabelas.get(i).getConteudo();
				   				if(coluna == 0) inicio = conteudo;
				   				if(coluna == 1) fim = conteudo;
				   				
				   				if(coluna == 2) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Segunda");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 3) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Terça");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 4) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Quarta");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 5) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Quinta");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 6) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Sexta");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 7) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Sábado");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				if(coluna == 8) {
				   					a = new Aula();
				   					a.setInicio(inicio);
				   					a.setFim(fim);
				   					a.setDiaSemana("Domingo");
				   					a.setNomeAula(conteudo);
				   					aulaDao.save(a);
				   				}
				   				
				   				if(finalLinha >= 8) {
				   					finalLinha = -1;
				   				}
				   				finalLinha++;
				   			}
				   		} catch(Exception e) {
				   			System.out.println("Erro: "+ e);
				   		}
					 	List<Aula> aulas = aulaDao.buscarTudo();
					 	modelAndView.addObject("aulas", aulas);
					 	List<Aula> h = aulaDao.buscarhorarios();
					 	List<Horario> horarios = new ArrayList<Horario>();
					 	String ultimoHorarioInicio = "";
					 	String ultimoHorarioFim = "";
					 	for(Aula aul : h) {
					 		if(!((ultimoHorarioInicio.equals(aul.getInicio())) && (ultimoHorarioFim.equals(aul.getFim())))) {
					 			ultimoHorarioInicio = aul.getInicio();
					 			ultimoHorarioFim = aul.getFim();
					 			Horario hr = new Horario();
					 			hr.setInicio(aul.getInicio());
					 			hr.setFim(aul.getFim());
					 			horarios.add(hr);
					 		}
					 	}
					 	modelAndView.addObject("horarios", horarios);
					}
			*/
			}
			
			return modelAndView; //retorna a variavel	
		}
		
		
		@RequestMapping(value = "/home", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView home(@RequestParam(value = "usuarioVal", defaultValue = "") String usuarioVal, @RequestParam(value = "senhaVal", defaultValue = "") String senhaVal) throws SQLException {
			String link = verificaLink("home");
			List<Consulta> consultas = new ArrayList<Consulta>();
			Integer confirmada = 0;
			Integer recusada = 0;
			Integer clientes = usuarioDao.buscarClientes().size();
			Integer pendentes = consultaDao.buscarPendentes().size();

			itemMenu = link;
			if(usuarioSessao == null) {
				Usuario u = usuarioDao.fazerLogin(usuarioVal, senhaVal);
				usuarioSessao = u;
			}
			if((usuarioSessao != null) || logado) {
				logado=true;
				paginaAtual = "Consulta";
				iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
				link = verificaLink("pages/home"); //Colocar regra se for ADM ou Aluno.
				consultas = consultaDao.buscarMinhaAgendaOrdenadaData(usuarioSessao.getId());
								
				if(usuarioSessao.getPerfil().getFuncionario()) {
					for(Consulta c : consultas) {
						if(c.getProfissional() == usuarioSessao) {
							if(c.getConfirmado()) confirmada++;
							if(c.getCancelado()) recusada++;
							pendentes = consultaDao.buscaMeusPendentes(usuarioSessao.getId()).size();
						}
					}
				}
				if(usuarioSessao.getPerfil().getCliente()) {
					for(Consulta c : consultas) {
						if(c.getConfirmado()) confirmada++;
						if(c.getCancelado()) recusada++;
					}
				}
				
			} else {
				logado=false;
				link = verificaLink("pages/deslogar"); 
			}
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			modelAndView.addObject("confirmada", confirmada);
			modelAndView.addObject("recusada", recusada);
			modelAndView.addObject("pendentes", pendentes);
			modelAndView.addObject("clientes", clientes);
			modelAndView.addObject("consultas", consultas);
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			if(logado) {
				if(usuarioSessao.getPerfil().getAdmin()) {
					
					
				}
			} else {
				senhaIncorreta = "Usuário / Senha incorretos!";
				modelAndView.addObject("senhaIncorreta", senhaIncorreta);
			}
			return modelAndView; //retorna a variavel
		}

		
		@RequestMapping(value = "/clientes", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView clientes(Usuario cliente, String acao ) throws SQLException, ParseException {
			paginaAtual = "Clientes";
			iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
			String link = verificaLink("pages/clientes");
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			
			List<Preco> grupos = precoDao.buscarTudo();
			modelAndView.addObject("grupos", grupos);			
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			
			
			if(logado) {
				//Gerando matrícula aleatória
				String matriculaPadrao = gerarMatricula();
				modelAndView.addObject("matriculaPadrao", matriculaPadrao);
				
				Boolean repetido = false;
				if(usuarioDao.buscarClientesRepetidos(cliente.getMatricula(), cliente.getCpf()).size() > 0) {
					repetido = true;
				}
				if(cliente.getMatricula() != null && (acao.equals("salvar")) && !repetido) {
					try {
						atualizarPagina = "/clientes";
				    	Usuario a = new Usuario();
						a = cliente;
						a.setSenha(cliente.getCpf().replace(".", "").replace("-", ""));
						a.setPerfil(perfilDao.buscarSomenteCliente().get(0));
						usuarioDao.save(a);
						usuarioDao.save(a);
						String msg = "Solicitação confirmada com sucesso!";
						modelAndView.addObject("mensagem", msg);
						modelAndView.addObject("tipoMensagem", "info");
					} catch(Exception e) {
					modelAndView.addObject("erro", e);
					}
				} else if (cliente.getMatricula() != null && (acao.equals("atualizar")) && repetido){
					Usuario a = usuarioDao.buscarMatricula(cliente.getMatricula());
					a.setNome(cliente.getNome());
					a.setDataNascimento(cliente.getDataNascimento());
					a.setTelefone(cliente.getTelefone());
					a.setCelular(cliente.getCelular());
					a.setEndereco(cliente.getEndereco());
					a.setEmail(cliente.getEmail());
					a.setPathImagem(cliente.getPathImagem());
					a.setCep(cliente.getCep());
					a.setBairro(cliente.getBairro());
					a.setCidade(cliente.getCidade());
					a.setEstado(cliente.getEstado());
					usuarioDao.save(a);
					String msg = "Atualização confirmada com sucesso!";
					modelAndView.addObject("mensagem", msg);
					modelAndView.addObject("tipoMensagem", "info");
					
				} else if(cliente.getMatricula() != null && (acao.equals("salvar")) && repetido) {
					modelAndView.addObject("mensagem", "Já existe este CPF / Matrícula.");
					modelAndView.addObject("tipoMensagem", "erro");
				}
				modelAndView.addObject("atualizarPagina", atualizarPagina);
				List<Usuario> usuarios = usuarioDao.buscarClientes();
				modelAndView.addObject("usuarios", usuarios);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/funcionarios", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView funcionarios(Usuario funcionario, String acao, String perfil_codigo, String grupo_codigo) throws SQLException {
			paginaAtual = "Funcionários";
			iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
			String link = verificaLink("pages/funcionarios");
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			if(logado) {
				//Gerando matrícula aleatória
				String matriculaPadrao = gerarMatricula();
				modelAndView.addObject("matriculaPadrao", matriculaPadrao);
				
				Boolean repetido = false;
				if(usuarioDao.buscarFuncionariosRepetidos(funcionario.getMatricula(), funcionario.getCpf()).size() > 0) {
					repetido = true;
				}
				if(funcionario.getMatricula() != null && (acao.equals("salvar")) && !repetido) {
					try {
						atualizarPagina = "/funcionarios";
						Usuario a = new Usuario();
						a = funcionario;
						a.setSenha(funcionario.getCpf().replace(".", "").replace("-", ""));
						if(usuarioSessao.getPerfil().getAdmin()) {
							a.setPerfil(perfilDao.buscarCodigo(perfil_codigo));
						} else {
							a.setPerfil(perfilDao.buscarFuncionario().get(0));
						}
						usuarioDao.save(a);
						String msg = "Solicitação confirmada com sucesso!";
						modelAndView.addObject("mensagem", msg);
						modelAndView.addObject("tipoMensagem", "info");
					} catch(Exception e) {
						modelAndView.addObject("erro", e);
						System.out.println("Erro: "+e);
					}
				} else if (funcionario.getMatricula() != null && (acao.equals("atualizar")) && repetido){
					Usuario a = usuarioDao.buscarMatricula(funcionario.getMatricula());
					a.setNome(funcionario.getNome());
					a.setDataNascimento(funcionario.getDataNascimento());
					a.setTelefone(funcionario.getTelefone());
					a.setCelular(funcionario.getCelular());
					a.setEndereco(funcionario.getEndereco());
					a.setEmail(funcionario.getEmail());
					a.setPathImagem(funcionario.getPathImagem());
					a.setCep(funcionario.getCep());
					a.setBairro(funcionario.getBairro());
					a.setCidade(funcionario.getCidade());
					a.setEstado(funcionario.getEstado());
					a.setPerfil(perfilDao.buscarCodigo(perfil_codigo));
					usuarioDao.save(a);
					String msg = "Atualização confirmada com sucesso!";
					modelAndView.addObject("mensagem", msg);
					modelAndView.addObject("tipoMensagem", "info");
				} else if(funcionario.getMatricula() != null && (acao.equals("salvar")) && repetido) {
					modelAndView.addObject("mensagem", "Já existe este CPF / Matrícula.");
					modelAndView.addObject("tipoMensagem", "erro");    
				}
				List<Usuario> usuarios = usuarioDao.buscarFuncionarios();
				modelAndView.addObject("usuarios", usuarios);
				
				List<Preco> grupos = precoDao.buscarTudo();
				modelAndView.addObject("grupos", grupos);
				
			}
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/precos", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView grupos(String acao, Preco precos, String precoValor, Integer idValor) throws SQLException {
			paginaAtual = "Preços";
			iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
			String link = verificaLink("pages/precos");
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			if(logado) {
				//... Salvando dados.
				if(acao != null) {
					Preco p = new Preco();
					if(acao.equals("atualizar")) {
						p = precoDao.findById(idValor).get();
					}
					p.setAtivo(true);
					p.setPreco(Double.parseDouble(precoValor.replace(",", ".")));
					p.setNome(precos.getNome());
					precoDao.save(p);
					String msg = "Preço cadastrado com sucesso!";
					modelAndView.addObject("mensagem", msg);
					modelAndView.addObject("tipoMensagem", "info");
				}
				atualizarPagina = "/precos";
				List<Preco> gruposTodos = precoDao.buscarTudo();
				modelAndView.addObject("grupos", gruposTodos);
				modelAndView.addObject("paginaAtual", paginaAtual);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/alterarSenha", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView alterarSenha(Integer acao, String matricula,String senha,String novaSenha,String confirmaSenha) throws SQLException {
			paginaAtual = "Alterar Senha";
			iconePaginaAtual = "fa fa-key"; //Titulo do menuzinho.
			String link = verificaLink("pages/alterarSenha");
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			if(logado) {
				String msg = "";
				//... Salvando dados.
				if(acao != null) {
					if(acao > 0) {
						Usuario u = usuarioDao.fazerLogin(matricula, senha);
						if(u != null && (novaSenha.equals(confirmaSenha)) ) {
							u.setSenha(novaSenha);
							usuarioDao.save(u);
							msg = "Senha alterada com sucesso!";
							modelAndView.addObject("mensagem", msg);
							modelAndView.addObject("tipoMensagem", "info");
						} else {
							msg = "Usuário / Senha inválida!";
							modelAndView.addObject("mensagem", msg);
							modelAndView.addObject("tipoMensagem", "erro");
						}
					}
				}
			}
			return modelAndView; //retorna a variavel
		}
		
		
		
		@RequestMapping(value = "/agendamento", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView agendamento(Boolean salvar, Boolean proximo, Boolean anterior, Integer mesAtual,   String nomeCliente, String dataSubmit, String horaEscolhida, String servicoSelecionado, String profissionalSelecionado, String precoSubmit, String obs ) throws SQLException, ParseException {
			//Salvando ------------------------------------------------------------
			String msg = "";
			Boolean erro = false;
			try {
				Integer i = Integer.parseInt(servicoSelecionado);
			}catch(Exception e) {
				if(profissionalSelecionado != null) {
					salvar = null;
					msg = "Serviço não selecionado.";
					erro = true;
				}
			}
			if(salvar!= null && salvar) {
				System.out.println("nomeCliente: "+nomeCliente);
				System.out.println("dataSubmit: "+dataSubmit);
				System.out.println("horaEscolhida: "+horaEscolhida);
				System.out.println("servicoSelecionado: "+servicoSelecionado);
				System.out.println("profissionalSelecionado: "+profissionalSelecionado);
				System.out.println("precoTexto: "+precoSubmit);
				System.out.println("obs: "+obs);

				Consulta c = new Consulta ();
				c.setAtivo(true);
				c.setCancelado(false);
				c.setCliente(nomeCliente);
				if(usuarioSessao.getPerfil().getCliente() && !usuarioSessao.getPerfil().getAdmin() && !usuarioSessao.getPerfil().getFuncionario()) {
					c.setClienteSistema(usuarioSessao);
				}
				c.setConfirmado(false);
				
				String str = dataSubmit+" 00:00";
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
				c.setData(dateTime);
				
				str = dataSubmit+" "+horaEscolhida;
				dateTime = LocalDateTime.parse(str, formatter);
				c.setInicio(dateTime);
				c.setFim(dateTime);
				c.setPreco(precoDao.findById(Integer.parseInt(servicoSelecionado)).get().getPreco());
				if(!profissionalSelecionado.equals("")) {
					c.setProfissional(usuarioDao.findById(Integer.parseInt(profissionalSelecionado)).get());
				}
				c.setServico(precoDao.findById(Integer.parseInt(servicoSelecionado)).get());
				c.setObservacoes(obs);
				
				//Favor validar se este profissional possui data disponivel neste periodo antes de salvar
				String s = dataSubmit.substring(6, 10) + "-" + dataSubmit.substring(3, 5) + "-" + dataSubmit.substring(0, 2);
				List<Consulta> consultas = consultaDao.buscarInvalidos(usuarioSessao.getId(),s,horaEscolhida);
				
				
				if(consultas.size() == 0) {
					consultaDao.save(c);
					msg = "Solicitado a reserva.";
					erro = false;
				} else {
					msg = "Data e Horário inválidos.";
					erro = true;
				}
				
				
				
			}
			//Salvando ------------------------------------------------------------
			
			
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
		    Calendar calendar = new GregorianCalendar();
		    
			if(proximo != null && proximo) {
				mesSelecionado = mesSelecionado+1;
				if(mesSelecionado > 12) {
					mesSelecionado = 1;
					anoSelecionado = anoSelecionado + 1;
				}
			}
			if(anterior != null && anterior) {
				mesSelecionado = mesSelecionado-1;
				if(mesSelecionado < 1) {
					mesSelecionado = 12;
					anoSelecionado = anoSelecionado - 1;
				}
			}
			if((proximo == null && anterior == null) || calendar.get(Calendar.MONTH) == (mesSelecionado-1) ) {
				proximo = null;
				anterior = null;
				resetaMes();
			}
			
			paginaAtual = "Agendamento";
			iconePaginaAtual = "fa fa-calendar"; //Titulo do menuzinho.
			String link = verificaLink("pages/agendamento");
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			if(logado) {
				int maxDiasMes = 28;
				List<Integer> listaDias = new ArrayList<Integer>();
				
				int ano = anoSelecionado;
			    int mes = 0;
			    int dia = 1;
			    
			    if(proximo == null && anterior == null ) {
			    	mes = calendar.get(Calendar.MONTH);
			    	mes++;
			    	dia = calendar.get(Calendar.DAY_OF_MONTH);
			    } else {
			    	mes = mesSelecionado;
			    	dia = 1;
			    }
			     
			    String semana = "";
			    String mesStr = "";
			    
			    //String dia val
			    String diaVal = "";
			    if(dia < 10) {
			    	diaVal = "0"+dia;
			    } else {
			    	diaVal = ""+dia;
			    }
			    
			    //Primeiro dia da semana do mes:
			    String diaPrimeiroSemana = "---";
			    GregorianCalendar gc = new GregorianCalendar();
			    GregorianCalendar primeiro = new GregorianCalendar();
			    if((proximo == null && anterior == null) || calendar.get(Calendar.MONTH) == (mesSelecionado-1) ) {
			    	gc.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(diaVal+"/"+mes+"/"+ano));
			    } else {
			    	gc.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("01/"+mes+"/"+ano));
			    }
			    int semanaVal  = gc.get(Calendar.DAY_OF_WEEK);
			    
			    primeiro.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("01/"+mes+"/"+ano));
			    switch (primeiro.get(Calendar.DAY_OF_WEEK)) {
		            case Calendar.SUNDAY:
		            	diaPrimeiroSemana = "DOM";
		                break;
		            case Calendar.MONDAY:
		            	diaPrimeiroSemana = "SEG";
		                break;
		            case Calendar.TUESDAY:
		            	diaPrimeiroSemana = "TER";
		            break;
		            case Calendar.WEDNESDAY:
		            	diaPrimeiroSemana = "QUA";
		                break;
		            case Calendar.THURSDAY:
		            	diaPrimeiroSemana = "QUI";
		                break;
		            case Calendar.FRIDAY:
		            	diaPrimeiroSemana = "SEX";
		                break;
		            case Calendar.SATURDAY:
		            	diaPrimeiroSemana = "SAB";
			    }
			    
			    switch(semanaVal) {
				    case 1:
				    	semana = "Domingo";
				    	break;
				    case 2:
				    	semana = "Segunda-Feira";
				    	break;
				    case 3:
				    	semana = "Terça-Feira";
				    	break;
				    case 4:
				    	semana = "Quarta-Feira";
				    	break;
				    case 5:
				    	semana = "Quinta-Feira";
				    	break;
				    case 6:
				    	semana = "Sexta-Feira";
				    	break;
				    case 7:
				    	semana = "Sábado";
				    	break;
				    default:
				}
			    
			    switch(mes) {
				    case 1:
				    	mesStr = "Janeiro";
				    	break;
				    case 2:
				    	mesStr = "Fevereiro";
				    	break;
				    case 3:
				    	mesStr = "Mar&ccedil;o";
				    	break;
				    case 4:
				    	mesStr = "Abril";
				    	break;
				    case 5:
				    	mesStr = "Maio";
				    	break;
				    case 6:
				    	mesStr = "Junho";
				    	break;
				    case 7:
				    	mesStr = "Julho";
				    	break;
				    case 8:
				    	mesStr = "Agosto";
				    	break;
				    case 9:
				    	mesStr = "Setembro";
				    	break;
				    case 10:
				    	mesStr = "Outubro";
				    	break;
				    case 11:
				    	mesStr = "Novembro";
				    	break;
				    case 12:
				    	mesStr = "Dezembro";
				    	break;
				    default:
			  }
			  
			    
			  
			  
			  if(proximo == null && anterior == null ) {
				  Calendar datas = new GregorianCalendar();
			      datas.set(Calendar.MONTH, Calendar.MONTH);//2
			      maxDiasMes = datas.getActualMaximum (Calendar.DAY_OF_MONTH);
			      for(int i = 1; i <= maxDiasMes; i++) {
			        	listaDias.add(i);
			      }
			  } else {
				  GregorianCalendar gcb = new GregorianCalendar();
				  gcb.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("01/"+mes+"/"+ano));
			      maxDiasMes = gcb.getActualMaximum (Calendar.DAY_OF_MONTH);
			      for(int i = 1; i <= maxDiasMes; i++) {
			        	listaDias.add(i);
			      }
			  }
			    
		        
			  
		        
		      System.out.println("Ano \t\t: " + ano);
		      System.out.println("Mês \t\t: " + mes);
		      System.out.println("MêsStr \t\t: " + mesStr);
			  System.out.println("Dia \t\t: " + dia);
			  System.out.println("Dia da Semana \t: " + semana);
			  System.out.println("Primeiro dia da Semana \t: " + diaPrimeiroSemana);
			  System.out.println("Máximo mês \t: " + maxDiasMes);
			  System.out.println("-----------------------------------------------------------------");
		      
			  
			  modelAndView.addObject("mesSelecionado", mesSelecionado);
			  modelAndView.addObject("maxDiasMes", maxDiasMes);
			  modelAndView.addObject("hoje", "Dia " + diaVal + ": " + semana);
			  modelAndView.addObject("diaVal", diaVal);
			  modelAndView.addObject("dia", dia);
			  modelAndView.addObject("mes", mesStr);
			  if(mes < 10) {
				  modelAndView.addObject("mesNumero", "0"+mes);
			  } else {
				  modelAndView.addObject("mesNumero", mes);
			  }
			  modelAndView.addObject("ano", ano);
			  modelAndView.addObject("listaDias", listaDias);
			  modelAndView.addObject("diaPrimeiroSemana", diaPrimeiroSemana);
			  
			  
			  List<Usuario> funcionarios = usuarioDao.buscarFuncionarios();
			  modelAndView.addObject("funcionarios", funcionarios);
			  List<Preco> precos = precoDao.buscarTudo();
			  modelAndView.addObject("precos", precos);
			  List<Consulta> consultas = consultaDao.buscarTudo();
			  modelAndView.addObject("consultas", consultas);
			  modelAndView.addObject("mensagem", msg);
			  if(erro) {
				  modelAndView.addObject("tipoMensagem", "erro");  
			  } else {
				  modelAndView.addObject("tipoMensagem", "info");
			  }
			  
			}
			return modelAndView; //retorna a variavel
		}
		
		
		@RequestMapping(value = "/minhaAgenda", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView minhaAgenda(String acao, String tabelaSolicitada,Integer idValor, String data_str, String inicioHora_str, String fimHora_str, String cliente_str, String servico_str, String preco_str, String observacao_str) throws SQLException {
			paginaAtual = "Minha Agenda";
			iconePaginaAtual = "fa fa-user"; //Titulo do menuzinho.
			String link = verificaLink("pages/minhaAgenda");
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			if(logado) {
				//... Salvando dados.
				if(acao != null) {
					if(acao.equals("salvar")) {
						Consulta c = consultaDao.findById(idValor).get();
						Boolean valido = false;
						List<Consulta> validacao = consultaDao.buscarInvalidosDuasDatas(idValor ,usuarioSessao.getId(), data_str, inicioHora_str, fimHora_str);
						if( validacao.size() == 0 ) {
							valido = true;
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
							LocalDateTime dateTime = LocalDateTime.parse(data_str+" "+inicioHora_str, formatter);
							c.setInicio(dateTime);
							dateTime = LocalDateTime.parse(data_str+" "+fimHora_str, formatter);
							c.setFim(dateTime);
							formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
							dateTime = LocalDateTime.parse(data_str+" 00:00", formatter);
							c.setData(dateTime);
						}preco_str = preco_str.replace("R$", "").replace(",", ".");
						Double va = Double.parseDouble(preco_str);
						if(va >= 0) {
							c.setPreco(va);
						} else {
							 String msg = "O Valor deve ser maior ou igual a zero.";
							 modelAndView.addObject("mensagem", msg);
							 modelAndView.addObject("tipoMensagem", "erro");  
						}
						c.setObservacoes(observacao_str);
						if(tabelaSolicitada.equals("confirmar") && valido && va >= 0) {
							c.setProfissional(usuarioSessao);
							c.setConfirmado(true);
							c.setCancelado(false);
							consultaDao.save(c);
							 String msg = "Solicitação confirmada com sucesso!";
							 modelAndView.addObject("mensagem", msg);
							 modelAndView.addObject("tipoMensagem", "info");
						}
						if(tabelaSolicitada.equals("confirmar") && !valido) {
							 String msg = "Data inválida.";
							 modelAndView.addObject("mensagem", msg);
							 modelAndView.addObject("tipoMensagem", "erro");
						}
						if(tabelaSolicitada.equals("recusar")) {
							if(c.getProfissional() != null) {
								c.setConfirmado(false);
								c.setCancelado(true);
								consultaDao.save(c);
								 String msg = "Solicitação recusada com sucesso.";
								 modelAndView.addObject("mensagem", msg);
								 modelAndView.addObject("tipoMensagem", "erro");  
							}
						}
					}
				}
				atualizarPagina = "/minhaAgenda";
				List<Consulta> consultas = consultaDao.buscarMinhaAgenda(usuarioSessao.getId());
				modelAndView.addObject("consultas", consultas);
				modelAndView.addObject("paginaAtual", paginaAtual);
			}
			return modelAndView; //retorna a variavel
		}
		
		
		
		
		
		
		
}
	
	
	




