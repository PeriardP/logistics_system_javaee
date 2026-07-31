package controler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import logistica.modelo.Encomenda;
import logistica.modelo.Entregador;
import service.EncomendaService;
import service.EntregadorService;

@ViewScoped
@ManagedBean
public class RelatorioBean {

    private List<Encomenda> encomendas = new ArrayList<Encomenda>();
    private List<Entregador> entregadores = new ArrayList<Entregador>();
    
    private Long idEntregadorFiltro;
    private String cidadeFiltro = "";
    private Double valorMaximoFiltro;

    @EJB
    private EncomendaService encomendaService;
    
    @EJB
    private EntregadorService entregadorService;

    @PostConstruct
    public void iniciar() {
        // Carrega a lista de entregadores 
        entregadores = entregadorService.pesquisarPorNome("");
        pesquisar(); // Carrega a tabela inicial
    }

    // Aciona a consulta no banco 
    public void pesquisar() {
        encomendas = encomendaService.relatorioEntregas(idEntregadorFiltro, cidadeFiltro, valorMaximoFiltro);
    }

    // Resetar a tela para novo uso
    public void limpar() {
        idEntregadorFiltro = null;
        cidadeFiltro = "";
        valorMaximoFiltro = null;
        pesquisar(); 
    }

    
    public List<Encomenda> getEncomendas() { return encomendas; }
    public void setEncomendas(List<Encomenda> encomendas) { this.encomendas = encomendas; }

    public List<Entregador> getEntregadores() { return entregadores; }
    public void setEntregadores(List<Entregador> entregadores) { this.entregadores = entregadores; }

    public Long getIdEntregadorFiltro() { return idEntregadorFiltro; }
    public void setIdEntregadorFiltro(Long idEntregadorFiltro) { this.idEntregadorFiltro = idEntregadorFiltro; }

    public String getCidadeFiltro() { return cidadeFiltro; }
    public void setCidadeFiltro(String cidadeFiltro) { this.cidadeFiltro = cidadeFiltro; }

    public Double getValorMaximoFiltro() { return valorMaximoFiltro; }
    public void setValorMaximoFiltro(Double valorMaximoFiltro) { this.valorMaximoFiltro = valorMaximoFiltro; }
}