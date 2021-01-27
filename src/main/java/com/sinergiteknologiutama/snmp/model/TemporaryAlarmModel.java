package com.sinergiteknologiutama.snmp.model;

public class TemporaryAlarmModel {
    int id;
    String region;
    //String site;
    String site_oid;
    //String device;
    int device_node_id;
    int snmp_trap_id;
    String ipaddress;
    String trap_oid;
    Double severity_commlost;
    Double severity_critical;
    Double severity_major;
    Double severity_minor;
    Double severity_warning;
    Double alarm_counting;
    String alarm_status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSite_oid() {
        return site_oid;
    }

    public void setSite_oid(String site_oid) {
        this.site_oid = site_oid;
    }

    public int getDevice_node_id() {
        return device_node_id;
    }

    public void setDevice_node_id(int device_node_id) {
        this.device_node_id = device_node_id;
    }

    public int getSnmp_trap_id() {
        return snmp_trap_id;
    }

    public void setSnmp_trap_id(int snmp_trap_id) {
        this.snmp_trap_id = snmp_trap_id;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getTrap_oid() {
        return trap_oid;
    }

    public void setTrap_oid(String trap_oid) {
        this.trap_oid = trap_oid;
    }

    public Double getSeverity_commlost() {
        return severity_commlost;
    }

    public void setSeverity_commlost(Double severity_commlost) {
        this.severity_commlost = severity_commlost;
    }

    public Double getSeverity_critical() {
        return severity_critical;
    }

    public void setSeverity_critical(Double severity_critical) {
        this.severity_critical = severity_critical;
    }

    public Double getSeverity_major() {
        return severity_major;
    }

    public void setSeverity_major(Double severity_major) {
        this.severity_major = severity_major;
    }

    public Double getSeverity_minor() {
        return severity_minor;
    }

    public void setSeverity_minor(Double severity_minor) {
        this.severity_minor = severity_minor;
    }

    public Double getSeverity_warning() {
        return severity_warning;
    }

    public void setSeverity_warning(Double severity_warning) {
        this.severity_warning = severity_warning;
    }

    public Double getAlarm_counting() {
        return alarm_counting;
    }

    public void setAlarm_counting(Double alarm_counting) {
        this.alarm_counting = alarm_counting;
    }

    public String getAlarm_status() {
        return alarm_status;
    }

    public void setAlarm_status(String alarm_status) {
        this.alarm_status = alarm_status;
    }


}
