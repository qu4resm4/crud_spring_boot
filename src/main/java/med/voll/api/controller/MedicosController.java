package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import jakarta.validation.Valid;
import med.voll.api.medicos.DadosAtualizarMedico;
import med.voll.api.medicos.DadosCadastroMedico;
import med.voll.api.medicos.DadosDetalhamentoMedico;
import med.voll.api.medicos.DadosListagemMedico;
import med.voll.api.medicos.Medico;
import med.voll.api.medicos.MedicoRepository;

@RestController
@RequestMapping("medicos")
public class MedicosController {
	
	@Autowired
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "medicos")
	public ResponseEntity cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
		var medico = new Medico(dados);
		repository.save(medico);
		
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		
		var dto = new DadosDetalhamentoMedico(medico);
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	/*
	 * get normal sem paginação retorna uma array de jsons
	@GetMapping
	public List<DadosListagemMedico> listarMedicos(){
		return repository.findAll().stream().map(DadosListagemMedico::new).toList();
	}
	*/
	/*
	 * paginacao do proprio spring, não precisa fazer o stream nem to list pq já são implementados
	 * o spring tem seus proprios parametros de rota como ?size=% & page=%
	 * */
	/*
	@GetMapping
	public Page<DadosListagemMedico> listarMedicos(Pageable paginacao){
		return repository.findAll(paginacao).map(DadosListagemMedico::new);
	}
	*/

	/*Get apenas ativos: conceito de exclusão lógica*/
	@GetMapping
	@Cacheable(value = "medicos")
	public ResponseEntity<Page<DadosListagemMedico>> listarMedicos(Pageable paginacao){
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		//return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}
	
	@PutMapping
	@Transactional
	@CacheEvict(value = "medicos")
	public ResponseEntity atualizar(@RequestBody DadosAtualizarMedico dados) {
		var medico = repository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	
	/*
	@DeleteMapping("/{id}")
	@Transactional
	public void excluir(@PathVariable Long id) {
		repository.deleteById(id);
	}
	*/
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "medicos")
	public ResponseEntity excluir(@PathVariable Long id) {
		var medico = repository.getReferenceById(id);
		medico.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity detalhar(@PathVariable Long id) {
		var medico = repository.getReferenceById(id); 
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
}
