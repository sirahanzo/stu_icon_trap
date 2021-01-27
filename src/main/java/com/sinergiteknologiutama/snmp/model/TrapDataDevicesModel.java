package com.sinergiteknologiutama.snmp.model;

public class TrapDataDevicesModel {
    int id;
    String site_oid;
    int device_node_id;
    int snmp_trap_id;
    String trap_oid;
    int severity_id;
    int value;
    String status_alarm;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTrap_oid() {
        return trap_oid;
    }

    public void setTrap_oid(String trap_oid) {
        this.trap_oid = trap_oid;
    }

    public int getSeverity_id() {
        return severity_id;
    }

    public void setSeverity_id(int severity_id) {
        this.severity_id = severity_id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStatus_alarm() {
        return status_alarm;
    }

    public void setStatus_alarm(String status_alarm) {
        this.status_alarm = status_alarm;
    }
}
