package com.tp.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tp.entity.Noticias;
import com.tp.model.NoticiasModel;
import com.tp.repository.EmpresaRepository;
import com.tp.repository.NoticiasRepository;

@Service
public class NoticiasService {
	
	@Value("${directorio}")
	private String directorio;
	
	@Value("${directorio}")
	private Path directorioArchivo;

	@Autowired
	private NoticiasRepository repository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public Noticias guardar(NoticiasModel noticias, MultipartFile imagen) throws IOException {
		Noticias nuevaNoticia = new Noticias();
		nuevaNoticia.setAlta(new Date());
		nuevaNoticia.setContenidoHTML(noticias.getContenidoHTML());
		nuevaNoticia.setEmpresa(empresaRepository.findById(noticias.getEmpresa()).get());
		nuevaNoticia.setFecha(noticias.getFecha());
		nuevaNoticia.setPublicada(noticias.getPublicada());
		nuevaNoticia.setResumen(noticias.getResumen());
		nuevaNoticia.setTitulo(noticias.getTitulo());
		nuevaNoticia = repository.save(nuevaNoticia);
		nuevaNoticia.setImagen(guardarImagen(nuevaNoticia, imagen));
		return repository.save(nuevaNoticia);
		
	}
	
	public Noticias ver(Integer id) {
		return repository.findById(id).orElseThrow();
	}
	
	public Page<Noticias> listado(String search, Integer id, Pageable pageable) {
		if(id != null) {
			if(search != null && !search.isEmpty())
				return repository.buscarPorIdEmpresaTituloResumen(id,"%" + search + "%", pageable);
			return repository.listadoPorIdEmpresa(id, pageable);
		}
		return repository.findAll(pageable);
	}
	
	public Integer eliminar(Integer id) {
		repository.deleteById(id);
		return id;
	}
	
	public static String obtenerNombreBase(String fileName) {
        Path path = Paths.get(fileName);
        String fileNameWithPath = path.getFileName().toString();
        int dotIndex = fileNameWithPath.lastIndexOf('.');
        if (dotIndex == -1) {
            return fileNameWithPath;
        } else {
            return fileNameWithPath.substring(0, dotIndex);
        }
    }
	
	public String guardarImagen(Noticias noticia, MultipartFile archivo) throws IOException {
		if(archivo != null && archivo.getOriginalFilename() != null) {
			String fileName = archivo.getOriginalFilename().replace(" ", "_").replace("#", "_").replace("*", "_");

			String path = directorio 
                    + noticia.getId() 
                    + File.separator + fileName;
			Path destinoArchivo = this.directorioArchivo.resolve(
					Paths.get(path)
					.normalize().toAbsolutePath());
			try(InputStream inputStream = archivo.getInputStream()) {
				Files.createDirectories(destinoArchivo.getParent());
				Files.copy(inputStream, destinoArchivo, StandardCopyOption.REPLACE_EXISTING);
			}
			return destinoArchivo.toString();
		}
		return null;
	}
	
	public ResponseEntity<byte[]> abrirArchivo(String path) {
		File archivo = new File(path);
		return transferir(archivo);
	}
	
	private ResponseEntity<byte[]> transferir(File archivo) {
		byte[] multimedia = new byte[] {};
		HttpHeaders headers = new HttpHeaders();

		CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.DAYS).noTransform().mustRevalidate();
		headers.setCacheControl(cacheControl);

		try {
			if (archivo.getName().contains(".jpg") || archivo.getName().contains(".jpeg")) {
				headers.setContentType(MediaType.IMAGE_JPEG);
			} else if (archivo.getName().contains(".png")) {
				headers.setContentType(MediaType.IMAGE_PNG);
			}

			multimedia = Files.readAllBytes(archivo.toPath());
		} catch (IOException e) {
			headers.setContentType(MediaType.APPLICATION_JSON);
			return new ResponseEntity<>("{\"error\":\"No existe el archivo\"}".getBytes(), headers,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(multimedia, headers, HttpStatus.OK);
	}
}
