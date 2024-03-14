package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
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

/**
 * @author john
 * @since 03/03/2024
 */
@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    private VisitService visitService;
    @Mock
    private PetService petService;
    @InjectMocks
    private VisitController controller;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        //Given
        Pet pet = Pet.builder().id(1L).build();
        //When
        Mockito.when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(pet);
        //Then
        mvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/1/visits/new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("visit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("pets/createOrUpdateVisitForm"));
    }

    @Test
    void processNewVisitForm() throws Exception {
        //Given
        Pet pet = Pet.builder().id(1L).build();
        Visit visit = Visit.builder().id(2L).description("Visit").build();
        pet.addVisit(visit);
        //When
        Mockito.when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(pet);
        Mockito.when(visitService.save(ArgumentMatchers.any(Visit.class))).thenReturn(visit);
        //Then
        mvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/1/visits/new"))
               .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
               .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));

        Mockito.verify(visitService, Mockito.times(1)).save(ArgumentMatchers.any(Visit.class));
    }
}