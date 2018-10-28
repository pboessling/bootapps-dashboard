package de.phib.bootapps.dashboard.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for displaying the dashboard view, as well as providing a Web API to interact with the dashboard from the
 * frontend.
 */
@Controller
public class DashboardController {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    private static final String VALUE_NOT_AVAILABLE = "N/A";

    private RestTemplateBuilder restTemplateBuilder;

    private boolean autoreload;

    private int autoreloadInterval;

    private final List<Bootapp> bootapps;

    /**
     * Builds a new instance of DashboardController.
     * @param dashboardProperties the DashboardProperties
     * @param restTemplateBuilder  the RestTemplateBuilder
     */
    @Autowired
    public DashboardController(DashboardProperties dashboardProperties, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.autoreload = dashboardProperties.getAutoreload();
        this.autoreloadInterval = dashboardProperties.getAutoreloadInterval();
        this.bootapps = dashboardProperties.getBootapps();
    }

    /**
     * Returns the dashboard view.
     * @param model the model
     * @return the dashboard view
     */
    @GetMapping("/dashboard")
    public String renderDashboard(Model model) {
        model.addAttribute("autoreload", this.autoreload);
        model.addAttribute("autoreloadInterval", this.autoreloadInterval);
        model.addAttribute("bootapps", bootapps);

        return "dashboard";
    }

    /**
     * API function to return the status (health + info) of all bootapps as JSON.
     * @return the status (health + info) of all bootapps as JSON
     */
    @GetMapping("/api/dashboard/status")
    public  @ResponseBody
    List<BootappStatus> getBootappStatuses() {
        List<BootappStatus> bootappStatuses = new ArrayList<>();

        for(Bootapp bootapp : bootapps) {
            BootappStatus bootappStatus = fetchStatusFromEndpoints(bootapp);
            bootappStatuses.add(bootappStatus);
        }

        return bootappStatuses;
    }

    /**
     * API function to return the status (health + info) of a bootapps as JSON.
     * @return the status (health + info) of a bootapp as JSON
     */
    @GetMapping("/api/dashboard/status/{id}")
    public @ResponseBody BootappStatus getBootappStatus(@PathVariable("id") String id) {
        BootappStatus bootappStatus = null;

        Bootapp bootapp = findBootappById(id);
        if(bootapp != null) {
            bootappStatus = fetchStatusFromEndpoints(bootapp);
        }

        return bootappStatus;
    }

    /**
     * Finds a bootapp in the list of bootapps by its id.
     * @param id the id
     * @return the bootapp
     */
    private Bootapp findBootappById(String id) {
        for(Bootapp bootapp : bootapps) {
            if(bootapp.getId().equals(id)) {
                return bootapp;
            }
        }

        return null;
    }

    private BootappStatus fetchStatusFromEndpoints(Bootapp bootapp) {
        AppHealth health = null;
        try {
            LOG.debug("Fetching health for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getHealthEndpointUrl() + "'");
            RestTemplate restTemplate = buildRestTemplate(bootapp.getHealthEndpointUsername(), bootapp.getHealthEndpointPassword());
            health = restTemplate.getForObject(bootapp.getHealthEndpointUrl(), AppHealth.class);
            LOG.debug("Health: " + health.getStatus());
        } catch (Exception e) {
            LOG.error("An error occured why trying to fetch health for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getHealthEndpointUrl() + "'", e);
        }

        AppInfo info = null;
        try {
            LOG.debug("Fetching info for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getInfoEndpointUrl() + "'");
            RestTemplate restTemplate = buildRestTemplate(bootapp.getInfoEndpointUsername(), bootapp.getInfoEndpointPassword());
            info = restTemplate.getForObject(bootapp.getInfoEndpointUrl(), AppInfo.class);
            LOG.debug("Info: " + info.getApp());
        } catch (Exception e) {
            LOG.error("An error occured why trying to fetch info for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getInfoEndpointUrl() + "'", e);
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