package com.tp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tp.entity.Noticias;

@Repository
public interface NoticiasRepository extends JpaRepository<Noticias, Integer>{
	
	@Query("SELECT n FROM Noticias n WHERE n.baja IS NULL AND n.empresa.id = :id ORDER BY n.fecha DESC")
	Page<Noticias> listadoPorIdEmpresa(@Param("id") Integer idEmpresa, Pageable pageable);
	
	@Query("SELECT n FROM Noticias n WHERE n.baja IS NULL AND n.empresa.id = :id ORDER BY n.fecha DESC")
	List<Noticias> listadoPorIdEmpresa(@Param("id") Integer idEmpres);
	
	@Query("SELECT n FROM Noticias n WHERE n.baja IS NULL AND n.empresa.id = :id "
			+ "AND (n.titulo LIKE :search OR n.resumen LIKE :search)")
	Page<Noticias> buscarPorIdEmpresaTituloResumen(@Param("id") Integer idEmpresa, @Param("search") String search, Pageable pageable);

}
