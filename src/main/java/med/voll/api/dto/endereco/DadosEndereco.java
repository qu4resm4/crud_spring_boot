package med.voll.api.dto.endereco;

import jakarta.validation.constraints.NotBlank;

public record DadosEndereco(
		
		@NotBlank
		String rua,
		
		@NotBlank
		String complemento,
		
		@NotBlank
		String numero
		) {}
