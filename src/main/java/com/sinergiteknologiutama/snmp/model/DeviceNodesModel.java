package com.sinergiteknologiutama.snmp.model;

import java.util.ArrayList;
import java.util.List;

public class DeviceNodesModel {
    private int id;
    private String site_oid;
    private int device_id;
    private String name;
    private String poller_ipaddress;
    private String serial_number;
    private String ipaddress;
    private String protocol_monitoring;
    private String status;
    private Integer device_node_id;

    List<DevicesModel> devicesModels = new ArrayList<DevicesModel>();


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

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoller_ipaddress() {
        return poller_ipaddress;
    }

    public void setPoller_ipaddress(String poller_ipaddress) {
        this.poller_ipaddress = poller_ipaddress;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getProtocol_monitoring() {
        return protocol_monitoring;
    }

    public void setProtocol_monitoring(String protocol_monitoring) {
        this.protocol_monitoring = protocol_monitoring;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDevice_node_id() {
        return device_node_id;
    }

    public void setDevice_node_id(Integer device_node_id) {
        this.device_node_id = device_node_id;
    }

    public List<DevicesModel> getDevicesModels() {
        return devicesModels;
    }

    public void setDevicesModels(List<DevicesModel> devicesModels) {
        this.devicesModels = devicesModels;
    }
}
