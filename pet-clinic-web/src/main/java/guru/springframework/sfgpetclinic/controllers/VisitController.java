package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author john
 * @since 03/03/2024
 */
@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/visits/new")
    public String initNewVisitForm(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);
        Visit visit = Visit.builder().build();
        pet.addVisit(visit);
        model.addAttribute  ("visit", visit);
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/visits/new")
    public String processNewVisitForm(@PathVariable Long ownerId, @PathVariable Long petId, @ModelAttribute Visit visit, Model model, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("error");
            System.out.println(visit);
            model.addAttribute("visit",visit);
            return "pets/createOrUpdateVisitForm";
        }
        Pet pet = petService.findById(petId);
        pet.addVisit(visit);
        visitService.save(visit);
        return "redirect:/owners/" +ownerId;
    }
}
