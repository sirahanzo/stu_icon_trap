package com.sinergiteknologiutama.snmp.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("trapMessageReceivedDao")
public class TrapMessageReceivedDao extends BaseDao {

    public void insertNewTrapMassage(String from_address, String trap_oid, String type_trap, String var_name1, int var_value1) throws DataAccessException {
        //String SQL = "INSERT INTO trap_message_received (1from_ipaddress, 2trap_oid, 3uptime, 4trap_type, 5var_name1, 6var_value1,7updated_at ) VALUES (?,?,\"00:00:00\",?,?,?,(SELECT DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'));";
        //String SQL = "INSERT INTO trap_message_received (from_ipaddress, trap_oid, uptime, trap_type, var_name1, var_value1,updated_at ) " +
        //        "VALUES (?,?,\"00:00:00\",?,?,?,(SELECT DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'));";
        String SQL = "INSERT INTO trap_message_received (from_ipaddress, trap_oid, uptime, trap_type, var_name1, var_value1 ,updated_at) VALUES (?,?,'00:00:00',?,?,?,(SELECT DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')))";
        jdbcTemplate.update(SQL, from_address, trap_oid, type_trap, var_name1, var_value1);

    }
}
