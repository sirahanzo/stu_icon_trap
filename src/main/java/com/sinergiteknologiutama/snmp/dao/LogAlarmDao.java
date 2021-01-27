package com.sinergiteknologiutama.snmp.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("logAlarmDao")
public class LogAlarmDao extends BaseDao {

    public void insertNewLog(String region_oid, String city_oid, String site_oid, String site_name, int device_node_id, String device_node_name, String ipaddress, int snmp_trap_id, String trap_oid, String trap_name, int severity_id, int value, String status_alarm) throws DataAccessException {

        String SQL = "INSERT INTO log_alarm (region_oid, city_oid, site_oid, site_name, device_node_id, device_node_name, ipaddress, snmp_trap_id, trap_oid, trap_name, severity_id, value, status_alarm, updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,(SELECT DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')))";
        jdbcTemplate.update(SQL, region_oid, city_oid, site_oid, site_name, device_node_id, device_node_name, ipaddress, snmp_trap_id, trap_oid, trap_name, severity_id, value, status_alarm);
    }
}
