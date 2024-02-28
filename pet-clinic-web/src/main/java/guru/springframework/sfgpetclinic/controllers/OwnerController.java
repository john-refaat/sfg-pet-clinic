package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/create")
    public String initCreateForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/ownerForm";
    }

    @PostMapping("/create")
    public String createOwner(Owner owner, BindingResult result) {
        if(result.hasErrors())
            return "owners/ownersForm";
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{id}/update")
    public String initUpdateForm(@PathVariable String id, Model model) {
        model.addAttribute("owner", ownerService.findById(Long.valueOf(id)));
        return "owners/ownerForm";
    }

    @PostMapping("/{id}/update")
    public String updateOwner(@PathVariable String id, @ModelAttribute Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return "owners/ownerForm";
        }
        owner.setId(Long.valueOf(id));
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();

    }
}
