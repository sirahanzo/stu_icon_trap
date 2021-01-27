package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.model.DeviceNodesModel;
import com.sinergiteknologiutama.snmp.model.DeviceSNMPTrapModel;
import com.sinergiteknologiutama.snmp.model.DevicesModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevicesMapper implements RowMapper<DevicesModel> {

    List<DevicesModel> deviceNodeObject = new ArrayList<DevicesModel>();


    @Override
    public DevicesModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        DevicesModel device = new DevicesModel();
        DeviceNodesModel node = new DeviceNodesModel();
        DeviceSNMPTrapModel snmpTrap = new DeviceSNMPTrapModel();


        device.setId(rs.getInt("id"));
        device.setDevice_type_id(rs.getInt("device_type_id"));
        device.setName(rs.getString("name"));
        device.setProtocol(rs.getString("protocol"));
        device.setSnmp_version(rs.getString("snmp_version"));
        device.setSnmp_timeout(rs.getInt("snmp_timeout"));
        device.setSnmp_retries(rs.getInt("snmp_retries"));
        device.setSnmp_read(rs.getString("snmp_read"));
        device.setSnmp_write(rs.getString("snmp_write"));
        device.setSnmp_port(rs.getString("snmp_port"));

        node.setSite_oid(rs.getString("site_oid"));
        node.setDevice_id(rs.getInt("device_id"));
        node.setPoller_ipaddress(rs.getString("poller_ipaddress"));
        node.setSerial_number(rs.getString("serial_number"));
        node.setIpaddress(rs.getString("ipaddress"));
        node.setProtocol_monitoring(rs.getString("protocol_monitoring"));
        node.setStatus(rs.getString("status"));
        node.setDevice_node_id(rs.getInt("id"));



        //snmpTrap.setDevice_id(rs.getInt("device_id"));
        //snmpTrap.setId(rs.getInt("id"));
        snmpTrap.setVar_oid(rs.getString("var_oid"));
        snmpTrap.setVar_name(rs.getString("var_name"));
        snmpTrap.setSeverity_id(rs.getInt("severity_id"));
        snmpTrap.setType_trap(rs.getString("type_trap"));
        snmpTrap.setSnmp_trap_id(rs.getInt("dst.id"));

        device.setDeviceNodesModel(node);
        deviceNodeObject.add(device);

        device.setDeviceSNMPTrapModel(snmpTrap);
        deviceNodeObject.add(device);

        return device;
    }
}
