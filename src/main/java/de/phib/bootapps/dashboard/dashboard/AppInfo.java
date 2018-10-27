package de.phib.bootapps.dashboard.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppInfo {

    private Map<String, String> app;

    public Map<String, String> getApp() {
        return app;
    }

    public void setApp(Map<String, String> app) {
        this.app = app;
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("app", app)
                .toString();
    }
}
