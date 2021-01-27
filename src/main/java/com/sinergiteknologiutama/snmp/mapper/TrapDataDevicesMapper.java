package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.model.TrapDataDevicesModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrapDataDevicesMapper implements RowMapper<TrapDataDevicesModel> {

    @Override
    public TrapDataDevicesModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        TrapDataDevicesModel dataTrap = new TrapDataDevicesModel();

        dataTrap.setId(rs.getInt("id"));
        dataTrap.setSite_oid(rs.getString("site_oid"));
        dataTrap.setSnmp_trap_id(rs.getInt("snmp_trap_id"));
        dataTrap.setTrap_oid(rs.getString("trap_oid"));
        dataTrap.setSeverity_id(rs.getInt("severity_id"));
        dataTrap.setValue(rs.getInt("value"));
        dataTrap.setStatus_alarm(rs.getString("status_alarm"));

        return dataTrap;
    }
}
