package com.enactusumg.sdr.models;

import com.enactusumg.sdr.dto.RequestDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "request", schema = "enactus_sreg")
@Entity
public class Request implements Serializable {

    private Integer idRequest;
    private String name;
    private String lastName;
    private String telephone;
    private String email;
    private Integer requestType;
    private Integer state;
    private String idReviwer;
    private String appointmentLocation;
    private Date appointment;
    private String requestComment;


    public Request() {
    }

    public Request(Integer idRequest, String name, String lastName, String telephone, String email, Integer requestType, Integer state, String idReviwer, String appointmentLocation, Date appointment, String requestComment) {
        this.idRequest = idRequest;
        this.name = name;
        this.lastName = lastName;
        this.telephone = telephone;
        this.email = email;
        this.requestType = requestType;
        this.state = state;
        this.idReviwer = idReviwer;
        this.appointmentLocation = appointmentLocation;
        this.appointment = appointment;
        this.requestComment = requestComment;
    }
    public static  Request saveRequest(RequestDto dto){
        Request request = new Request();

        request.setName(dto.getName());
        request.setLastName(dto.getLastName());
        request.setTelephone(dto.getTelephone());
        request.setEmail(dto.getEmail());
        request.setRequestType(dto.getRequestType());
        request.setState(dto.getState());
        request.setIdReviwer(dto.getIdReviwer());
        request.setAppointment(dto.getAppointment());
        request.setRequestComment(dto.getRequestComment());

return request;
    }

    @Column
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "last_name", length = 100)
    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "telephone", length = 9)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "email", length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "request_type")
    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Column(name = "id_reviwer", length = 20)
    public String getIdReviwer() {
        return idReviwer;
    }

    public void setIdReviwer(String idReviwer) {
        this.idReviwer = idReviwer;
    }

    @Column(name = "appointment_location", length = 50)
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    @Column(name = "appointment", length = 22)
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getAppointment() {
        return appointment;
    }

    public void setAppointment(Date appointment) {
        this.appointment = appointment;
    }
@Column(name = "request_comment",length = 500)
    public String getRequestComment() {
        return requestComment;
    }

    public void setRequestComment(String requestComment) {
        this.requestComment = requestComment;
    }
}
