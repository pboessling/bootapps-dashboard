package de.phib.bootapps.dashboard.dashboard;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a host on the dashboard.
 */
public class Host {

    private String id;

    private final List<Bootapp> bootapps = new ArrayList<>();

    /**
     * Returns the id of this Host.
     * @return the id of this Host
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of this Host
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the list of bootapps of this host.
     * @return the list of bootapps
     */
    public List<Bootapp> getBootapps() {
        return this.bootapps;
    }

    /**
     * Returns a String representation of this Host.
     * @return a String representation of this Host
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("bootapps", bootapps)
                .toString();
    }

}
