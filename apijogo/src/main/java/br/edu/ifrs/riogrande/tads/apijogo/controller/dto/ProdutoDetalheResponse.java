package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Produto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "Detalhes Item da Loja", description = "Visão detalhada de um item que pode ser comprado na loja.")
@AllArgsConstructor
@JsonInclude(content = Include.NON_DEFAULT)
public class ProdutoDetalheResponse {

    @ApiModelProperty
    UUID id;

    @ApiModelProperty
    String nome;

    @ApiModelProperty
    double valor;

    @ApiModelProperty
    int estoque;

    @ApiModelProperty
    int ataque;

    @ApiModelProperty
    int defesa;

    @ApiModelProperty
    int vida;

    @ApiModelProperty
    int cura;

    @ApiModelProperty
    Integer duracao;

    public static ProdutoDetalheResponse from(Produto p) {
        return new ProdutoDetalheResponse(
            p.getId(),
            p.getNome(),
            p.getValor(),
            p.getEstoque(),
            p.getEfeito().getAtaque(),
            p.getEfeito().getDefesa(),
            p.getEfeito().getVida(),
            p.getEfeito().getCura(),
            p.getDuracao());
    }
}
