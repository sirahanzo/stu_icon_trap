package com.sinergiteknologiutama.snmp.model;

import java.util.ArrayList;
import java.util.List;

public class CitiesModel {
    int id;
    int owner_id;
    String region_oid;
    String oid;
    String name;
    String icon;
    String city_name;


    List<RegionsModel> regionsModels = new ArrayList<RegionsModel>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getRegion_oid() {
        return region_oid;
    }

    public void setRegion_oid(String region_oid) {
        this.region_oid = region_oid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<RegionsModel> getRegionsModels() {
        return regionsModels;
    }

    public void setRegionsModels(List<RegionsModel> regionsModels) {
        this.regionsModels = regionsModels;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
