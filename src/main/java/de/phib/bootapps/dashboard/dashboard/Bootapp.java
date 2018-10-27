package de.phib.bootapps.dashboard.dashboard;

public class Bootapp {

    // TODO: Rename to id
    private String name;

    private String healthEndpointUrl;

    private String infoEndpointUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHealthEndpointUrl() {
        return healthEndpointUrl;
    }

    public void setHealthEndpointUrl(String healthEndpointUrl) {
        this.healthEndpointUrl = healthEndpointUrl;
    }

    public String getInfoEndpointUrl() {
        return infoEndpointUrl;
    }

    public void setInfoEndpointUrl(String infoEndpointUrl) {
        this.infoEndpointUrl = infoEndpointUrl;
    }
}
