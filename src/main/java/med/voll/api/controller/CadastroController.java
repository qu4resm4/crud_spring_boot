package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.usuarios.DadosAutenticacao;
import med.voll.api.usuarios.Usuario;
import med.voll.api.usuarios.UsuarioRepository;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping
	public ResponseEntity cadastrar(@RequestBody @Valid DadosAutenticacao dados) {
		var senhaCrip = passwordEncoder.encode(dados.senha());
		
		var usuario = new Usuario(dados.login(), senhaCrip);
		
		repository.save(usuario);
		
		return ResponseEntity.ok().build();
	}

}
