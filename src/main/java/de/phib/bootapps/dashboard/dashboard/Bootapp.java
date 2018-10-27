package de.phib.bootapps.dashboard.dashboard;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents the configuration of a bootapp on the dashboard.
 */
public class Bootapp {

    private String id;

    private String healthEndpointUrl;

    private String infoEndpointUrl;

    /**
     * Returns the id of this Bootapp.
     * @return the id of this Bootapp
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of this Bootapp
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the health endpoint url of this Bootapp.
     * @return the health endpoint url of this Bootapp
     */
    public String getHealthEndpointUrl() {
        return healthEndpointUrl;
    }

    /**
     * Set the health endpoint url of this Bootapp.
     * @param healthEndpointUrl the health endpoint url to set
     */
    public void setHealthEndpointUrl(String healthEndpointUrl) {
        this.healthEndpointUrl = healthEndpointUrl;
    }

    /**
     * Returns the info endpoint url of this Bootapp.
     * @return the info endpoint url of this Bootapp
     */
    public String getInfoEndpointUrl() {
        return infoEndpointUrl;
    }

    /**
     * Set the info endpoint url of this Bootapp.
     * @param infoEndpointUrl the info endpoint url to set
     */
    public void setInfoEndpointUrl(String infoEndpointUrl) {
        this.infoEndpointUrl = infoEndpointUrl;
    }

    /**
     * Returns a String representation of this Bootapp.
     * @return a String representation of this Bootapp
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("healthEndpointUrl", this.healthEndpointUrl)
                .append("infoEndpointUrl", this.infoEndpointUrl)
                .toString();
    }

}
