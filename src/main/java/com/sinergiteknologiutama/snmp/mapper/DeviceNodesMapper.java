package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.model.DeviceNodesModel;
import com.sinergiteknologiutama.snmp.model.DeviceSNMPTrapModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeviceNodesMapper implements RowMapper<DeviceNodesModel> {




    @Override
    public DeviceNodesModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        DeviceNodesModel deviceNodes = new DeviceNodesModel();

        deviceNodes.setId(rs.getInt("id"));
        deviceNodes.setSite_oid(rs.getString("site_oid"));
        deviceNodes.setDevice_id(rs.getInt("device_id"));
        deviceNodes.setName(rs.getString("name"));
        deviceNodes.setPoller_ipaddress(rs.getString("poller_ipaddress"));
        deviceNodes.setSerial_number(rs.getString("serial_number"));
        deviceNodes.setIpaddress(rs.getString("ipaddress"));
        deviceNodes.setProtocol_monitoring(rs.getString("protocol_monitoring"));
        deviceNodes.setStatus(rs.getString("status"));
        deviceNodes.setDevice_node_id(rs.getInt("id"));


        return deviceNodes;

    }
}
