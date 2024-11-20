package med.voll.api.medicos;

import med.voll.api.jpa.endereco.Endereco;

public record DadosDetalhamentoMedico(
		
		Long id,
		
		String nome,
		
		Especializacao especializacao,
		
		Endereco endereco,
		
		String telefone
		
		) {

	public DadosDetalhamentoMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEspecializacao(), medico.getEndereco(), medico.getTelefone());
	}
	
}
