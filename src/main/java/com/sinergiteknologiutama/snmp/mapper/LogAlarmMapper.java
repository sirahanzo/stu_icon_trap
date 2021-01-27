package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.model.LogAlarmModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogAlarmMapper implements RowMapper<LogAlarmModel> {

    @Override
    public LogAlarmModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        LogAlarmModel alarm = new LogAlarmModel();

        alarm.setId(rs.getInt("id"));
        alarm.setRegion_oid(rs.getString("region_oid"));
        alarm.setCity_oid(rs.getString("city_oid"));
        alarm.setSite_oid(rs.getString("site_oid"));
        alarm.setSite_name(rs.getString("site_name"));
        alarm.setDevice_node_id(rs.getInt("device_node_id"));
        alarm.setDevice_node_name(rs.getString("device_node_name"));
        alarm.setIpaddress(rs.getString("ipaddress"));
        alarm.setSnmp_trap_id(rs.getInt("snmp_trap_id"));
        alarm.setTrap_oid(rs.getString("trap_oid"));
        alarm.setTrap_name(rs.getString("trap_name"));
        alarm.setSeverity_id(rs.getInt("severity_id"));
        alarm.setValue(rs.getInt("value"));
        alarm.setAlarm_status(rs.getString("alarm_status"));

        return alarm;
    }
}
