package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.model.CitiesModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CitiesMapper implements RowMapper<CitiesModel> {

    @Override
    public CitiesModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        CitiesModel city = new CitiesModel();

        city.setId(rs.getInt("id"));
        city.setOwner_id(rs.getInt("owner_id"));
        city.setRegion_oid(rs.getString("region_oid"));
        city.setOid(rs.getString("oid"));
        city.setName(rs.getString("name"));
        city.setIcon(rs.getString("icon"));
        city.setCity_name(rs.getString("city_name"));

        return  city;
    }
}
