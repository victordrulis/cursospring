package com.victor.cursospring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.cursospring.domain.Cidade;
import com.victor.cursospring.repositories.CidadeRepository;

@Service
public class CidadeService {
    
    @Autowired
    private CidadeRepository cidadeRepo;
    
    public List<Cidade> findByEstado(Integer estadoId) {
        return cidadeRepo.findCidades(estadoId);
    }

}
