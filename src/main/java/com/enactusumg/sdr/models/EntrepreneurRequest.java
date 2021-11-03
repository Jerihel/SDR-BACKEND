package com.enactusumg.sdr.models;

import com.enactusumg.sdr.dto.EntrepreneurRequestDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "entrepreneur_request", schema = "enactus_sreg")
@Entity
public class EntrepreneurRequest implements Serializable {

    private Integer idEntrepreneurRequest;
    private String aboutUs;
    private String requirements;
    private String contextLocation;
    private String details;

    public EntrepreneurRequest() {
    }

    public EntrepreneurRequest(Integer idEntrepreneurRequest, String aboutUs, String requirements, String contextLocation, String details) {
        this.idEntrepreneurRequest = idEntrepreneurRequest;
        this.aboutUs = aboutUs;
        this.requirements = requirements;
        this.contextLocation = contextLocation;
        this.details = details;
    }

    public static EntrepreneurRequest saveEntrepreneurRequest(EntrepreneurRequestDto dto, Integer idRequest) {
        EntrepreneurRequest entrepreneurRequest = new EntrepreneurRequest();
        entrepreneurRequest.setIdEntrepreneurRequest(1);
        entrepreneurRequest.setIdEntrepreneurRequest(idRequest);
        entrepreneurRequest.setAboutUs(dto.getAboutUs());
        entrepreneurRequest.setRequirements(dto.getRequirements());
        entrepreneurRequest.setContextLocation(dto.getContextLocation());
        entrepreneurRequest.setDetails(dto.getDetails());

        return entrepreneurRequest;
    }

    @Id
    @Column(name = "id_entrepreneur_request", unique = true, nullable = false)
    public Integer getIdEntrepreneurRequest() {
        return idEntrepreneurRequest;
    }

    public void setIdEntrepreneurRequest(Integer idEntrepreneurRequest) {
        this.idEntrepreneurRequest = idEntrepreneurRequest;
    }

    @Column(name = "about_us", length = 100)
    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    @Column(name = "requirements", length = 30)
    public String getRequirements() {
        return requirements;
    }


    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    @Column(name = "context_location", length = 30)
    public String getContextLocation() {
        return contextLocation;
    }

    public void setContextLocation(String contextLocation) {
        this.contextLocation = contextLocation;
    }

    @Column(name = "details", length = 500)
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
