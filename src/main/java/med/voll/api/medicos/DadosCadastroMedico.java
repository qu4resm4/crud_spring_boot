package med.voll.api.medicos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.dto.endereco.DadosEndereco;

public record DadosCadastroMedico(
		
		@NotBlank
		String nome,
		
		@NotNull
		@Valid
		DadosEndereco endereco,
		
		@NotNull
		Especializacao especializacao,
		
		@NotBlank
		String telefone
		
		) {

}
