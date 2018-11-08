package com.victor.cursospring.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

import com.victor.cursospring.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComBoleto extends Pagamento implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Date dataVencimento;
    private Date dataPagamento;
    
    public PagamentoComBoleto () {}

    public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido,
            Date dataVencimento, Date dataPagamento) {
        super(id, estado, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    /**
     * @return the dataVencimento
     */
    public Date getDataVencimento() {
        return dataVencimento;
    }

    /**
     * @param dataVencimento the dataVencimento to set
     */
    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * @return the dataPagamento
     */
    public Date getDataPagamento() {
        return dataPagamento;
    }

    /**
     * @param dataPagamento the dataPagamento to set
     */
    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    
}
