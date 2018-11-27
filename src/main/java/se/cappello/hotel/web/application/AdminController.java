package se.cappello.hotel.web.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }
}
