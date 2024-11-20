package med.voll.api.medicos;

public record DadosListagemMedico(
		Long id,
		String nome,
		Especializacao especializacao
		) {
	
	public DadosListagemMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEspecializacao());
	}
}
