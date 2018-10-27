package de.phib.bootapps.dashboard.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    private final List<Bootapp> bootapps;

    /**
     * Builds a new instance of DashboardController.
     * @param dashboardProperties the DashboardProperties
     */
    @Autowired
    public DashboardController(DashboardProperties dashboardProperties) {
        this.bootapps = dashboardProperties.getBootapps();
    }

    /**
     * Returns the dashboard view.
     * @param autoreload whether the view should automatically be reloaded
     * @param model the model
     * @return the dashboard view
     */
    @GetMapping("/dashboard")
    public String renderDashboard(@RequestParam(name="autoreload", required=false, defaultValue="false") String autoreload, Model model) {
        Boolean autoreloadAttribute = autoreload.equals("true");
        model.addAttribute("autoreload", autoreloadAttribute);

        model.addAttribute("bootapps", bootapps);

        return "dashboard";
    }

    /**
     * API function to return all bootapps as JSON.
     * @return all bootapps as JSON
     */
    @GetMapping("/api/dashboard/bootapps")
    public @ResponseBody
    List<Bootapp> getBootapps() {
        return bootapps;
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
            RestTemplate restTemplate = new RestTemplate();

            AppHealth health = null;
            try {
                LOG.debug("Fetching health for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getHealthEndpointUrl() + "'");
                health = restTemplate.getForObject(bootapp.getHealthEndpointUrl(), AppHealth.class);
                LOG.debug("Health: " + health.getStatus());
            } catch (Exception e) {
                LOG.error("An error occured why trying to fetch health for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getHealthEndpointUrl() + "'", e);
            }

            AppInfo info = null;
            try {
                LOG.debug("Fetching info for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getInfoEndpointUrl() + "'");
                info = restTemplate.getForObject(bootapp.getInfoEndpointUrl(), AppInfo.class);
                LOG.info("Info: " + info.getApp());
            } catch (Exception e) {
                LOG.error("An error occured why trying to fetch info for bootapp '" + bootapp.getId() + "' from url '" + bootapp.getInfoEndpointUrl() + "'", e);
            }

                BootappStatus bootappStatus = new BootappStatus();
                bootappStatus.setId(bootapp.getId());
                bootappStatus.setHealth((health != null) ? health.getStatus() : VALUE_NOT_AVAILABLE);
                bootappStatus.setInfo((info != null) ? info.getApp().toString() : VALUE_NOT_AVAILABLE);

                bootappStatuses.add(bootappStatus);
        }

        return bootappStatuses;
    }

}
