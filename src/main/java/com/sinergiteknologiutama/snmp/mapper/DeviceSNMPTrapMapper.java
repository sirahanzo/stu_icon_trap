package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.dao.DeviceNodesDao;
import com.sinergiteknologiutama.snmp.model.DeviceSNMPTrapModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeviceSNMPTrapMapper implements RowMapper<DeviceSNMPTrapModel> {


    @Override
    public DeviceSNMPTrapModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        DeviceSNMPTrapModel snmpTrap = new DeviceSNMPTrapModel();

        snmpTrap.setId(rs.getInt("id"));
        snmpTrap.setDevice_id(rs.getInt("device_id"));
        snmpTrap.setVar_oid(rs.getString("var_oid"));
        snmpTrap.setVar_name(rs.getString("var_name"));
        snmpTrap.setSeverity_id(rs.getInt("severity_id"));
        snmpTrap.setType_trap(rs.getString("type_trap"));
        snmpTrap.setSnmp_trap_id(rs.getInt("id"));


        return  snmpTrap;


    }
}
