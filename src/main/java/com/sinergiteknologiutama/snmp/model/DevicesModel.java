package com.sinergiteknologiutama.snmp.model;

public class DevicesModel {
    private Integer id;
    private Integer manufacturer_id;
    private Integer device_type_id;
    private String name;
    private String protocol;
    private String snmp_version;
    private Integer snmp_timeout;
    private Integer snmp_retries;
    private String snmp_read;
    private String snmp_write;
    private String snmp_port;
    private DeviceNodesModel deviceNodesModel;
    private DeviceSNMPTrapModel deviceSNMPTrapModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(Integer manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public Integer getDevice_type_id() {
        return device_type_id;
    }

    public void setDevice_type_id(Integer device_type_id) {
        this.device_type_id = device_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSnmp_version() {
        return snmp_version;
    }

    public void setSnmp_version(String snmp_version) {
        this.snmp_version = snmp_version;
    }

    public Integer getSnmp_timeout() {
        return snmp_timeout;
    }

    public void setSnmp_timeout(Integer snmp_timeout) {
        this.snmp_timeout = snmp_timeout;
    }

    public Integer getSnmp_retries() {
        return snmp_retries;
    }

    public void setSnmp_retries(Integer snmp_retries) {
        this.snmp_retries = snmp_retries;
    }

    public String getSnmp_read() {
        return snmp_read;
    }

    public void setSnmp_read(String snmp_read) {
        this.snmp_read = snmp_read;
    }

    public String getSnmp_write() {
        return snmp_write;
    }

    public void setSnmp_write(String snmp_write) {
        this.snmp_write = snmp_write;
    }

    public String getSnmp_port() {
        return snmp_port;
    }

    public void setSnmp_port(String snmp_port) {
        this.snmp_port = snmp_port;
    }

    public DeviceNodesModel getDeviceNodesModel() {
        return deviceNodesModel;
    }

    public void setDeviceNodesModel(DeviceNodesModel deviceNodesModel) {
        this.deviceNodesModel = deviceNodesModel;
    }

    public DeviceSNMPTrapModel getDeviceSNMPTrapModel() {
        return deviceSNMPTrapModel;
    }

    public void setDeviceSNMPTrapModel(DeviceSNMPTrapModel deviceSNMPTrapModel) {
        this.deviceSNMPTrapModel = deviceSNMPTrapModel;
    }
}
