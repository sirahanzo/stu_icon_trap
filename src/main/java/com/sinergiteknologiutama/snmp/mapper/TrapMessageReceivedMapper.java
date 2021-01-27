package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.model.TrapMessageReceivedModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrapMessageReceivedMapper implements RowMapper<TrapMessageReceivedModel> {
    @Override
    public TrapMessageReceivedModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        TrapMessageReceivedModel trap_received = new TrapMessageReceivedModel();

        trap_received.setId(rs.getInt("id"));
        trap_received.setFrom_ipaddress(rs.getString("from_ipaddress"));
        trap_received.setTrap_oid(rs.getString("trap_oid"));
        trap_received.setUptime(rs.getString("uptime"));
        trap_received.setTrap_type(rs.getString("trap_type"));
        trap_received.setVar_name1(rs.getString("var_name1"));
        trap_received.setVar_value1(rs.getDouble("var_value1"));
        trap_received.setVar_name1(rs.getString("var_name2"));
        trap_received.setVar_value1(rs.getDouble("var_value2"));
        trap_received.setVar_name1(rs.getString("var_name3"));
        trap_received.setVar_value1(rs.getDouble("var_value3"));
        trap_received.setVar_name1(rs.getString("var_name4"));
        trap_received.setVar_value1(rs.getDouble("var_value4"));
        trap_received.setVar_name1(rs.getString("var_name5"));
        trap_received.setVar_value1(rs.getDouble("var_value5"));
        trap_received.setVar_name1(rs.getString("var_name6"));
        trap_received.setVar_value1(rs.getDouble("var_value6"));
        trap_received.setVar_name1(rs.getString("var_name7"));
        trap_received.setVar_value1(rs.getDouble("var_value7"));
        trap_received.setVar_name1(rs.getString("var_name8"));
        trap_received.setVar_value1(rs.getDouble("var_value8"));
        trap_received.setVar_name1(rs.getString("var_name9"));
        trap_received.setVar_value1(rs.getDouble("var_value9"));
        trap_received.setVar_name1(rs.getString("var_name10"));
        trap_received.setVar_value1(rs.getDouble("var_value10"));

        return  trap_received;
    }
}
