package com.victor.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.cursospring.domain.Pedido;
import com.victor.cursospring.repositories.PedidoRepository;
import com.victor.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repo;
    
    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
        "Objeto não encontrado! Id: " + id + ", Tipo: "
        + Pedido.class.getName()));
    }
}
