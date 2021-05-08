package brandaoti.sistema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import brandaoti.sistema.model.Consulta;

public interface ConsultaDao extends JpaRepository<Consulta, Integer> {
	
	@Query(" select p from Consulta p where p.ativo = TRUE order by p.inicio asc")
	List<Consulta> buscarTudo();
	
	
}
