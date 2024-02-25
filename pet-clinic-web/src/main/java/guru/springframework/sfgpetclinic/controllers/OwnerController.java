package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

/**
 * @author john
 * @since 19/12/2023
 */
@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"", "/", "/index"})
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());
            return "owners/index";
    }

    @RequestMapping("find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping("searchResults")
    public String processFindForm(Model model, Owner owner, BindingResult result) {
        if(owner.getLastName() == null)
            owner.setLastName("");
        Set<Owner> results = ownerService.findByLastNameContainsIgnoreCase(owner.getLastName());
        if (results.isEmpty()) {
            // No Owners Found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // One Owner Found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // Multiple Owners Founds
            model.addAttribute("selection", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{id}")
    public ModelAndView displayOwner(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject("owner", ownerService.findById(id));
        return modelAndView;
    }
}
