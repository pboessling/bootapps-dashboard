package de.phib.bootapps.dashboard.dashboard;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a bootapp on the dashboard.
 */
public class Bootapp {

    private String id;

    private String healthEndpointUrl;

    private String healthEndpointUsername;

    private String healthEndpointPassword;

    private String infoEndpointUrl;

    private String infoEndpointUsername;

    private String infoEndpointPassword;

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
     * Sets the health endpoint url of this Bootapp.
     * @param healthEndpointUrl the health endpoint url to set
     */
    public void setHealthEndpointUrl(String healthEndpointUrl) {
        this.healthEndpointUrl = healthEndpointUrl;
    }

    /**
     * Returns the health endpoint username of this Bootapp.
     * @return the health endpoint username of this Bootapp
     */
    public String getHealthEndpointUsername() {
        return healthEndpointUsername;
    }

    /**
     * Sets the health endpoint username of this Bootapp.
     * @param healthEndpointUsername the health endpoint username to set.
     */
    public void setHealthEndpointUsername(String healthEndpointUsername) {
        this.healthEndpointUsername = healthEndpointUsername;
    }

    /**
     * Returns the health endpoint password of this Bootapp.
     * @return the health endpoint password of this Bootapp
     */
    public String getHealthEndpointPassword() {
        return healthEndpointPassword;
    }

    /**
     * Sets the health endpoint password of this Bootapp.
     * @param healthEndpointPassword the health endpoint password to set.
     */
    public void setHealthEndpointPassword(String healthEndpointPassword) {
        this.healthEndpointPassword = healthEndpointPassword;
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
     * Returns the info endpoint username of this Bootapp.
     * @return the info endpoint username of this Bootapp
     */
    public String getInfoEndpointUsername() {
        return infoEndpointUsername;
    }

    /**
     * Set the info endpoint username of this Bootapp.
     * @param infoEndpointUsername the info endpoint username to set
     */
    public void setInfoEndpointUsername(String infoEndpointUsername) {
        this.infoEndpointUsername = infoEndpointUsername;
    }

    /**
     * Returns the info endpoint password of this Bootapp.
     * @return the info endpoint password of this Bootapp
     */
    public String getInfoEndpointPassword() {
        return infoEndpointPassword;
    }

    /**
     * Set the info endpoint password of this Bootapp.
     * @param infoEndpointPassword the info endpoint password to set
     */
    public void setInfoEndpointPassword(String infoEndpointPassword) {
        this.infoEndpointPassword = infoEndpointPassword;
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
