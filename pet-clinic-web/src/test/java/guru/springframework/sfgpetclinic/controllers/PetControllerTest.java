package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

/**
 * @author john
 * @since 28/02/2024
 */
@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    private Owner owner;
    @Mock
    private PetService petService;

    @Mock
    private OwnerService ownerService;

    @Mock
    private PetTypeService petTypeService;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).firstName("Putri").build();
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void initCreationForm() throws Exception {
        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdateForm"));
    }
    
    @Test
    void processCreationForm() throws Exception {
        //Given
        Pet savedPet = Pet.builder().id(2L).name("Spock").build();
        owner.addPet(savedPet);

        //When
        Mockito.when(petService.save(ArgumentMatchers.any(Pet.class))).thenReturn(savedPet);
        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("types"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
    }

    @Test
    void initUpdateForm() throws Exception {
        //Given
        Pet pet = Pet.builder().id(2L).name("Fred").build();
        pet.setOwner(owner);

        //When
        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
        Mockito.when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(pet);
        Mockito.when(petTypeService.findAll()).thenReturn(Collections.emptySet());

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/2/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("types"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdateForm"));
    }

    @Test
    void processUpdateForm() throws Exception {
        //Given
        Pet pet = Pet.builder().id(2L).birthDate(LocalDate.now()).name("Fred").build();
        owner.addPet(pet);

        //When
        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
        Mockito.when(petService.save(ArgumentMatchers.any(Pet.class))).thenReturn(pet);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/2/edit")
                        .param("name", "Fred")
                        .param("birthDate", String.valueOf(LocalDate.now()))
                        )
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("pet"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
    }
}