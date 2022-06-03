package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import java.util.UUID;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Produto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(value = "Resumo Item da Loja", description = "O resumo de um item que pode ser comprado na loja.")
@AllArgsConstructor
public class ProdutoResumoResponse {
    
    @ApiModelProperty
    UUID id;

    @ApiModelProperty
    String nome;

    @ApiModelProperty
    double valor;

    @ApiModelProperty
    int estoque;

    public static ProdutoResumoResponse from(Produto p) {
        return new ProdutoResumoResponse(p.getId(), p.getNome(), p.getValor(), p.getEstoque());
    }
}
