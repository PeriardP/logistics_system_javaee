package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import logistica.modelo.Entregador;

@Stateless
public class EntregadorService extends GenericService<Entregador> {

    @PersistenceContext
    private EntityManager em;
    
    public EntregadorService() {
        super(Entregador.class);
    }

    // Buscar por nome usando e Ordenar de forma Crescente 
    public List<Entregador> pesquisarPorNome(String texto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entregador> cq = cb.createQuery(Entregador.class);
        Root<Entregador> root = cq.from(Entregador.class);

        if (texto != null && !texto.trim().isEmpty()) {
            Predicate restricoes = cb.like(root.get("nome"), "%" + texto + "%");
            cq.where(restricoes);
        }

        cq.orderBy(cb.asc(root.get("nome")));

        TypedQuery<Entregador> query = em.createQuery(cq);
        return query.getResultList();
    }

    // Verificar se o entregador possui encomendas 
    public boolean possuiEncomendas(Long idEntregador) {
        try {
            Long count = em.createQuery(
                "SELECT COUNT(e) FROM Encomenda e WHERE e.entregador.id = :idEntregador", Long.class)
                .setParameter("idEntregador", idEntregador)
                .getSingleResult();
            
            return count > 0;
        } catch (Exception e) {
            return false; 
        }
    }
}