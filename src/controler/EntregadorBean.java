package controler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import logistica.modelo.Endereco;
import logistica.modelo.Entregador;
import service.EntregadorService;

@ViewScoped
@ManagedBean
public class EntregadorBean {
        
    private Entregador entregador;
    private List<Entregador> entregadores = new ArrayList<Entregador>(); 
    private Boolean edicao = false;
    private String texto = "";
    
    @EJB
    private EntregadorService entregadorService;

    public EntregadorBean() {
        novo();
    }

    @PostConstruct
    public void iniciar() {
        pesquisarEntregador();
    }

    public void salvar() {
        if (edicao) {
            entregadorService.merge(entregador); 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Entregador atualizado!"));
        } else {
            entregadorService.create(entregador);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Entregador cadastrado com sucesso!"));
        }
        
        novo(); 
        pesquisarEntregador(); 
    }
        
    public void pesquisarEntregador() {
        if (texto == null) {
            texto = ""; 
        }
        entregadores = entregadorService.pesquisarPorNome(texto);
    }

    public void novo() {
        entregador = new Entregador();
        entregador.setEndereco(new Endereco()); 
        edicao = false;
        texto = ""; 
    }

    public void editar(Entregador e) {
        this.entregador = e;
        this.edicao = true;
    }

    public void excluir(Entregador e) {
        // Bloquear exclusão se tiver encomendas vinculadas
        if (e.getId() != null && entregadorService.possuiEncomendas(e.getId())) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Este entregador possui encomendas atreladas e não pode ser excluído!"));
            return; 
        }

        entregadorService.remove(e); 
        novo(); 
        pesquisarEntregador();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Entregador excluído!"));
    }
    
    public Entregador getEntregador() { return entregador; }
    public void setEntregador(Entregador entregador) { this.entregador = entregador; }
    public List<Entregador> getEntregadores() { return entregadores; }
    public void setEntregadores(List<Entregador> entregadores) { this.entregadores = entregadores; }
    public Boolean getEdicao() { return edicao; }
    public void setEdicao(Boolean edicao) { this.edicao = edicao; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
}