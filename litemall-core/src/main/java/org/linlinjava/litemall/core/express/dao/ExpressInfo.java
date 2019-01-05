/**
 * Copyright 2018 bejson.com
 */
package org.linlinjava.litemall.core.express.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2018-07-19 22:27:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ExpressInfo {

    @JsonProperty("number")
    private String LogisticCode;
    @JsonProperty("expName")
    private String ShipperName;
    @JsonProperty("expSite")
    private String ShipperSite;
    @JsonProperty("expPhone")
    private String ShipperPhone;
    @JsonProperty("type")
    private String ShipperCode;
    @JsonProperty("list")
    private List<Traces> Traces;
    @JsonProperty("State")
    private String State;
    @JsonProperty("EBusinessID")
    private String EBusinessID;
    @JsonProperty("deliverystatus")
    private String Success;
    @JsonProperty("Reason")
    private String Reason;
    @JsonProperty("issign")
    private String IsSign;

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public List<Traces> getTraces() {
        return Traces;
    }

    public void setTraces(List<Traces> Traces) {
        this.Traces = Traces;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public String getShipperName() {
        return ShipperName;
    }

    public void setShipperName(String shipperName) {
        ShipperName = shipperName;
    }

    public String getShipperSite() {
        return ShipperSite;
    }

    public void setShipperSite(String shipperSite) {
        ShipperSite = shipperSite;
    }

    public String getShipperPhone() {
        return ShipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        ShipperPhone = shipperPhone;
    }

    public String isSuccess() {
        return Success;
    }

    public String getIsSign() {
        return IsSign;
    }

    public void setIsSign(String isSign) {
        IsSign = isSign;
    }

    @Override
    public String toString() {
        return "ExpressInfo{" +
                "LogisticCode='" + LogisticCode + '\'' +
                ", ShipperCode='" + ShipperCode + '\'' +
                ", ShipperSite='" + ShipperSite + '\'' +
                ", ShipperPhone='" + ShipperPhone + '\'' +
                ", Traces=" + Traces +
                ", State='" + State + '\'' +
                ", EBusinessID='" + EBusinessID + '\'' +
                ", Success=" + Success +
                ", Reason=" + Reason +
                ", ShipperName='" + ShipperName + '\'' +
                '}';
    }
}