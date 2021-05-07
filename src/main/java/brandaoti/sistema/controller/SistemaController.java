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
import brandaoti.sistema.dao.ChamadoDao;
import brandaoti.sistema.dao.PrecoDao;
import brandaoti.sistema.dao.PerfilDao;
import brandaoti.sistema.dao.StatusChamadoDao;
import brandaoti.sistema.dao.UsuarioDao;
import brandaoti.sistema.excel.ProcessaExcel;
import brandaoti.sistema.excel.Tabela;
import brandaoti.sistema.model.Objeto;
import brandaoti.sistema.model.Perfil;
import brandaoti.sistema.model.StatusChamado;
import brandaoti.sistema.model.Assunto;
import brandaoti.sistema.model.Chamado;
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
		private StatusChamadoDao statusChamadoDao;
		@Autowired
		private ChamadoDao chamadoDao;
		
		public static Usuario usuarioSessao;
		public static String atualizarPagina = null;
		public static Boolean logado = false;
		public static String itemMenu = "home";
		public static String paginaAtual = "Dashboard";
		public static String iconePaginaAtual = "fa fa-home";
		public static Integer mesSelecionado;
		
		
		
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
		}
		
		@RequestMapping(value = {"/","/login"}, produces = "text/plain;charset=UTF-8", method = RequestMethod.GET) // Pagina de Vendas
		public ModelAndView login(@RequestParam(value = "nome", required = false, defaultValue = "Henrique Brandão") String nome) throws SQLException { //Funcao e alguns valores que recebe...
			//Caso não haja registros
			List<Usuario> u = usuarioDao.buscarTudo();
			List<Perfil> p = perfilDao.buscarTudo();
			List<Preco> pl = precoDao.buscarTudo();
			List<Assunto> as = assuntoDao.buscarTudo();
			List<StatusChamado> st = statusChamadoDao.buscarTudo();
			List<Chamado> ch = chamadoDao.buscarTudo();
			usuarioSessao = null;
			
			Calendar calendar = new GregorianCalendar();
			mesSelecionado = calendar.get(Calendar.MONTH);
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
			
			if(st == null || st.size() == 0) {
				StatusChamado s = new StatusChamado();
				s.setCodigo("1");
				s.setNome("Aberto");
				s.setAberto(true);
				statusChamadoDao.save(s);
				s = new StatusChamado();
				s.setCodigo("2");
				s.setPendente(true);
				s.setNome("Em Andamento");
				statusChamadoDao.save(s);
				s = new StatusChamado();
				s.setCodigo("3");
				s.setPendente(true);
				s.setNome("Pendente Usuário");
				statusChamadoDao.save(s);
				s = new StatusChamado();
				s.setCodigo("4");
				s.setPendente(true);
				s.setNome("Pendente Suporte");
				statusChamadoDao.save(s);
				s = new StatusChamado();
				s.setCodigo("5");
				s.setEncerrado(true);
				s.setNome("Encerrado");
				statusChamadoDao.save(s);
			}
			
			
			if(u == null || u.size() == 0) {
				// Excluir ----------------------------------------------------------------------------------------------------
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
				
				
				// Excluir ----------------------------------------------------------------------------------------------------

			}
			
			
			logado = false;
			String link = "index";
			itemMenu = link;
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irÃ¡ acessar
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
				if(tabela.equals("grupos")) {
					link = verificaLink("pages/grupos");
					modelAndView = new ModelAndView(link);
					paginaAtual = "Cadastrar novo Grupo";
					Preco objeto = precoDao.findById(id).get();
					objeto.setAtivo(false);
					precoDao.save(objeto);
					List<Preco> pl = precoDao.buscarTudo();
					modelAndView.addObject("grupos", pl);
					atualizarPagina = "/grupos";
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
			System.out.println("file: "+file);
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
			System.out.println("Validado");
			return modelAndView; //retorna a variavel	
		}
		
		
		@RequestMapping(value = "/home", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView home(@RequestParam(value = "usuarioVal", defaultValue = "") String usuarioVal, @RequestParam(value = "senhaVal", defaultValue = "") String senhaVal) throws SQLException {
			String link = verificaLink("home");
			itemMenu = link;
			if(usuarioSessao == null) {
				Usuario u = usuarioDao.fazerLogin(usuarioVal, senhaVal);
				usuarioSessao = u;
			}
			if((usuarioSessao != null) || logado) {
				logado=true;
				if(usuarioSessao.getPerfil().getAdmin()) {
					paginaAtual = "Administrador";
					iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
					link = verificaLink("pages/home"); //Colocar regra se for ADM ou Aluno.
				} else {
					paginaAtual = "Consulta";
					iconePaginaAtual = "fa fa-cogs"; //Titulo do menuzinho.
					link = verificaLink("pages/home"); //Colocar regra se for ADM ou Aluno.
				}
			} else {
				logado=false;
				link = verificaLink("pages/deslogar"); 
			}
			ModelAndView modelAndView = new ModelAndView(link); //JSP que irá acessar.
			modelAndView.addObject("usuario", usuarioSessao);
			modelAndView.addObject("paginaAtual", paginaAtual); 
			modelAndView.addObject("iconePaginaAtual", iconePaginaAtual);
			if(logado) {
				if(usuarioSessao.getPerfil().getAdmin()) {
					List<Chamado> todosChamados = chamadoDao.buscarTudo();
					List<Chamado> todosAbertos = chamadoDao.buscarAbertos();
					List<Chamado> todosVencidos= chamadoDao.buscarVencidos();
					List<Chamado> todosAndamentos = chamadoDao.buscarAndamentos();
					List<Chamado> todosEncerrados = chamadoDao.buscarEncerrados();
					
					modelAndView.addObject("todosChamados", todosChamados.size());
					modelAndView.addObject("todosAbertos", todosAbertos.size());
					modelAndView.addObject("todosVencidos", todosVencidos.size());
					modelAndView.addObject("todosAndamentos", todosAndamentos.size());
					modelAndView.addObject("todosEncerrados", todosEncerrados.size());
					
				}
				
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
					a.setGrupo(cliente.getGrupo());
					usuarioDao.save(a);
					
				} else if(cliente.getMatricula() != null && (acao.equals("salvar")) && repetido) {
					modelAndView.addObject("erro", "Já existe este CPF / Matrícula.");
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
						a.setPerfil(perfilDao.buscarCodigo(perfil_codigo));
						a.setGrupo(precoDao.buscarCodigo(grupo_codigo));
						usuarioDao.save(a);
						modelAndView.addObject("atualizarPagina", atualizarPagina);
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
					a.setGrupo(funcionario.getGrupo());
					a.setPerfil(perfilDao.buscarCodigo(perfil_codigo));
					a.setGrupo(precoDao.buscarCodigo(grupo_codigo));
					usuarioDao.save(a);
				} else if(funcionario.getMatricula() != null && (acao.equals("salvar")) && repetido) {
					modelAndView.addObject("erro", "Já existe este CPF / Matrícula.");
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
							msg = "Alterado com sucesso.";
							modelAndView.addObject("msgOk", msg);
						} else {
							msg = "Usuário ou senha inválidos.";
							modelAndView.addObject("msg", msg);
						}
					}
				}
			}
			return modelAndView; //retorna a variavel
		}
		
		
		
		@RequestMapping(value = "/agendamento", produces = "text/plain;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST}) // Pagina de Vendas
		public ModelAndView agendamento(Boolean proximo, Boolean anterior, Integer mesAtual  ) throws SQLException, ParseException {
			if(proximo != null && proximo) {
				mesSelecionado = mesSelecionado+1;
				if(mesSelecionado > 12) {
					mesSelecionado = 1;
				}
			}
			if(anterior != null && anterior) {
				mesSelecionado = mesSelecionado-1;
				if(mesSelecionado < 1) {
					mesSelecionado = 12;
				}
			}
			if(proximo == null && anterior == null ) {
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
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
			    Calendar calendar = new GregorianCalendar();
			    int ano = calendar.get(Calendar.YEAR);
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
			     
			    int semanaVal  = calendar.get(Calendar.DAY_OF_WEEK);
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
			    gc.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("01/"+mes+"/"+ano));
			    
			    
			    switch (gc.get(Calendar.DAY_OF_WEEK)) {
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
			  modelAndView.addObject("ano", ano);
			  modelAndView.addObject("listaDias", listaDias);
			  modelAndView.addObject("diaPrimeiroSemana", diaPrimeiroSemana);
			    
			}
			return modelAndView; //retorna a variavel
		}
		
}
	
	
	




