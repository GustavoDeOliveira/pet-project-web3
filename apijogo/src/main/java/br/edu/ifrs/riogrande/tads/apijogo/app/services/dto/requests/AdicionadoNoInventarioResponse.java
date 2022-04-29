package br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdicionadoNoInventarioResponse {

	UUID itemId;
}
