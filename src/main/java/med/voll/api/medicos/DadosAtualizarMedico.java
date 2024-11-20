package med.voll.api.medicos;

import jakarta.validation.constraints.NotNull;
import med.voll.api.dto.endereco.DadosEndereco;

public record DadosAtualizarMedico(
		@NotNull
		Long id,
		String nome,
		DadosEndereco endereco,
		String telefone,
		Especializacao especializacao
		) {

}
