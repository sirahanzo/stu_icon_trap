package com.sinergiteknologiutama.snmp.model;

import java.util.ArrayList;
import java.util.List;

public class SitesModel {

    private int id;
    private int owner_id;
    private String region_oid;
    private String city_oid;
    private String oid;
    private String name;
    private String site_id_label;
    private String address;
    private String longitude;
    private String latitude;
    private String icon;
    private String site_name;
    private String site_oid;

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

    public String getCity_oid() {
        return city_oid;
    }

    public void setCity_oid(String city_oid) {
        this.city_oid = city_oid;
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

    public String getSite_id_label() {
        return site_id_label;
    }

    public void setSite_id_label(String site_id_label) {
        this.site_id_label = site_id_label;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getSite_oid() {
        return site_oid;
    }

    public void setSite_oid(String site_oid) {
        this.site_oid = site_oid;
    }
}
