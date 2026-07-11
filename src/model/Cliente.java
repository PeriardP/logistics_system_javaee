package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Cliente extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String cpf;

    public Cliente() {
    }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}