package com.enactusumg.sdr.models;


import com.enactusumg.sdr.dto.UserRequestDto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_request", schema = "enactus_sreg")
public class UserRequest implements Serializable {
    private Integer idUserRequest;
    private Integer campus;
    private Integer facultad;
    private Integer career;
    private Integer enacterDegree;
    private Integer interest;
    private Integer lookUp;
    private String financialSupport;
    private Integer amount;
    private String supportDetail;
    private String adviserDegree;
    private String currenctActivity;
    private String hasExperience;
    private String experienceDetail;
    private Integer adviseOn;
    private Integer adviseWay;
    private String supportLookUp;


    public UserRequest() {
    }

    public UserRequest(Integer idUserRequest, Integer campus, Integer facultad, Integer career, Integer enacterDegree, Integer interest, Integer lookUp, String financialSupport, Integer amount, String supportDetail, String adviserDegree, String currenctActivity, String hasExperience, String experienceDetail, Integer adviseOn, Integer adviseWay, String supportLookUp) {
        this.idUserRequest = idUserRequest;
        this.campus = campus;
        this.facultad = facultad;
        this.career = career;
        this.enacterDegree = enacterDegree;
        this.interest = interest;
        this.lookUp = lookUp;
        this.financialSupport = financialSupport;
        this.amount = amount;
        this.supportDetail = supportDetail;
        this.adviserDegree = adviserDegree;
        this.currenctActivity = currenctActivity;
        this.hasExperience = hasExperience;
        this.experienceDetail = experienceDetail;
        this.adviseOn = adviseOn;
        this.adviseWay = adviseWay;
        this.supportLookUp= supportLookUp;

    }

    public static UserRequest saveUserRequest(UserRequestDto dto, Integer idRequest) {
        UserRequest user = new UserRequest();
        user.setIdUserRequest(1);
        user.setIdUserRequest(idRequest);
        user.setCampus(dto.getCampus());
        user.setFacultad(dto.getFacultad());
        user.setCareer(dto.getCareer());
        user.setEnacterDegree(dto.getEnacterDegree());
        user.setInterest(dto.getInterest());
        user.setLookUp(dto.getLookUp());
        user.setFinancialSupport(dto.getFinancialSupport());
        user.setAmount(dto.getAmount());
        user.setSupportDetail(dto.getSupportDetail());
        user.setAdviserDegree(dto.getAdviserDegree());
        user.setCurrenctActivity(dto.getCurrenctActivity());
        user.setHasExperience(dto.getHasExperience());
        user.setExperienceDetail(dto.getExperienceDetail());
        user.setAdviseOn(dto.getAdviseOn());
        user.setAdviseWay(dto.getAdviseWay());
        user.setSupportLookUp(dto.getSupportLookUp());

        return user;
    }

    @Id
    @Column(name = "id_user_request", unique = true, nullable = false)

    public Integer getIdUserRequest() {
        return idUserRequest;
    }

    public void setIdUserRequest(Integer idUserRequest) {
        this.idUserRequest = idUserRequest;
    }

    @Column(name = "support_look_up", length = 100)
    public String getSupportLookUp() {
        return supportLookUp;
    }

    public void setSupportLookUp(String supportLookUp) {
        this.supportLookUp = supportLookUp;
    }

    @Column(name = "campus")
    public Integer getCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    @Column(name = "facultad")
    public Integer getFacultad() {
        return facultad;
    }

    public void setFacultad(Integer facutlad) {
        this.facultad = facutlad;
    }

    @Column(name = "career")
    public Integer getCareer() {
        return career;
    }

    public void setCareer(Integer career) {
        this.career = career;
    }

    @Column(name = "interest")
    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    @Column(name = "look_up")
    public Integer getLookUp() {
        return lookUp;
    }

    public void setLookUp(Integer lookUp) {
        this.lookUp = lookUp;
    }

    @Column(name = "enacter_degree")
    public Integer getEnacterDegree() {
        return enacterDegree;
    }

    public void setEnacterDegree(Integer enacterDegree) {
        this.enacterDegree = enacterDegree;
    }

    @Column(name = "financial_support", length = 1)
    public String getFinancialSupport() {
        return financialSupport;
    }

    public void setFinancialSupport(String financialSupport) {
        this.financialSupport = financialSupport;
    }

    @Column(name = "amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Column(name = "support_detail", length = 200)
    public String getSupportDetail() {
        return supportDetail;
    }

    public void setSupportDetail(String supportDetail) {
        this.supportDetail = supportDetail;
    }

    @Column(name = "adviser_degree", length = 50)
    public String getAdviserDegree() {
        return adviserDegree;
    }

    public void setAdviserDegree(String adviserDegree) {
        this.adviserDegree = adviserDegree;
    }

    @Column(name = "currect_activity", length = 100)
    public String getCurrenctActivity() {
        return currenctActivity;
    }

    public void setCurrenctActivity(String currenctActivity) {
        this.currenctActivity = currenctActivity;
    }

    @Column(name = "has_experience", length = 1)
    public String getHasExperience() {
        return hasExperience;
    }

    public void setHasExperience(String hasExperience) {
        this.hasExperience = hasExperience;
    }

    @Column(name = "experience_detail", length = 100)
    public String getExperienceDetail() {
        return experienceDetail;
    }

    public void setExperienceDetail(String experienceDetail) {
        this.experienceDetail = experienceDetail;
    }

    @Column(name = "advise_on")
    public Integer getAdviseOn() {
        return adviseOn;
    }

    public void setAdviseOn(Integer adviseOn) {
        this.adviseOn = adviseOn;
    }

    @Column(name = "advise_way")
    public Integer getAdviseWay() {
        return adviseWay;
    }

    public void setAdviseWay(Integer adviseWay) {
        this.adviseWay = adviseWay;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "idUserRequest=" + idUserRequest +
                ", campus='" + campus + '\'' +
                ", facultad='" + facultad + '\'' +
                ", career='" + career + '\'' +
                ", enacterDegree=" + enacterDegree +
                ", interest=" + interest +
                ", lookUp=" + lookUp +
                ", financialSupport='" + financialSupport + '\'' +
                ", amount=" + amount +
                ", supportDetail='" + supportDetail + '\'' +
                ", adviserDegree='" + adviserDegree + '\'' +
                ", currenctActivity='" + currenctActivity + '\'' +
                ", hasExperience='" + hasExperience + '\'' +
                ", experienceDetail='" + experienceDetail + '\'' +
                ", adviseOn=" + adviseOn +
                ", adviseWay=" + adviseWay +
                '}';
    }
}
