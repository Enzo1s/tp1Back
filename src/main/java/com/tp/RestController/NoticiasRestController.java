package com.tp.RestController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tp.entity.Noticias;
import com.tp.model.NoticiasModel;
import com.tp.service.NoticiasService;

@RestController
@RequestMapping("/api/noticias")
@CrossOrigin(origins = "*")
public class NoticiasRestController {

	@Autowired
	private NoticiasService service;
	
	
	@GetMapping("/ver")
	public ResponseEntity<Noticias> ver(@RequestParam Integer id) {
		return ResponseEntity.ok(service.ver(id));
	}
	
	@GetMapping("/listado")
	public ResponseEntity<Page<Noticias>> listado(@RequestParam(required = false) Integer id, @RequestParam(required = false) String search, Pageable pageable) {
		return ResponseEntity.ok(service.listado(search, id, pageable));
	}
	
	@PostMapping("/guardar")	
	public ResponseEntity<?> guardar(NoticiasModel noticias, @ModelAttribute MultipartFile imagen) {
		try {
			return ResponseEntity.ok(service.guardar(noticias, imagen));
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Error al guardar imagen");
		}
	}
	
	@DeleteMapping("/eliminar")
	public ResponseEntity<Integer> eliminar(@RequestParam Integer id) {
		return ResponseEntity.ok(service.eliminar(id));
	}
	
	@GetMapping(value="/ver-archivo")
    public ResponseEntity<byte[]> verArchivo(@RequestParam String path) {
		try {
		return service.abrirArchivo(path);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
