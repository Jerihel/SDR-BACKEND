package com.enactusumg.sdr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CriterionDto {
   private String nombreCriterio;
    private int ponderacion;

    private String usuarioAgrega;

}
