package com.dns.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author valeriali on {25.08.2023}
 * @project TechnoShopProject
 */
@Controller
public class MainController {

    @GetMapping("")
    public String viewHome() {
        return "index";
    }
}
