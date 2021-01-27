package com.sinergiteknologiutama.snmp.model;

public class RegionsModel {
    int id;
    int owner_id;
    String oid;
    String name;
    //String icon;
    private CitiesModel citiesModel;
    private SitesModel sitesModel;


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

    //public String getIcon() {
    //    return icon;
    //}
    //
    //public void setIcon(String icon) {
    //    this.icon = icon;
    //}


    public CitiesModel getCitiesModel() {
        return citiesModel;
    }

    public void setCitiesModel(CitiesModel citiesModel) {
        this.citiesModel = citiesModel;
    }

    public SitesModel getSitesModel() {
        return sitesModel;
    }

    public void setSitesModel(SitesModel sitesModel) {
        this.sitesModel = sitesModel;
    }
}
