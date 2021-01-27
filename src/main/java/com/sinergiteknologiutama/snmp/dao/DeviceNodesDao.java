package com.sinergiteknologiutama.snmp.dao;

import com.sinergiteknologiutama.snmp.mapper.DeviceNodesMapper;
import com.sinergiteknologiutama.snmp.model.DeviceNodesModel;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("deviceNodesDao")
public class DeviceNodesDao extends BaseDao {

    public List<DeviceNodesModel> listDeviceNode(String ipaddress) {
        String SQL = "select * from device_nodes WHERE  ipaddress = ?";
        List<DeviceNodesModel> nodesModelList = jdbcTemplate.query(SQL, new Object[]{ipaddress}, new DeviceNodesMapper());
        return nodesModelList;
    }

    //public List<DeviceNodesModel> listTrapNode(String ipaddress) throws DataAccessException {
    //    String SQL = "SELECT * FROM device_nodes dn LEFT JOIN device_snmp_trap dst ON dn.device_id= dst.device_id WHERE ipaddress = ?";
    //    List<DeviceNodesModel> nodesModelList = jdbcTemplate.query(SQL, new Object[]{ipaddress}, new DeviceNodesMapper());
    //    return nodesModelList;
    //}

    //public DeviceNodesModel validAgentNode(String ipaddress) {
    //    String SQL = "SELECT * FROM devices d LEFT JOIN device_nodes dn on d.id = dn.device_id LEFT JOIN device_snmp_trap dst on d.id = dst.device_id WHERE dn.ipaddress =?";
    //    DeviceNodesModel deviceNode = jdbcTemplate.queryForObject(SQL, new Object[]{ipaddress}, new DeviceNodesMapper());
    //    return deviceNode;
    //}

    public DeviceNodesModel validAgentNode(String ipaddress) {
        String SQL = "SELECT * FROM device_nodes WHERE ipaddress =?";
        DeviceNodesModel deviceNode = jdbcTemplate.queryForObject(SQL, new Object[]{ipaddress}, new DeviceNodesMapper());
        return deviceNode;
    }

}
