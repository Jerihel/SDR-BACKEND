package com.enactusumg.sdr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CriterionUpdatDto {
    private int noCriterio;
    private String nombreCriterio;
    private int estadoColaborador;
    private String usuarioModifica;
    private int ponderacion;

}
