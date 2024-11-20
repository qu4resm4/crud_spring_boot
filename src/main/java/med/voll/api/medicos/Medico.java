package med.voll.api.medicos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.jpa.endereco.Endereco;


@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String nome;
    
    @Enumerated(EnumType.STRING)
    private Especializacao especializacao;
    
    @Embedded
    private Endereco endereco;
    
    private String telefone;
    
    private Boolean ativo;
    
	public Medico(DadosCadastroMedico dados) {
		this.ativo = true;
		this.nome = dados.nome();
		this.endereco = new Endereco(dados.endereco());
		this.especializacao = dados.especializacao();
		this.telefone = dados.telefone();
		}

	public void atualizarInformacoes(DadosAtualizarMedico dados) {
		if(dados.nome() != null) {
			this.nome = dados.nome();
		}
		
		if(dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		
		if(dados.endereco() != null) {
			this.endereco.atualizarInformacoes(dados.endereco());
		}
		
	}

	public void excluir(Long id) {
		this.ativo = false;
		
	}

}
