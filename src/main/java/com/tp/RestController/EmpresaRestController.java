package com.tp.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tp.entity.Empresa;
import com.tp.service.EmpresaService;

@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "*")
public class EmpresaRestController {

	@Autowired
	private EmpresaService service;
	
	
	@GetMapping("/ver")
	public ResponseEntity<Empresa> ver(@RequestParam Integer id) {
		return ResponseEntity.ok(service.ver(id));
	}
	
	@GetMapping("/listado")
	public ResponseEntity<List<Empresa>> listado() {
		return ResponseEntity.ok(service.listado());
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Empresa> guardar(@RequestBody Empresa empresa) {
		return ResponseEntity.ok(service.guardar(empresa));
	}
	
	@DeleteMapping("/eliminar")
	public ResponseEntity<Integer> eliminar(@RequestParam Integer id) {
		return ResponseEntity.ok(service.eliminar(id));
	}
}
