package de.phib.bootapps.dashboard.dashboard;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("dashboard")
public class DashboardProperties {

    private final List<Bootapp> bootapps = new ArrayList<>();

    public List<Bootapp> getBootapps() {
        return bootapps;
    }
}
