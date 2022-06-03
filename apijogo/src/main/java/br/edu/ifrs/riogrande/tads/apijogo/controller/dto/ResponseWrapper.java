package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ServerResponse")
public class ResponseWrapper<T, M> { // Generics, Templates

	@ApiModelProperty(value = "data")
    @JsonInclude(value = Include.NON_NULL)
	private final T data; // payload

    @ApiModelProperty(value = "meta")
    @JsonInclude(value = Include.NON_NULL)
	private final M meta;

	// inferência do tipo, método fábrica
	public static <T, M> ResponseWrapper<T, M> wrap(T response, M meta) {
		return new ResponseWrapper<T, M>(response, meta);
	}

	// inferência do tipo, método fábrica
	public static <T> ResponseWrapper<T, Void> wrap(T response) {
		return new ResponseWrapper<T, Void>(response, null);
	}
}