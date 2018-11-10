package de.phib.bootapps.dashboard;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The app infos configured in the application properties.
 */
@Component
@ConfigurationProperties("info.app")
public class InfoAppProperties {

    String name;

    String version;

    String deploydate;

    /**
     * Returns the value of configuration property name.
     * @return the value of configuration property name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of configuration property name.
     * @param name the value of configuration property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the value of configuration property version.
     * @return the value of configuration property version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of configuration property version.
     * @param version the value of configuration property version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Returns the value of configuration property deploydate.
     * @return the value of configuration property deploydate
     */
    public String getDeploydate() {
        return deploydate;
    }

    /**
     * Sets the value of configuration property deploydate.
     * @param deploydate the value of configuration property deploydate
     */
    public void setDeploydate(String deploydate) {
        this.deploydate = deploydate;
    }
}
