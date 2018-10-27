package de.phib.bootapps.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BootappsDashboardController {

    private final DashboardProperties dashboardProperties;

    @Autowired
    public BootappsDashboardController(DashboardProperties dashboardProperties) {
        this.dashboardProperties = dashboardProperties;
    }

    @GetMapping("/dashboard")
    public String renderDashboard(@RequestParam(name="autoreload", required=false, defaultValue="false") String autoreload, Model model) {
        Boolean autoreloadAttribute = autoreload.equals("true");
        model.addAttribute("autoreload", autoreloadAttribute);

        List<Bootapp> bootapps = this.dashboardProperties.getBootapps();
        model.addAttribute("bootapps", bootapps);

        return "dashboard";
    }

}
