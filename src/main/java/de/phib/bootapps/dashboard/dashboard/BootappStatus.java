package de.phib.bootapps.dashboard.dashboard;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents the status of a bootapp on the dashboard.
 */
public class BootappStatus {

    private String id;

    private String health;

    private String info;

    /**
     * Returns the id of this BootappStatus.
     * @return the id of this BootappStatus
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of this BootappStatus.
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the health of this BootappStatus.
     * @return the health of this BootappStatus
     */
    public String getHealth() {
        return health;
    }

    /**
     * Sets the health of this BootappStatus.
     * @param health the health to set
     */
    public void setHealth(String health) {
        this.health = health;
    }

    /**
     * Returns the info of this BootappStatus.
     * @return the info of this BootappStatus
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the info of this BootappStatus.
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Returns a String representation of this BootappStatus.
     * @return a String representation of this BootappStatus
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("health", this.health)
                .append("info", this.info)
                .toString();
    }

}
