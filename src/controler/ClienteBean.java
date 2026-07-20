package controler;

import java.util.ArrayList;
import java.util.List;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import logistica.modelo.Cliente;


@ViewScoped
@ManagedBean

public class ClienteBean {
		
	private Cliente cliente = new Cliente();
	private List<Cliente> Clientes = new ArrayList<Cliente>(); 
	private Boolean edicao = false;
	
	
	private String texto;
	
	private void pesquisarCliente() {
		
		
	}
	
	
	
	
	
	
	
	
	

}
