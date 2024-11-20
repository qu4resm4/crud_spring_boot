package med.voll.api.jpa.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.endereco.DadosEndereco;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
	
	private String rua;
	private String complemento;
	private String numero;

	/*
	// Construtor padrão manual
    public Endereco() {
    }
*/
    // Construtor com DadosEndereco
	public Endereco(DadosEndereco dados) {
		this.rua = dados.rua();
		this.complemento = dados.complemento();
		this.numero = dados.numero();
	}

	public void atualizarInformacoes(DadosEndereco dados) {
		if(dados.rua() != null) {
			this.rua = dados.rua();
		}
		
		if(dados.complemento() != null) {
			this.complemento = dados.complemento();
		}
		
		if(dados.numero() != null) {
			this.numero = dados.numero();
		}
		
	}
	
}
