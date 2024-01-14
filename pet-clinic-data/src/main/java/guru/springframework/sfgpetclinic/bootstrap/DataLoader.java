package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author john
 * @since 21/12/2023
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        if(petTypeService.findAll().isEmpty())
            loadData();

    }

    private void loadData() {
        //Pet Types
        PetType cat = new PetType("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        PetType dog = new PetType("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        System.out.println("Pet Types Loaded...");

        //Owners
        Owner mike = Owner.builder().firstName("Mike").lastName("Weston").city("Cairo")
                .address("Madinaty G68").telephone("1223334567").build();


        Pet rosco = Pet.builder().name("Rosco")
                .type(savedDogPetType).birthDate(LocalDate.now())
                .owner(mike).build();
        mike.addPet(rosco);
        ownerService.save(mike);

        Owner fiona = Owner.builder().firstName("Fiona").lastName("Glenanne").city("Alexandria")
                .address("San Stefano").telephone("2897643123").build();

        Pet spock = Pet.builder().name("Spock").type(savedCatPetType).birthDate(LocalDate.now()).owner(fiona).build();
        fiona.addPet(spock);

        ownerService.save(fiona);

        System.out.println("Owners Loaded...");

        Speciality radiology = new Speciality("Radiology");
        radiology =specialityService.save(radiology);

        Speciality surgery = new Speciality("Surgery");
        surgery =specialityService.save(surgery);

        Speciality dentistry = new Speciality("Dentistry");
        dentistry =specialityService.save(dentistry);

        //Vets
        Vet vet1 = Vet.builder().firstName("Sam").lastName("Axe").build();
        vet1.getSpecialities().add(radiology);
        vetService.save(vet1);

        Vet vet2 = Vet.builder().firstName("Jessie").lastName("Porter").build();
        vet2.getSpecialities().add(dentistry);
        vetService.save(vet2);

        System.out.println("Vets Loaded...");

        Visit catVisit = Visit.builder().date(LocalDate.now()).pet(spock).description("Sneezy Kitty").build();
        visitService.save(catVisit);
        System.out.println("Visit Loaded");

        System.out.println(visitService.findAll());
    }
}
