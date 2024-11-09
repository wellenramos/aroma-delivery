package br.com.aroma.aroma_delivery.dto.enums;

import lombok.Getter;

@Getter
public enum PerfilEnum {

    CLIENTE(2L),
    ADMINISTRADOR(1L);

    PerfilEnum(Long codigo) {
        this.codigo = codigo;
    }

    private final Long codigo;
}
