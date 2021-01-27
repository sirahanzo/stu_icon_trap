package com.sinergiteknologiutama.snmp.dao;

import com.sinergiteknologiutama.snmp.mapper.RegionsMapper;
import com.sinergiteknologiutama.snmp.model.RegionsModel;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("regionsDao")
public class RegionsDao extends BaseDao {

    public RegionsModel validAgentRegion(String ipaddress) throws DataAccessException{
        //String SQL = "SELECT * FROM regions r LEFT JOIN sites s on r.oid = s.region_oid LEFT JOIN device_nodes dn on s.oid = dn.site_oid WHERE ipaddress = ?";
        //String SQL = "SELECT * FROM regions LEFT JOIN cities c on regions.oid = c.region_oid LEFT JOIN sites s on c.oid = s.city_oid LEFT JOIN device_nodes dn on s.oid = dn.site_oid WHERE ipaddress = ?";
        //String SQL = "SELECT * FROM regions r LEFT JOIN cities c on r.oid = c.region_oid LEFT JOIN sites s on c.oid = s.city_oid LEFT JOIN device_nodes dn on s.oid = dn.site_oid WHERE ipaddress = ?";
        String SQL = "SELECT r.id as id,r.owner_id as owner_id,r.oid as oid,r.name as name,c.name as city_name,s.city_oid,s.name as site_name,s.oid as site_oid,site_id_label FROM regions r LEFT JOIN cities c on r.oid = c.region_oid LEFT JOIN sites s on c.oid = s.city_oid LEFT JOIN device_nodes dn on s.oid = dn.site_oid WHERE ipaddress =?";
        return jdbcTemplate.queryForObject(SQL,new Object[]{ipaddress},new RegionsMapper());
    }
}
