package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.model.SitesModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SitesMapper implements RowMapper<SitesModel> {
    @Override
    public SitesModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        SitesModel site = new SitesModel();
        site.setId(rs.getInt("id"));
        site.setOwner_id(rs.getInt("owner_id"));
        site.setRegion_oid(rs.getString("region_oid"));
        site.setCity_oid(rs.getString("city_oid"));
        site.setOid(rs.getString("oid"));
        site.setSite_id_label(rs.getString("site_id_label"));
        site.setAddress(rs.getString("address"));
        site.setLatitude(rs.getString("latitude"));
        site.setLongitude(rs.getString("longitude"));
        site.setIcon(rs.getString("icon"));
        site.setSite_oid(rs.getString("site_oid"));
        site.setSite_name(rs.getString("site_name"));


        return site;
    }
}
