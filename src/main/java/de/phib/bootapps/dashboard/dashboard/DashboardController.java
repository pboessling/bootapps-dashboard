package de.phib.bootapps.dashboard.dashboard;

import de.phib.bootapps.dashboard.dashboard.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for displaying the dashboard views.
 */
@Controller
public class DashboardController {

    private boolean autoreload;

    private int autoreloadInterval;

    private Map<String, Map<String, List<Bootapp>>> bootapps;

    /**
     * Builds a new instance of DashboardController.
     * @param dashboardProperties the DashboardProperties
     */
    @Autowired
    public DashboardController(DashboardProperties dashboardProperties) {
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
        model.addAttribute("environmentIds", this.bootapps.keySet());

        return "dashboard";
    }

    /**
     * Returns the environment view.
     * @param model the model
     * @return the environment view
     */
    @GetMapping("/dashboard/{environmentId}")
    public String renderDashboard(@PathVariable("environmentId") String environmentId, Model model) {
        model.addAttribute("autoreload", this.autoreload);
        model.addAttribute("autoreloadInterval", this.autoreloadInterval);
        model.addAttribute("environmentIds", this.bootapps.keySet());
        model.addAttribute("hosts", this.bootapps.get(environmentId));
        model.addAttribute("environmentId", environmentId);

        return "environment";
    }

}