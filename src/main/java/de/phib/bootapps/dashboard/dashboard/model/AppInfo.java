package de.phib.bootapps.dashboard.dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * Represents a map of arbitrary info about an app.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppInfo {

    private Map<String, String> app;

    /**
     * Returns the info map of this AppInfo.
     * @return the info map of this AppInfo
     */
    public Map<String, String> getApp() {
        return app;
    }

    /**
     * Sets the info map of this AppInfo.
     * @param app the info map to set
     */
    public void setApp(Map<String, String> app) {
        this.app = app;
    }

    /**
     * Returns a String representation of this AppInfo.
     * @return a String representation of this AppInfo
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("app", this.app)
                .toString();
    }

}
