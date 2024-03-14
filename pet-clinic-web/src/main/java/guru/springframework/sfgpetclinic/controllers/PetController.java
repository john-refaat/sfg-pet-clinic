package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author john
 * @since 28/02/2024
 */
@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateForm";
    private PetService petService;
    private OwnerService ownerService;
    private PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Set<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner populateOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Model model, @ModelAttribute Owner owner) {
        System.out.println(model.getAttribute("owner"));
        System.out.println(owner);
        Pet pet  = Pet.builder().build();
        owner.addPet(pet);
        model.addAttribute("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(@ModelAttribute Owner owner, @ModelAttribute Pet pet, Model model, BindingResult result) {
        System.out.println(pet);
       if (StringUtils.hasLength(pet.getName()) && owner.findPetByNameIgnoreNew(pet.getName()).isPresent()) {
            result.rejectValue("name", "duplicate", "Already Exists");
        }
        if (result.hasErrors()) {
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }
        owner.addPet(pet);
        Pet savedPet = petService.save(pet);
        return "redirect:/owners/" + savedPet.getOwner().getId();
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(Model model, @ModelAttribute Owner owner, @PathVariable Long petId) {
        Pet pet = petService.findById(petId);
        System.out.println(pet);
        System.out.println(pet.getOwner());
        model.addAttribute("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@PathVariable Long ownerId, @ModelAttribute Pet pet, @PathVariable Long petId, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }
        Owner owner = ownerService.findById(ownerId);
        Optional<Pet> petOptional = owner.getPets().stream().filter(p -> p.getId().equals(petId)).findFirst();
        petOptional.ifPresent(p -> {
            if(StringUtils.hasLength(pet.getName()))
                p.setName(pet.getName());
            if(Objects.nonNull(pet.getBirthDate()))
                p.setBirthDate(pet.getBirthDate());
            if(Objects.nonNull(pet.getType()))
                p.setType(pet.getType());
            petService.save(p);
        });

        return "redirect:/owners/" + owner.getId();
    }
}
