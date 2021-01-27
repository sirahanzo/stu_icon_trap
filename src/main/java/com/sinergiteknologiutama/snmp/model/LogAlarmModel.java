package com.sinergiteknologiutama.snmp.model;

public class LogAlarmModel {
    int id;
    String region_oid;
    String city_oid;
    String site_oid;
    String site_name;
    int device_node_id;
    String device_node_name;
    String ipaddress;
    int snmp_trap_id;
    String trap_oid;
    String trap_name;
    int severity_id;
    int value;
    String alarm_status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion_oid() {
        return region_oid;
    }

    public void setRegion_oid(String region_oid) {
        this.region_oid = region_oid;
    }

    public String getCity_oid() {
        return city_oid;
    }

    public void setCity_oid(String city_oid) {
        this.city_oid = city_oid;
    }

    public String getSite_oid() {
        return site_oid;
    }

    public void setSite_oid(String site_oid) {
        this.site_oid = site_oid;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public int getDevice_node_id() {
        return device_node_id;
    }

    public void setDevice_node_id(int device_node_id) {
        this.device_node_id = device_node_id;
    }

    public String getDevice_node_name() {
        return device_node_name;
    }

    public void setDevice_node_name(String device_node_name) {
        this.device_node_name = device_node_name;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
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

    public String getTrap_name() {
        return trap_name;
    }

    public void setTrap_name(String trap_name) {
        this.trap_name = trap_name;
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

    public String getAlarm_status() {
        return alarm_status;
    }

    public void setAlarm_status(String alarm_status) {
        this.alarm_status = alarm_status;
    }
}
