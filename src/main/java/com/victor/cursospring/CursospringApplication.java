package com.victor.cursospring;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.victor.cursospring.domain.Categoria;
import com.victor.cursospring.domain.Cidade;
import com.victor.cursospring.domain.Cliente;
import com.victor.cursospring.domain.Endereco;
import com.victor.cursospring.domain.Estado;
import com.victor.cursospring.domain.ItemPedido;
import com.victor.cursospring.domain.Pagamento;
import com.victor.cursospring.domain.PagamentoComBoleto;
import com.victor.cursospring.domain.PagamentoComCartao;
import com.victor.cursospring.domain.Pedido;
import com.victor.cursospring.domain.Produto;
import com.victor.cursospring.domain.enums.EstadoPagamento;
import com.victor.cursospring.domain.enums.TipoCliente;
import com.victor.cursospring.repositories.CategoriaRepository;
import com.victor.cursospring.repositories.CidadeRepository;
import com.victor.cursospring.repositories.ClienteRepository;
import com.victor.cursospring.repositories.EnderecoRepository;
import com.victor.cursospring.repositories.EstadoRepository;
import com.victor.cursospring.repositories.ItemPedidoRepository;
import com.victor.cursospring.repositories.PagamentoRepository;
import com.victor.cursospring.repositories.PedidoRepository;
import com.victor.cursospring.repositories.ProdutoRepository;

@SpringBootApplication
public class CursospringApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepo;
    @Autowired
    private ProdutoRepository produtoRepo;
    @Autowired
    private CidadeRepository cidadeRepo;
    @Autowired
    private EstadoRepository estadoRepo;
    @Autowired
    private EnderecoRepository enderecoRepo;
    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private PedidoRepository pedidoRepo;
    @Autowired
    private PagamentoRepository pagamentoRepo;
    @Autowired
    private ItemPedidoRepository itemPedidoRepo;
    
	public static void main(String[] args) {
		SpringApplication.run(CursospringApplication.class, args);
	}

    @Override
    public void run (String... args) throws Exception {
        
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Eletrônicos");
        Categoria cat4 = new Categoria(null, "Entreterimento");
        Categoria cat5 = new Categoria(null, "Cama, mesa e banho");
        Categoria cat6 = new Categoria(null, "Elétrica");
        Categoria cat7 = new Categoria(null, "Jardinagem");
        
        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);
        
        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));
        
        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));
        
        categoriaRepo.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepo.saveAll(Arrays.asList(p1, p2, p3));
        
        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");
        
        Cidade c1 = new Cidade(null, "Uberlándia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);
        
        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));
        
        estadoRepo.saveAll(Arrays.asList(est1, est2));
        cidadeRepo.saveAll(Arrays.asList(c1, c2, c3));
        
        Cliente cli1 = new Cliente(null, "Maria da Silva", "maria@gmail.com", "36378945611", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("12312312", "456354756"));
        
        Endereco e1 = new Endereco(null, "Rua Z", "121", "Apto 23", c1, "Jardim", "08535131", cli1);
        Endereco e2 = new Endereco(null, "Avenida A", "444", "Cj 55", c2, "Bairro Dois", "1112223", cli1);
        
        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
        
        clienteRepo.saveAll(Arrays.asList(cli1));
        enderecoRepo.saveAll(Arrays.asList(e1, e2));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
        
        Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pgto1);
        Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pgto2);
        
        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
        
        pedidoRepo.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepo.saveAll(Arrays.asList(pgto1, pgto2));
        
        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.0);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.0);
        
        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));
        
        itemPedidoRepo.saveAll(Arrays.asList(ip1, ip2, ip3));
        
    }
}
