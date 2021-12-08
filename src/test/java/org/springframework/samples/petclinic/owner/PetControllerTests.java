package org.springframework.samples.petclinic.owner;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PetRepository petRepository;

	@MockBean
	private OwnerRepository ownerRepository;

	private Owner owner;
	private Pet dog;
	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	private PetType canines;

	@BeforeEach
	public void setup(){
		owner = new Owner();
		owner.setId(10);

		dog = new Pet();
		dog.setName("Chihuahua");
		dog.setId(11);

		canines = new PetType();
		canines.setId(12);
		canines.setName("Canines");
	}

	@Test
	void testInitCreationForm() throws Exception {
		given(this.ownerRepository.findById(10)).willReturn(owner);
		mvc.perform(get("/owners/{ownerId}/pets/new",10))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	void testProcessCreationFormHasError() throws Exception {
		given(this.ownerRepository.findById(10)).willReturn(owner);
		given(this.petRepository.findById(11)).willReturn(dog);

		mvc.perform(post("/owners/{ownerId}/pets/new",10))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	void testProcessCreationFormSuccessful() throws Exception {
		given(this.petRepository.findPetTypes()).willReturn(Lists.newArrayList(canines));
		given(this.ownerRepository.findById(10)).willReturn(owner);
		given(this.petRepository.findById(11)).willReturn(dog);

		mvc.perform(post("/owners/{ownerId}/pets/new", 10)
			.param("name", "Chihuahua")
			.param("type", "Canines")
			.param("birthDate", "2021-12-01"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	void testInitUpdateForm() throws Exception {
		given(this.ownerRepository.findById(10)).willReturn(owner);
		given(this.petRepository.findById(11)).willReturn(dog);
		mvc.perform(get("/owners/{ownerId}/pets/{petId}/edit",10, 11))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	void testProcessUpdateFormHasError() throws Exception {
		given(this.ownerRepository.findById(10)).willReturn(owner);
		given(this.petRepository.findById(11)).willReturn(dog);
		mvc.perform(post("/owners/{ownerId}/pets/{petId}/edit",10, 11))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	void testProcessUpdateFormSuccessful() throws Exception {
		given(this.petRepository.findPetTypes()).willReturn(Lists.newArrayList(canines));
		given(this.ownerRepository.findById(10)).willReturn(owner);
		given(this.petRepository.findById(11)).willReturn(dog);

		mvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", 10, 11)
			.param("name", "Chihuahua")
			.param("type", "Canines")
			.param("birthDate", "2021-12-01")
			.param("owner", "Ali"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
}
