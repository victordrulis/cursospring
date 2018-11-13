package com.victor.cursospring.services;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.victor.cursospring.domain.ItemPedido;
import com.victor.cursospring.domain.PagamentoComBoleto;
import com.victor.cursospring.domain.Pedido;
import com.victor.cursospring.domain.enums.EstadoPagamento;
import com.victor.cursospring.repositories.ItemPedidoRepository;
import com.victor.cursospring.repositories.PagamentoRepository;
import com.victor.cursospring.repositories.PedidoRepository;
import com.victor.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private PagamentoRepository pagamentoRepo;

    @Autowired
    private ItemPedidoRepository itemPedidoRepo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private ProdutoService produtoService;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public @Valid Pedido insert(@Valid Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);

        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepo.save(obj.getPagamento());

        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }

        itemPedidoRepo.saveAll(obj.getItens());
        return obj;
    }
}
