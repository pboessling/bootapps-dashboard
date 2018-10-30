package de.phib.bootapps.dashboard.dashboard;

import de.phib.bootapps.dashboard.dashboard.model.AppHealth;
import de.phib.bootapps.dashboard.dashboard.model.AppInfo;
import de.phib.bootapps.dashboard.dashboard.model.Bootapp;
import de.phib.bootapps.dashboard.dashboard.model.BootappStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Controller for providing a Web API retrieve the status of bootapps.
 */
@RestController
@RequestMapping("/api/status")
public class ApiStatusController {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    private static final String VALUE_NOT_AVAILABLE = "N/A";

    private RestTemplateBuilder restTemplateBuilder;

    private Map<String, Map<String, List<Bootapp>>> bootapps;

    /**
     * Builds a new instance of DashboardController.
     * @param dashboardProperties the DashboardProperties
     * @param restTemplateBuilder  the RestTemplateBuilder
     */
    @Autowired
    public ApiStatusController(DashboardProperties dashboardProperties, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.bootapps = dashboardProperties.getBootapps();
    }

    /**
     * API function to return the status (health + info) of all bootapps as JSON.
     * @return the status (health + info) of all bootapps as JSON
     */
    @GetMapping("/")
    public Map<String, Map<String, List<BootappStatus>>> getStatus() {
        Map<String, Map<String, List<BootappStatus>>> status = new HashMap<>();

        for(Map.Entry<String, Map<String, List<Bootapp>>> environment : bootapps.entrySet()) {
            status.put(environment.getKey(), getEnvironmentStatus(environment.getValue()));
        }

        return status;
    }

    /**
     * API function to return the status (health + info) of all bootapps of an environment as JSON.
     * @param environmentId the environment id
     * @return the status (health + info) of all bootapps of an environment as JSON
     */
    @GetMapping("/{environmentId}")
    public Map<String, List<BootappStatus>> getEnvironmentStatus(@PathVariable("environmentId") String environmentId) {
        Map<String, List<Bootapp>> environment = this.bootapps.get(environmentId);
        return getEnvironmentStatus(environment);
    }

    /**
     * Returns the status (health + info) of all bootapps of an environment as JSON.
     * @param environment the environment
     * @return the status (health + info) of all bootapps of an environment as JSON
     */
    private Map<String, List<BootappStatus>> getEnvironmentStatus(Map<String, List<Bootapp>> environment) {
        Map<String, List<BootappStatus>> environmentStatus= new HashMap<>();

        if(environment != null) {
            for(Map.Entry<String, List<Bootapp>> host : environment.entrySet()) {
                environmentStatus.put(host.getKey(), getHostStatus(host.getValue()));
            }
        }

        return environmentStatus;
    }

    /**
     * API function to return the status (health + info) of all bootapps of a host as JSON.
     * @param environmentId the environment id
     * @param hostId the hosts id
     * @return the status (health + info) of all bootapps of a host as JSON
     */
    @GetMapping("/{environmentId}/{hostId}")
    public @ResponseBody List<BootappStatus> getHostStatus(@PathVariable("environmentId") String environmentId, @PathVariable("hostId") String hostId) {
        List<BootappStatus> hostStatus = new ArrayList<>();

        Map<String, List<Bootapp>> environment = this.bootapps.get(environmentId);

        if(environment != null) {
            List<Bootapp> host = environment.get(hostId);
            hostStatus =  getHostStatus(host);
        }

        return hostStatus;
    }

    /**
     * Returns the status (health + info) of all bootapps of a host as JSON.
     * @param host the host
     * @return  the status (health + info) of all bootapps of a host as JSON
     */
    private List<BootappStatus> getHostStatus(List<Bootapp> host) {
        List<BootappStatus> hostStatus = new ArrayList<>();

        if(host != null) {
            for(Bootapp bootapp : host) {
                BootappStatus bootappStatus = fetchStatusFromEndpoints(bootapp);
                hostStatus.add(bootappStatus);
            }
        }

        return hostStatus;
    }

    /**
     * API function to return the status (health + info) of a bootapp as JSON.
     * @return the status (health + info) of a bootapp as JSON
     */
    @GetMapping("/{environmentId}/{hostId}/{bootappId}")
    public @ResponseBody BootappStatus getBootappStatus(@PathVariable("environmentId") String environmentId, @PathVariable("hostId") String hostId, @PathVariable("bootappId") String bootappId) {
        BootappStatus bootappStatus = null;

        Map<String, List<Bootapp>> environment = this.bootapps.get(environmentId);
        if(environment != null) {
            List<Bootapp> host = environment.get(hostId);
            if(host != null) {
                for(Bootapp bootapp : host) {
                    if(bootapp.getId().equals(bootappId)) {
                        bootappStatus = fetchStatusFromEndpoints(bootapp);
                    }
                }
            }
        }

        return bootappStatus;
    }

    /**
     * Fetches the status (health + info) of a bootapp from the respective endpoints
     * @param bootapp the bootapp
     * @return the status of the bootapp
     */
    private BootappStatus fetchStatusFromEndpoints(Bootapp bootapp) {
        AppHealth health = null;
        if(bootapp.getHealthEndpointUrl() != null) {
            try {
                LOG.debug("Fetching health for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getHealthEndpointUrl() + "'");
                RestTemplate restTemplate = buildRestTemplate(bootapp.getHealthEndpointUsername(), bootapp.getHealthEndpointPassword());
                health = restTemplate.getForObject(bootapp.getHealthEndpointUrl(), AppHealth.class);
                LOG.debug("Health: " + health.getStatus());
            } catch (Exception e) {
                LOG.error("An error occured why trying to fetch health for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getHealthEndpointUrl() + "'", e);
            }
        } else {
            LOG.warn("Health endpoint url not configure for bootapp '" + bootapp.getId() + "'");
        }


        AppInfo info = null;
        if(bootapp.getInfoEndpointUrl() != null) {
            try {
                LOG.debug("Fetching info for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getInfoEndpointUrl()
                        + "'");
                RestTemplate restTemplate = buildRestTemplate(bootapp.getInfoEndpointUsername(), bootapp.getInfoEndpointPassword());
                info = restTemplate.getForObject(bootapp.getInfoEndpointUrl(), AppInfo.class);
                LOG.debug("Info: " + info.getApp());
            } catch (Exception e) {
                LOG.error("An error occured why trying to fetch info for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getInfoEndpointUrl() + "'", e);
            }
        } else {
            LOG.warn("Info endpoint url not configure for bootapp '" + bootapp.getId() + "'");
        }

        BootappStatus bootappStatus = new BootappStatus();
        bootappStatus.setId(bootapp.getId());
        bootappStatus.setHealth((health != null) ? health.getStatus() : VALUE_NOT_AVAILABLE);
        bootappStatus.setInfo((info != null && info.getApp() != null) ? info.getApp().toString() : VALUE_NOT_AVAILABLE);

        return bootappStatus;
    }

    /**
     * Builds a RestTemplate with the given basic auth username and password.
     * @param username the basic auth username
     * @param password the basic auth password
     * @return a RestTemplate
     */
    private RestTemplate buildRestTemplate(String username, String password) {
        if(!StringUtils.isEmpty(username)) {
            return this.restTemplateBuilder.basicAuthorization(username, password).build();
        } else {
            return this.restTemplateBuilder.build();
        }
    }

}
