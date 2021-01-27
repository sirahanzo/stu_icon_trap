package com.sinergiteknologiutama.snmp.mapper;

import com.sinergiteknologiutama.snmp.model.CitiesModel;
import com.sinergiteknologiutama.snmp.model.RegionsModel;
import com.sinergiteknologiutama.snmp.model.SitesModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegionsMapper implements RowMapper<RegionsModel> {

    List<RegionsModel> regionSite = new ArrayList<RegionsModel>();
    List<RegionsModel> regionCity = new ArrayList<RegionsModel>();

    @Override
    public RegionsModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        RegionsModel region = new RegionsModel();
        CitiesModel city = new CitiesModel();
        SitesModel site = new SitesModel();

        region.setId(rs.getInt("id"));
        region.setOwner_id(rs.getInt("owner_id"));
        region.setOid(rs.getString("oid"));
        region.setName(rs.getString("name"));

        //city.setOwner_id(rs.getInt("owner_id"));
        //city.setRegion_oid(rs.getString("region_oid"));
        city.setOid(rs.getString("oid"));
        //city.setName(rs.getString("name"));
        //city.setIcon(rs.getString("icon"));
        city.setCity_name(rs.getString("city_name"));


        //site.setOwner_id(rs.getInt("owner_id"));
        //site.setRegion_oid(rs.getString("region_oid"));
        site.setCity_oid(rs.getString("city_oid"));
        //site.setOid(rs.getString("oid"));
        //site.setName(rs.getString("name"));
        site.setSite_oid(rs.getString("site_oid"));
        site.setSite_name(rs.getString("site_name"));
        site.setSite_id_label(rs.getString("site_id_label"));
        //site.setAddress(rs.getString("address"));
        //site.setLatitude(rs.getString("latitude"));
        //site.setLongitude(rs.getString("longitude"));
        //site.setIcon(rs.getString("icon"));

        region.setCitiesModel(city);
        regionCity.add(region);

        region.setSitesModel(site);
        regionSite.add(region);




        return region;
    }
}
