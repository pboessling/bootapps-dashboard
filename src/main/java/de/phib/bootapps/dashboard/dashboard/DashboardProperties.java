package de.phib.bootapps.dashboard.dashboard;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The configuration of the dashboard configured in the application properties.
 */
@Component
@ConfigurationProperties("dashboard")
public class DashboardProperties {

    private boolean autoreload;

    private int autoreloadInterval;

    private final List<Bootapp> bootapps = new ArrayList<>();

    /**
     * Returns the value of configuration property autoreload.
     * @return the value of configuration property autoreload
     */
    public boolean getAutoreload() {
        return this.autoreload;
    }

    /**
     * Sets the value of configuration property autoreload
     * @param autoreload the value of configuration property autoreload
     */
    public void setAutoreload(boolean autoreload) {
        this.autoreload = autoreload;
    }

    /**
     * Returns the value of configuration property autoreload interval.
     * @return the value of configuration property autoreload interval
     */
     public int getAutoreloadInterval() {
        return this.autoreloadInterval;
    }

    /**
     * Sets the value of configuration property autoreload interval.
     * @param autoreloadInterval the value of configuration property autoreload interval
     */
    public void setAutoreloadInterval(int autoreloadInterval) {
        this.autoreloadInterval = autoreloadInterval;
    }

    /**
     * Returns a list of bootapps configured in the application properties.
     * @return a list of bootapps
     */
    public List<Bootapp> getBootapps() {
        return this.bootapps;
    }

}
