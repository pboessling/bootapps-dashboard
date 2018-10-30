package de.phib.bootapps.dashboard.dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents the health status of an app, which can be "UP" or "DOWN".
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppHealth {

    private String status;

    /**
     * Returns the status of this AppHealth.
     * @return the status of this AppHealth
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this AppHealth.
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a String representation of this AppHealth.
     * @return a String representation of this AppHealth
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", this.status)
                .toString();
    }

}
