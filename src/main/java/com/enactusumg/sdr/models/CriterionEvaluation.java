package com.enactusumg.sdr.models;

import com.enactusumg.sdr.dto.CriterionDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "criterion_evaluation",schema = "enactus_sreg")
public class CriterionEvaluation implements Serializable {
    private int noCriterio;
    private String nombreCriterio;
    private int ponderacion;
    private int estado;
    private String usuarioAgrega;
    private Date fechaAgrega;
    private Date fechaModifica;
    private String usuarioModifica;

    public CriterionEvaluation() {
    }
     public static CriterionEvaluation createCriterion(CriterionDto dto){
        CriterionEvaluation criterion = new CriterionEvaluation();

        criterion.setEstado(3);
        criterion.setNombreCriterio(dto.getNombreCriterio().toLowerCase());
        criterion.setUsuarioAgrega(dto.getUsuarioAgrega());
        criterion.setPonderacion(dto.getPonderacion());
        criterion.setFechaAgrega(new Date());



        return criterion;
     }

    public CriterionEvaluation(int noCriterio, String nombreCriterio, int ponderacion, int estado, String usuarioAgrega, Date fechaAgrega, Date fechaModifica, String usuarioModifica) {
        this.noCriterio = noCriterio;
        this.nombreCriterio = nombreCriterio;
        this.ponderacion = ponderacion;
        this.estado = estado;
        this.usuarioAgrega = usuarioAgrega;
        this.fechaAgrega = fechaAgrega;
        this.fechaModifica = fechaModifica;
        this.usuarioModifica = usuarioModifica;
    }

    @Id
    @Column(name = "no_criterio",unique = true,nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public int getNoCriterio() {
        return noCriterio;
    }

    public void setNoCriterio(int noCriterio) {
        this.noCriterio = noCriterio;
    }

    @Column(name = "nombre_criterio",length = 150)
    public String getNombreCriterio() {
        return nombreCriterio;
    }

    public void setNombreCriterio(String nombreCriterio) {
        this.nombreCriterio = nombreCriterio;
    }
@Column(name = "ponderacion")
    public int getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(int ponderacion) {
        this.ponderacion = ponderacion;
    }
@Column(name = "estado")
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
@Column(name = "usuario_agrega",length = 150,nullable = false)
    public String getUsuarioAgrega() {
        return usuarioAgrega;
    }

    public void setUsuarioAgrega(String usuarioAgrega) {
        this.usuarioAgrega = usuarioAgrega;
    }
@Column(name = "fecha_agrega",length = 22)
@Temporal(TemporalType.DATE)
    public Date getFechaAgrega() {
        return fechaAgrega;
    }

    public void setFechaAgrega(Date fechaAgrega) {
        this.fechaAgrega = fechaAgrega;
    }
    @Column(name = "fecha_modifica",length = 22)
    @Temporal(TemporalType.DATE)
    public Date getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(Date fechaModifica) {
        this.fechaModifica = fechaModifica;
    }
@Column(name = "usuario_modifica",length = 150)
    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }


    @Override
    public String toString() {
        return "CriterionEvaluation{" +
                "noCriterio=" + noCriterio +
                ", nombreCriterio='" + nombreCriterio + '\'' +
                ", ponderacion=" + ponderacion +
                ", estado='" + estado + '\'' +
                ", usuarioAgrega='" + usuarioAgrega + '\'' +
                ", fechaAgrega='" + fechaAgrega + '\'' +
                ", fechaModifica=" + fechaModifica +
                ", usuarioModifica=" + usuarioModifica +
                '}';
    }
}
