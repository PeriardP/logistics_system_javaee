package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import logistica.modelo.Cliente;

@Stateless
public class ClienteService extends GenericService<Cliente>{

	public ClienteService() {
		super(Cliente.class);
	}
	
public List<Cliente> pesquisarPorNome(String texto){
	
	final CriteriaBuilder cBuilder =  getEntityManager().getCriteriaBuilder();
	final CriteriaQuery<Cliente> cQuery = cBuilder.createQuery(Cliente.class);
	final Root<Cliente> rootCliente = cQuery.from(Cliente.class);
	
	cQuery.select(rootCliente);
	
	final Expression<String> expNome = rootCliente.get("nome");
	
	cQuery.where(cBuilder.like(expNome, "%"+texto+"%"));
	cQuery.orderBy(cBuilder.asc(expNome));
	
	List<Cliente> resultado = getEntityManager().createQuery(cQuery).getResultList();
	return resultado;
  }
}
	
	
	
	
	



