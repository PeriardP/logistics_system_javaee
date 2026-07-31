package service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import logistica.modelo.Encomenda;

@Stateless
public class EncomendaService extends GenericService<Encomenda> {

    @PersistenceContext
    private EntityManager em;

    public EncomendaService() {
        super(Encomenda.class);
    }

    // Metodo usado pela tela de Registro de Encomendas 
    public List<Encomenda> pesquisarTodas() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Encomenda> cq = cb.createQuery(Encomenda.class);
        Root<Encomenda> root = cq.from(Encomenda.class);
        
        cq.select(root);
        
        return em.createQuery(cq).getResultList();
    }

    // Metodo usado pela tela de Relatórios
    public List<Encomenda> relatorioEntregas(Long idEntregador, String cidade, Double valorMaximo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Encomenda> cq = cb.createQuery(Encomenda.class);
        Root<Encomenda> root = cq.from(Encomenda.class);
        
        List<Predicate> predicados = new ArrayList<Predicate>();
        
        if (idEntregador != null) {
            predicados.add(cb.equal(root.get("entregador").get("id"), idEntregador));
        }
        
        if (cidade != null && !cidade.trim().isEmpty()) {
            predicados.add(cb.like(root.get("cliente").get("endereco").get("cidade"), "%" + cidade + "%"));
        }
        
        if (valorMaximo != null) {
            predicados.add(cb.lessThan(root.get("valor"), valorMaximo));
        }
        
        if (!predicados.isEmpty()) {
            cq.where(cb.and(predicados.toArray(new Predicate[0])));
        }
        
        cq.orderBy(cb.asc(root.get("cliente").get("nome")));
        
        return em.createQuery(cq).getResultList();
    }
}