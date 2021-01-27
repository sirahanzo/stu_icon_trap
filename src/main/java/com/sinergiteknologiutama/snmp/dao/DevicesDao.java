package com.sinergiteknologiutama.snmp.dao;

import com.sinergiteknologiutama.snmp.mapper.DevicesMapper;
import com.sinergiteknologiutama.snmp.model.DevicesModel;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("devicesDao")
public class DevicesDao extends BaseDao {

    public List<DevicesModel> deviceNodeTrap(String ipaddress) throws DataAccessException {
        String SQL = "SELECT * FROM devices d LEFT JOIN device_nodes dn on d.id = dn.device_id LEFT JOIN device_snmp_trap dst on d.id = dst.device_id WHERE ipaddress =?";
        List<DevicesModel> devicesModels = jdbcTemplate.query(SQL, new Object[]{ipaddress}, new DevicesMapper());
        return devicesModels;
    }


    public List<DevicesModel> nodeTrap(String oid) throws DataAccessException {
        String SQL = "SELECT * FROM devices d LEFT JOIN device_nodes dn on d.id = dn.device_id LEFT JOIN device_snmp_trap dst on d.id = dst.device_id WHERE var_oid like ?";
        //String SQL = "SELECT * FROM devices d LEFT JOIN device_nodes dn on d.id = dn.device_id LEFT JOIN device_snmp_trap dst on d.id = dst.device_id WHERE var_oid ='"+oid+"'";
        List<DevicesModel> devicesModels = jdbcTemplate.query(SQL, new Object[]{oid.toString()}, new DevicesMapper());
        //List<DevicesModel> devicesModels = jdbcTemplate.query(SQL, new DevicesMapper());
        return devicesModels;
    }

    public DevicesModel validTrapOID(String oid, String ipaddress) throws DataAccessException {
        String SQL = "SELECT * FROM devices d LEFT JOIN device_nodes dn on d.id = dn.device_id LEFT JOIN device_snmp_trap dst on d.id = dst.device_id WHERE var_oid = ? AND ipaddress=?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{oid, ipaddress}, new DevicesMapper());
        //return devicesModel;
    }

    public DevicesModel validAgent(String ipaddress) throws DataAccessException {
        String SQL = "SELECT * FROM devices d LEFT JOIN device_nodes dn on d.id = dn.device_id LEFT JOIN device_snmp_trap dst on d.id = dst.device_id WHERE  ipaddress=?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{ipaddress}, new DevicesMapper());
        //return devicesModel;
    }

}
