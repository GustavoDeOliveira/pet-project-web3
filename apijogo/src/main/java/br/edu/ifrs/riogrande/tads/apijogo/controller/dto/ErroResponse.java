package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class ErroResponse {
    final List<String> erros;
}
