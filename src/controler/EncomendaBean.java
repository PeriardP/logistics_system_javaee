package controler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import logistica.modelo.Cliente;
import logistica.modelo.Encomenda;
import logistica.modelo.Entregador;
import service.ClienteService;
import service.EncomendaService;
import service.EntregadorService;

@ViewScoped
@ManagedBean
public class EncomendaBean {

    private Encomenda encomenda;
    private List<Encomenda> encomendas = new ArrayList<Encomenda>();
    private Boolean edicao = false;
    
    private List<Cliente> clientes = new ArrayList<Cliente>();
    private List<Entregador> entregadores = new ArrayList<Entregador>();

    private Long idClienteSelecionado;
    private Long idEntregadorSelecionado;

    @EJB
    private EncomendaService encomendaService;
    
    @EJB
    private ClienteService clienteService;
    
    @EJB
    private EntregadorService entregadorService;

    public EncomendaBean() {
        novo();
    }

    @PostConstruct
    public void iniciar() {
        clientes = clienteService.pesquisarPorNome("");
        entregadores = entregadorService.pesquisarPorNome("");
        
        pesquisarEncomendas();
    }

    public void salvar() {
        if (idClienteSelecionado != null) {
            Cliente c = clientes.stream().filter(x -> x.getId().equals(idClienteSelecionado)).findFirst().orElse(null);
            encomenda.setCliente(c);
        }
        
        if (idEntregadorSelecionado != null) {
            Entregador e = entregadores.stream().filter(x -> x.getId().equals(idEntregadorSelecionado)).findFirst().orElse(null);
            encomenda.setEntregador(e);
        }

        //  preenchimento obrigatório
        if (encomenda.getCliente() == null || encomenda.getEntregador() == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Selecione um Cliente e um Entregador!"));
            return;
        }

        if (edicao) {
            encomendaService.merge(encomenda);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Encomenda atualizada!"));
        } else {
            encomendaService.create(encomenda);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Encomenda registrada!"));
        }
        
        novo();
        pesquisarEncomendas();
    }

    public void pesquisarEncomendas() {
        encomendas = encomendaService.pesquisarTodas();
    }

    public void novo() {
        encomenda = new Encomenda();
        edicao = false;
        idClienteSelecionado = null;
        idEntregadorSelecionado = null;
    }

    public void editar(Encomenda enc) {
        this.encomenda = enc;
        this.edicao = true;
        
        this.idClienteSelecionado = enc.getCliente().getId();
        this.idEntregadorSelecionado = enc.getEntregador().getId();
    }

    public void excluir(Encomenda enc) {
        encomendaService.remove(enc);
        novo();
        pesquisarEncomendas();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Encomenda excluída!"));
    }

    
    public Encomenda getEncomenda() { return encomenda; }
    public void setEncomenda(Encomenda encomenda) { this.encomenda = encomenda; }

    public List<Encomenda> getEncomendas() { return encomendas; }
    public void setEncomendas(List<Encomenda> encomendas) { this.encomendas = encomendas; }

    public Boolean getEdicao() { return edicao; }
    public void setEdicao(Boolean edicao) { this.edicao = edicao; }

    public List<Cliente> getClientes() { return clientes; }
    public void setClientes(List<Cliente> clientes) { this.clientes = clientes; }

    public List<Entregador> getEntregadores() { return entregadores; }
    public void setEntregadores(List<Entregador> entregadores) { this.entregadores = entregadores; }

    public Long getIdClienteSelecionado() { return idClienteSelecionado; }
    public void setIdClienteSelecionado(Long idClienteSelecionado) { this.idClienteSelecionado = idClienteSelecionado; }

    public Long getIdEntregadorSelecionado() { return idEntregadorSelecionado; }
    public void setIdEntregadorSelecionado(Long idEntregadorSelecionado) { this.idEntregadorSelecionado = idEntregadorSelecionado; }
}