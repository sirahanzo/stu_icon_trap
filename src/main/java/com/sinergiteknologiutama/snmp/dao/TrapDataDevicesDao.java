package com.sinergiteknologiutama.snmp.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;

@Repository("trapDataDevicesDao")
public class TrapDataDevicesDao extends BaseDao {


    //public void newTrapData(String from_address, String trap_oid, String type_trap,String var_name1, int var_value1) throws DataAccessException {
    //    String SQL = "INSERT INTO trap_data_devices (from_ipaddress, trap_oid, uptime, trap_type, var_name1, var_value1,updated_at ) VALUES (?,?,\"00:00:00\",?,?,?,(SELECT DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'));";
    //    jdbcTemplate.update(SQL, from_address, trap_oid,type_trap ,var_name1, var_value1);
    //
    //}

    // TODO: 3/11/2020 trap_type use for define (active / cleared) alarm
    public void newTrapData2(String from_address, String trap_oid, String var_name1, int var_value1) throws DataAccessException {
        String SQL = "INSERT INTO trap_data_devices (from_ipaddress, trap_oid, uptime, trap_type, var_name1, var_value1,updated_at ) VALUES (?,?,\"00:00:00\",\"integer\",?,?,(SELECT DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'));";
        jdbcTemplate.update(SQL, from_address, trap_oid, var_name1, var_value1);

    }

    public void updateTrapData(String site_oid,int device_node_id,int snmp_trap_id,String trap_oid,int severity_id,int value, String status_alarm)throws DataAccessException{
        String SQL = "UPDATE trap_data_devices SET value=?,status_alarm =?, updated_at=(SELECT NOW())  WHERE site_oid =? AND device_node_id =? AND snmp_trap_id =?";
        jdbcTemplate.update(SQL,value,status_alarm,site_oid,device_node_id,snmp_trap_id);
    }

}
