package br.edu.ifrs.riogrande.tads.apijogo.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ServerResponse")
public class MetaResponseWrapper<T, M> extends ResponseWrapper<T> {

    @ApiModelProperty(value = "meta")
    @JsonInclude(value = Include.NON_NULL)
	private final M meta;

	protected MetaResponseWrapper(T data, M meta) {
		super(data);
		this.meta = meta;
	}

	// inferência do tipo, método fábrica
	public static <T, M> MetaResponseWrapper<T, M> wrap(T response, M meta) {
		return new MetaResponseWrapper<T, M>(response, meta);
	}

}