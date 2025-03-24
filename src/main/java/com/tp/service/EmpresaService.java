package com.tp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp.entity.Empresa;
import com.tp.entity.Noticias;
import com.tp.repository.EmpresaRepository;
import com.tp.repository.NoticiasRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository repository;
	
	@Autowired
	private NoticiasRepository noticiasRepository;
	
	public Empresa guardar(Empresa empresa) {
		return repository.save(empresa);
	}
	
	public Empresa ver(Integer id) {
		return repository.findById(id).orElseThrow();
	}
	
	public List<Empresa> listado() {
		return repository.findAll();
	}
	
	public Integer eliminar(Integer id) {
		List<Noticias> noticias = noticiasRepository.listadoPorIdEmpresa(id);
		for (Noticias noticias2 : noticias) {
			repository.deleteById(noticias2.getId());
		}
		repository.deleteById(id);
		return id;
	}
}
