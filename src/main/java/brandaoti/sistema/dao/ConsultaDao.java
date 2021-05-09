package brandaoti.sistema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import brandaoti.sistema.model.Consulta;

public interface ConsultaDao extends JpaRepository<Consulta, Integer> {
	
	@Query(" select p from Consulta p where p.ativo = TRUE order by p.inicio asc")
	List<Consulta> buscarTudo();
	
	@Query("select p from Consulta p where convert(p.inicio, date) = convert(:dataEscolhida, date) and convert(:horaEscolhida, time) >= convert(p.inicio, time) and convert(:horaEscolhida, time) <= convert(p.fim, time)")
	List<Consulta> buscarInvalidos(@Param("dataEscolhida") String dataEscolhida, @Param("horaEscolhida") String horaEscolhida);
	
	@Query(" select p from Consulta p where p.ativo = TRUE and (p.profissional.id like (:meuID) or p.clienteSistema.id like (:meuID)) order by p.id asc")
	List<Consulta> buscarMinhaAgenda(@Param("meuID") Integer meuID);
}
