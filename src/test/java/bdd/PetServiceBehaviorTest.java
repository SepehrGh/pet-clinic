package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.samples.petclinic.owner.*;
//import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetServiceBehaviorTest {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Autowired
	PetTimedCache petTimedCache;

	private Owner sampleOwner;
	private Pet samplePet;
	private PetType sampleType;
	private Owner foundOwner;
	private Pet foundPet;
	private int ownerId;
	private int petId;
	private int ownerPetCount;
	@Before("@petService_annotation")
	public void setup() {
		// sample setup code
	}

	@Given("There is an owner with ID {int}")
	public void thereIsAnOwnerCalledWithID(int id) {
		sampleOwner = new Owner();
		sampleOwner.setFirstName("Amu");
		sampleOwner.setLastName("Abbaspour");
		sampleOwner.setAddress("Najibie - Kooche shahid abbas alavi");
		sampleOwner.setCity("Tehran");
		sampleOwner.setTelephone("09191919223");
		ownerRepository.save(sampleOwner);
		ownerId = sampleOwner.getId();
	}
	@When("Find owner with ID {int} is called")
	public void findOwnerWithIDisCalled(int id) {
		foundOwner = petService.findOwner(ownerId);
	}

	@Then("The owner with ID {int} is returned successfully")
	public void ownerWithIDisReturned(int id) {
		assertEquals(ownerId, foundOwner.getId());
	}

	@When("New pet for ID {int} is called")
	public void makeNewPetForOwner(int id) {
		ownerPetCount = sampleOwner.getPets().size();
		petService.newPet(sampleOwner);
	}

	@Then("A pet is created for owner with ID {int}")
	public void newPetIsCreated(int id) {
		assertEquals(ownerPetCount+1, sampleOwner.getPets().size());
	}

	@Given("There is a pet with ID {int} in cache")
	public void thereIsAPetWithIDinCache(int id) {

		sampleType = new PetType();
		sampleType.setName("feline");
		petTypeRepository.save(sampleType);

		samplePet = new Pet();
		samplePet.setName("Lemon");
		sampleOwner.addPet(samplePet);
		samplePet.setType(sampleType);
		petTimedCache.save(samplePet);
		petId = samplePet.getId();
	}

	@Given("There is a pet with ID {int}")
	public void thereIsAPetWithID(int id) {

		sampleType = new PetType();
		sampleType.setName("feline");
		petTypeRepository.save(sampleType);

		samplePet = new Pet();
		samplePet.setName("Lemon");
		samplePet.setType(sampleType);
	}

	@When("Find pet for ID {int} is called")
	public void findPetWithIDisCalled(int id){
		foundPet = petService.findPet(petId);
	}

	@Then("The pet with ID {int} is returned successfully")
	public void petWithIDisReturned(int id) {
		assertEquals(petId, foundPet.getId());
	}

	@When("Save pet for ID {int} on owner {int} is called")
	public void savePetOnOwnerIsCalled(int petId, int ownerId) {
		ownerPetCount = sampleOwner.getPets().size();
		petService.savePet(samplePet, sampleOwner);
	}

	@Then("The pet with ID {int} is saved on owner {int}")
	public void petIsSavedOnOwner(int petId, int ownerId) {
		int petsSize = sampleOwner.getPets().size();
		assertEquals(ownerPetCount+1, petsSize);
		assertEquals(samplePet.getId(), sampleOwner.getPets().get(petsSize-1).getId());
	}

	@Then("The pet with ID {int} is added to cache")
	public void petIsAddedToCache(int id){
		assertEquals(petTimedCache.get(samplePet.getId()).getName(),samplePet.getName());
	}
}
