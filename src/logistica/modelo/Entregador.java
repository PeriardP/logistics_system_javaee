package logistica.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Entregador extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String cnh;

	@Column(nullable = false)
	private String categoriaCnh;

	public Entregador() {
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getCategoriaCnh() {
		return categoriaCnh;
	}

	public void setCategoriaCnh(String categoriaCnh) {
		this.categoriaCnh = categoriaCnh;
	}

}