package hackathon.fpt.ktk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/")
public class HomeController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewHome() {
        ModelAndView m = new ModelAndView("welcome");
        return m;
    }

    @RequestMapping(value = "/ats/checkConnection", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String checkConnection() {
        System.out.println("Check Connection successfully.");
        return "true";
    }
}
