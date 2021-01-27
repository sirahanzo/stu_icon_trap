package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.model.TemporaryAlarmModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TemporaryAlarmMapper implements RowMapper<TemporaryAlarmModel> {
    @Override
    public TemporaryAlarmModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        TemporaryAlarmModel tempAlarm = new TemporaryAlarmModel();

        tempAlarm.setId(rs.getInt("id"));
        tempAlarm.setRegion(rs.getString("region"));
        //tempAlarm.setSite(rs.getString("site"));
        //tempAlarm.setDevice(rs.getString("device"));
        tempAlarm.setSite_oid(rs.getString("site_oid"));
        tempAlarm.setIpaddress(rs.getString("ipaddress"));
        tempAlarm.setTrap_oid(rs.getString("trap_oid"));
        tempAlarm.setSeverity_commlost(rs.getDouble("severity_commlost"));
        tempAlarm.setSeverity_major(rs.getDouble("severity_major"));
        tempAlarm.setSeverity_minor(rs.getDouble("severity_minor"));
        tempAlarm.setSeverity_warning(rs.getDouble("severity_warning"));
        tempAlarm.setAlarm_counting(rs.getDouble("alarm_counting"));
        tempAlarm.setAlarm_status(rs.getString("alarm_status"));


        return tempAlarm;
    }
}
