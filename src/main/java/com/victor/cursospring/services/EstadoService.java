package com.victor.cursospring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.cursospring.domain.Estado;
import com.victor.cursospring.repositories.EstadoRepository;

@Service
public class EstadoService {
    
    @Autowired
    private EstadoRepository estadoRepo;
    
    public List<Estado> findAll() {
        return estadoRepo.findAllByOrderByNome();
    }

}
