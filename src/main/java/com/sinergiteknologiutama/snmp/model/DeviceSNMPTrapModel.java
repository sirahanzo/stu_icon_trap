package com.sinergiteknologiutama.snmp.model;

import java.util.ArrayList;
import java.util.List;

public class DeviceSNMPTrapModel {
    private int id;
    private int device_id;
    private String var_oid;
    private String var_name;
    private int severity_id;
    private int snmp_trap_id;
    private String type_trap;

    List<DeviceNodesModel> deviceNodesModels = new ArrayList<DeviceNodesModel>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getVar_oid() {
        return var_oid;
    }

    public void setVar_oid(String var_oid) {
        this.var_oid = var_oid;
    }

    public String getVar_name() {
        return var_name;
    }

    public void setVar_name(String var_name) {
        this.var_name = var_name;
    }

    public int getSeverity_id() {
        return severity_id;
    }

    public void setSeverity_id(int severity_id) {
        this.severity_id = severity_id;
    }

    public int getSnmp_trap_id() {
        return snmp_trap_id;
    }

    public void setSnmp_trap_id(int snmp_trap_id) {
        this.snmp_trap_id = snmp_trap_id;
    }

    public String getType_trap() {
        return type_trap;
    }

    public void setType_trap(String type_trap) {
        this.type_trap = type_trap;
    }

    public List<DeviceNodesModel> getDeviceNodesModels() {
        return deviceNodesModels;
    }

    public void setDeviceNodesModels(List<DeviceNodesModel> deviceNodesModels) {
        this.deviceNodesModels = deviceNodesModels;
    }
}
