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
import logistica.modelo.Endereco; 
import service.ClienteService;

@ViewScoped
@ManagedBean
public class ClienteBean {
        
    private Cliente cliente;
    private List<Cliente> clientes = new ArrayList<Cliente>(); 
    private Boolean edicao = false;
    private String texto = "";
    
    @EJB
    private ClienteService clienteService;

    public ClienteBean() {
        novo();
    }

    @PostConstruct
    public void iniciar() {
        pesquisarCliente();
    }

    public void salvar() {
        // Bloqueio de CPF duplicado 
        if (!edicao && clienteService.existeCpf(cliente.getCpf())) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Este CPF já está cadastrado no sistema!"));
            return; 
        }

        if (edicao) {
            clienteService.merge(cliente); 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente atualizado!"));
        } else {
            clienteService.create(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente cadastrado com sucesso!"));
        }
        
        novo(); 
        pesquisarCliente(); 
    } 
        
    public void pesquisarCliente() {
        if (texto == null) {
            texto = ""; 
        }
        clientes = clienteService.pesquisarPorNome(texto);
    }

    public void novo() {
        cliente = new Cliente();
        cliente.setEndereco(new Endereco()); 
        edicao = false;
        texto = ""; 
    }

    public void editar(Cliente c) {
        this.cliente = c;
        this.edicao = true;
    }

    public void excluir(Cliente c) {
        clienteService.remove(c); 
        novo(); 
        pesquisarCliente();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente excluído!"));
    }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<Cliente> getClientes() { return clientes; }
    public void setClientes(List<Cliente> clientes) { this.clientes = clientes; }
    public Boolean getEdicao() { return edicao; }
    public void setEdicao(Boolean edicao) { this.edicao = edicao; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
}