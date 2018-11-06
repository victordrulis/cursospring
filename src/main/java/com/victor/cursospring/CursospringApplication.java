package com.victor.cursospring;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.victor.cursospring.domain.Categoria;
import com.victor.cursospring.repositories.CategoriaRepository;

@SpringBootApplication
public class CursospringApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepo;
    
	public static void main(String[] args) {
		SpringApplication.run(CursospringApplication.class, args);
	}

    @Override
    public void run (String... args) throws Exception {
        
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        categoriaRepo.saveAll(Arrays.asList(cat1, cat2));
    }
}
