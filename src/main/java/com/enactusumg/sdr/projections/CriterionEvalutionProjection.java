package com.enactusumg.sdr.projections;

public class CriterionEvalutionProjection {

    private int noCriterio;
    private String nombreCriterio;
    private int ponderacion;
    private String estado;


    public CriterionEvalutionProjection(int noCriterio, String nombreCriterio, int ponderacion, String estado) {
        this.noCriterio = noCriterio;
        this.nombreCriterio = nombreCriterio;
        this.ponderacion = ponderacion;
        this.estado = estado;
    }

    public int getNoCriterio() {
        return noCriterio;
    }

    public void setNoCriterio(int noCriterio) {
        this.noCriterio = noCriterio;
    }

    public String getNombreCriterio() {
        return nombreCriterio;
    }

    public void setNombreCriterio(String nombreCriterio) {
        this.nombreCriterio = nombreCriterio;
    }

    public int getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(int ponderacion) {
        this.ponderacion = ponderacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
