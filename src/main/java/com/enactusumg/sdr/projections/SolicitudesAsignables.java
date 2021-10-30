package com.enactusumg.sdr.projections;

public class SolicitudesAsignables {

  private   Integer noSolicitud;
  private String nombreSolicitante;
  private String nombreRevisonr;
  private String estadoSolicitud;


    public SolicitudesAsignables(Integer noSolicitud, String nombreSolicitante, String nombreRevisonr, String estadoSolicitud) {
        this.noSolicitud = noSolicitud;
        this.nombreSolicitante = nombreSolicitante;
        this.nombreRevisonr = nombreRevisonr;
        this.estadoSolicitud = estadoSolicitud;
    }

    public Integer getNoSolicitud() {
        return noSolicitud;
    }

    public void setNoSolicitud(Integer noSolicitud) {
        this.noSolicitud = noSolicitud;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public String getNombreRevisonr() {
        return nombreRevisonr;
    }

    public void setNombreRevisonr(String nombreRevisonr) {
        this.nombreRevisonr = nombreRevisonr;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }
}
