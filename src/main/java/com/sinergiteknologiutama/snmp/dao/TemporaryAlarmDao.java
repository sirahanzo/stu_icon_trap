package com.sinergiteknologiutama.snmp.dao;

import com.sinergiteknologiutama.snmp.mapper.TemporaryAlarmMapper;
import com.sinergiteknologiutama.snmp.model.TemporaryAlarmModel;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("temporaryAlarmDao")
public class TemporaryAlarmDao extends BaseDao {

    public TemporaryAlarmModel existAlarmCheck(String agent_ipaddress, String oid) throws DataAccessException {
        String SQL = "SELECT * FROM temporary_alarm WHERE ipaddress = ? AND trap_oid =?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{agent_ipaddress, oid}, new TemporaryAlarmMapper());
    }

    //public void updateAlarm(String agent_ipaddress,String )
    public void inserNewTemporaryAlarm(String region, String site, String device, String ipaddress, String trap_oid, int commlost, int critical, int major, int minor, int warning, int counting) throws DataAccessException {
        String SQL = "INSERT INTO temporary_alarm ( region, site, device, ipaddress, trap_oid, severity_commlost, severity_critical, severity_major, severity_minor, severity_warning, alarm_counting, updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,(SELECT DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'))";
        jdbcTemplate.update(SQL, region, site, device, ipaddress, trap_oid, commlost, critical, major, minor, warning, counting);
    }

    public void updateSeverityMajor(String ipdaddress ,int snmp_trap_id, String status_alarm, int count) {
        //String SQL = "UPDATE temporary_alarm SET severity_major = severity_major + 1, status_alarm =? , total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE ipaddress = ? AND snmp_trap_id = ?";
        String SQL = "UPDATE temporary_alarm SET severity_major = ?, status_alarm =? , total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE ipaddress = ? AND snmp_trap_id = ?";
        jdbcTemplate.update(SQL, count, status_alarm, count, ipdaddress, snmp_trap_id);

    }

    public void countSeverityMajor(int device_node_id ,int snmp_trap_id, String status_alarm, int count) {
        String SQL = "UPDATE temporary_alarm SET severity_major = ?, status_alarm =? , total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE device_node_id = ? AND snmp_trap_id = ?";
        jdbcTemplate.update(SQL, count, status_alarm, count, device_node_id, snmp_trap_id);
    }

    public void updateSeverityMinor(String ipdaddress,int snmp_trap_id, String status_alarm, int count) {
        //String SQL = "UPDATE temporary_alarm SET severity_minor = severity_minor + 1, status_alarm =? ,total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE ipaddress = ? AND snmp_trap_id = ?";
        String SQL = "UPDATE temporary_alarm SET severity_minor = ?, status_alarm =? ,total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE ipaddress = ? AND snmp_trap_id = ?";
        jdbcTemplate.update(SQL, count, status_alarm, count, ipdaddress, snmp_trap_id);
    }

    public void countSeverityMinor(int device_node_id, int snmp_trap_id, String status_alarm, int count) {
        String SQL = "UPDATE temporary_alarm SET severity_minor = ?, status_alarm =? ,total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE device_node_id = ? AND snmp_trap_id = ?";
        jdbcTemplate.update(SQL, count, status_alarm, count, device_node_id, snmp_trap_id);
    }

    public void updateSeverityCritical(String ipdaddress, int snmp_trap_id, String status_alarm, int count) {
        //String SQL = "UPDATE temporary_alarm SET severity_critical = severity_critical + 1, status_alarm =? ,total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE ipaddress = ? AND snmp_trap_id = ?";
        String SQL = "UPDATE temporary_alarm SET severity_critical = ?, status_alarm =? ,total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE ipaddress = ? AND snmp_trap_id = ?";
        jdbcTemplate.update(SQL, count, status_alarm, count, ipdaddress, snmp_trap_id);
    }

    public void countSeverityCritical(int device_node_id,int snmp_trap_id, String status_alarm, int count) {
        String SQL = "UPDATE temporary_alarm SET severity_critical = ?, status_alarm =? ,total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE device_node_id = ? AND snmp_trap_id = ?";
        jdbcTemplate.update(SQL, count, status_alarm, count, device_node_id, snmp_trap_id);
    }

    public void updateSeverityWarning(String ipdaddress, int snmp_trap_id, String status_alarm, int count) {
        //String SQL = "UPDATE temporary_alarm SET severity_warning = severity_warning + 1, status_alarm =? ,total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE ipaddress = ? AND snmp_trap_id = ?";
        String SQL = "UPDATE temporary_alarm SET severity_warning =?, status_alarm =? ,total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE ipaddress = ? AND snmp_trap_id = ?";
        jdbcTemplate.update(SQL, count, status_alarm, count, ipdaddress, snmp_trap_id);
    }

    public void countSeverityWarning(int device_node_id, int snmp_trap_id, String status_alarm, int count) {
        String SQL = "UPDATE temporary_alarm SET severity_warning =?, status_alarm =? ,total_alarm=total_alarm + ?, updated_at = (SELECT NOW()) WHERE device_node_id = ? AND snmp_trap_id = ?";
        jdbcTemplate.update(SQL, count, status_alarm, count, device_node_id, snmp_trap_id);
    }

    public void updateActiveSeverityCommLost(String ipdaddress, int device_node_id,int snmp_trap_id, String status_alarm) {
        //String SQL = "UPDATE temporary_alarm SET severity_commlost = severity_commlost + 1, status_alarm =? ,total_alarm=total_alarm + 1 , updated_at = (SELECT NOW()) WHERE ipaddress = ? AND snmp_trap_id = ?";
        //jdbcTemplate.update(SQL, status_alarm, ipdaddress, snmp_trap_id);
        String SQL = "UPDATE temporary_alarm SET severity_commlost = severity_commlost + 1, status_alarm =? ,total_alarm=total_alarm + 1 , updated_at = (SELECT NOW()) WHERE device_node_id = ? AND snmp_trap_id = ?";
        jdbcTemplate.update(SQL, status_alarm, device_node_id, snmp_trap_id);
    }


    // TODO: 3/11/2020 : value will be +1 if active alarm
    //public void updateSeverityCritical(String agent_ipdaddress,String oid){
    //    String SQL = "UPDATE temporary_alarm SET severity_critical = severity_critical + 1  WHERE ipaddress = ?";
    //    jdbcTemplate.update(SQL,agent_ipdaddress,oid);
    //}
}
