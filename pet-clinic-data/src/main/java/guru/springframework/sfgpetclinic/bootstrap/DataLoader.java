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
        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        System.out.println("Pet Types Loaded...");

        //Owners
        Owner mike = new Owner();
        mike.setFirstName("Michael");
        mike.setLastName("Weston");
        mike.setAddress("Madinaty");
        mike.setCity("Cairo");
        mike.setTelephone("1233344567");

        Pet rosco = new Pet();
        rosco.setName("Rosco");
        rosco.setType(savedDogPetType);
        rosco.setBirthDate(LocalDate.now());
        rosco.setOwner(mike);
        mike.getPets().add(rosco);
        ownerService.save(mike);

        Owner fiona = new Owner();
        fiona.setFirstName("Fiona");
        fiona.setLastName("Glenanne");
        fiona.setAddress("Madinaty");
        fiona.setCity("Cairo");
        fiona.setTelephone("88775554431");


        Pet spock = new Pet();
        spock.setName("Spock");
        spock.setType(savedCatPetType);
        spock.setBirthDate(LocalDate.now());
        spock.setOwner(fiona);
        fiona.getPets().add(spock);
        ownerService.save(fiona);

        System.out.println("Owners Loaded...");

        Speciality radiology = new Speciality();
        radiology.setName("Radiology");
        radiology =specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setName("Surgery");
        surgery =specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setName("Dentistry");
        dentistry =specialityService.save(dentistry);
        //Vets
        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(radiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet1.getSpecialities().add(dentistry);
        vetService.save(vet2);

        System.out.println("Vets Loaded...");

        Visit catVisit = new Visit();
        catVisit.setPet(spock);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");
        visitService.save(catVisit);
        System.out.println("Visit Loaded");

        System.out.println(visitService.findAll());
    }
}
