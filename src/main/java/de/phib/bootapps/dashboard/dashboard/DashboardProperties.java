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

    private final List<Bootapp> bootapps = new ArrayList<>();

    /**
     * Returns a list of bootapps configured in the application properties.
     * @return a list of bootapps
     */
    public List<Bootapp> getBootapps() {
        return bootapps;
    }
}
