package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import logistica.modelo.Encomenda;

@Stateless
public class EncomendaService extends GenericService<Encomenda> {

    @PersistenceContext
    private EntityManager em;

    public EncomendaService() {
        super(Encomenda.class);
    }

    // Listar as encomendas usando o Criteria 
    public List<Encomenda> pesquisarTodas() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Encomenda> cq = cb.createQuery(Encomenda.class);
        Root<Encomenda> root = cq.from(Encomenda.class);
        
        cq.select(root);
        
        return em.createQuery(cq).getResultList();
    }
}