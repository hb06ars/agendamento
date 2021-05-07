package brandaoti.sistema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import brandaoti.sistema.model.Preco;
import brandaoti.sistema.model.Perfil;

public interface PrecoDao extends JpaRepository<Preco, Integer> {
	
	@Query(" select p from Preco p where ativo = 1 order by nome asc")
	List<Preco> buscarTudo();
	
	@Query(" select p from Preco p where upper( p.codigo ) like upper( :codigo ) and ativo = 1 ")
	Preco buscarCodigo(@Param("codigo") String codigo);
	
		
	
}
