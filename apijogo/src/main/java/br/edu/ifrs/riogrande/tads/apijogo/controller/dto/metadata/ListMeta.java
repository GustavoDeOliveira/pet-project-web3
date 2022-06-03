package br.edu.ifrs.riogrande.tads.apijogo.controller.dto.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListMeta {
    final int total;
    final int paginaAtual;
    final int tamanhoPagina;
}
