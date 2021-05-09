package brandaoti.sistema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import brandaoti.sistema.model.Consulta;

public interface ConsultaDao extends JpaRepository<Consulta, Integer> {
	
	@Query(" select p from Consulta p where p.ativo = TRUE order by p.inicio asc")
	List<Consulta> buscarTudo();
	
	@Query("select p from Consulta p where p.confirmado = TRUE and p.profissional.id like (:idUsuario) and convert(p.inicio, date) = convert(:dataEscolhida, date) and convert(:horaEscolhida, time) >= convert(p.inicio, time) and convert(:horaEscolhida, time) <= convert(p.fim, time)")
	List<Consulta> buscarInvalidos(@Param("idUsuario") Integer idUsuario, @Param("dataEscolhida") String dataEscolhida, @Param("horaEscolhida") String horaEscolhida);
	
	@Query(" select p from Consulta p where p.ativo = TRUE and ((p.profissional.id like (:meuID) or p.profissional.id is null) or p.clienteSistema.id like (:meuID)) order by p.id asc")
	List<Consulta> buscarMinhaAgenda(@Param("meuID") Integer meuID);
	
	@Query("select p from Consulta p where p.id not like (:idValor) and p.confirmado = TRUE and p.profissional.id like (:idUsuario) and convert(p.inicio, date) = convert(:dataEscolhida, date) and (   (convert(:horaInicio, time) BETWEEN convert(p.inicio, time) AND convert(p.fim, time) or convert(:horaFim, time) BETWEEN convert(p.inicio, time) AND convert(p.fim, time) ) or (convert(:horaInicio, time) <= convert(p.inicio, time) and convert(:horaFim, time) >= convert(p.inicio, time)) or (convert(:horaInicio, time) <= convert(p.fim, time) and convert(:horaFim, time) >= convert(p.fim, time)))")
	List<Consulta> buscarInvalidosDuasDatas(@Param("idValor") Integer idValor, @Param("idUsuario") Integer idUsuario, @Param("dataEscolhida") String dataEscolhida, @Param("horaInicio") String horaInicio, @Param("horaFim") String horaFim);
	
	@Query(" select p from Consulta p where p.ativo = TRUE and p.confirmado = FALSE and p.cancelado = FALSE")
	List<Consulta> buscarPendentes();
	
	@Query(" select p from Consulta p where p.id like (:idValor) and p.ativo = TRUE and p.confirmado = FALSE and p.cancelado = FALSE")
	List<Consulta> buscaMeusPendentes(@Param("idValor") Integer idValor);
	
	@Query(" select p from Consulta p where p.ativo = TRUE and ((p.profissional.id like (:meuID) or p.profissional.id is null) or p.clienteSistema.id like (:meuID)) order by p.data desc")
	List<Consulta> buscarMinhaAgendaOrdenadaData(@Param("meuID") Integer meuID);
}
