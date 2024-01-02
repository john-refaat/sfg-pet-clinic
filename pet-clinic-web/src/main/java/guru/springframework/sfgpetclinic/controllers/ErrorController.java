package guru.springframework.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author john
 * @since 02/01/2024
 */
@Controller
public class ErrorController {

    @RequestMapping("oups")
    public String error(){
        return "notImplemented";
    }
}
