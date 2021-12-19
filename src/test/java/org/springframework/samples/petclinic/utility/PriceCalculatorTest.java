package org.springframework.samples.petclinic.utility;

import org.hibernate.usertype.UserType;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.visit.Visit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceCalculatorTest {

	public Pet dog;
	public Pet cat;
	public Pet cheetah;
	public Pet persianCat;
	public Pet pitbull;
	public Pet germanShepard;
	public Pet chihuahua;
	public PetType canines;
	public PetType felines;
	public ArrayList<Pet> pets;
	public PriceCalculator priceCalculator;
	public Visit visit;

	@BeforeEach
	public void setUp() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		dog = mock(Pet.class);
		canines = mock(PetType.class);
		when(dog.getType()).thenReturn(canines);
		LocalDate dogDate = LocalDate.parse("2015-11-04");
		when(dog.getBirthDate()).thenReturn(dogDate);

		cat = mock(Pet.class);
		felines = mock(PetType.class);
		when(cat.getType()).thenReturn(felines);
		LocalDate catDate = LocalDate.parse("2020-06-07");
		when(cat.getBirthDate()).thenReturn(catDate);

		cheetah = mock(Pet.class);
		when(cheetah.getType()).thenReturn(canines);
		LocalDate cheetahDate = LocalDate.parse("2020-08-14");
		when(cheetah.getBirthDate()).thenReturn(cheetahDate);

		persianCat = mock(Pet.class);
		when(persianCat.getType()).thenReturn(felines);
		LocalDate persianCatDate = LocalDate.parse("2017-02-03");
		when(persianCat.getBirthDate()).thenReturn(persianCatDate);

		pitbull = mock(Pet.class);
		when(pitbull.getType()).thenReturn(canines);
		LocalDate pitbullDate = LocalDate.parse("2021-08-14");
		when(pitbull.getBirthDate()).thenReturn(pitbullDate);

		germanShepard = mock(Pet.class);
		when(germanShepard.getType()).thenReturn(canines);
		LocalDate germanDate = LocalDate.parse("2019-12-14");
		when(germanShepard.getBirthDate()).thenReturn(germanDate);

		chihuahua = mock(Pet.class);
		when(chihuahua.getType()).thenReturn(canines);
		LocalDate chihuahuaDate = LocalDate.parse("2020-10-14");
		when(chihuahua.getBirthDate()).thenReturn(chihuahuaDate);

		pets = new ArrayList<>();
		priceCalculator = new PriceCalculator();

		visit = new Visit();
		visit.setDate(LocalDate.parse("2017-11-04"));
		List <Visit> visits = new ArrayList <Visit> ();
		visits.add(visit);
		when(dog.getVisitsUntilAge(6)).thenReturn(visits);
	}

	@Test
	public void VisitAndAgeAndDiscountPathsTest(){
		pets.add(cat);
		pets.add(cheetah);
		pets.add(persianCat);
		pets.add(pitbull);
		pets.add(germanShepard);
		pets.add(chihuahua);
		pets.add(dog);
		assertEquals(156760,priceCalculator.calcPrice(pets,500,500));
	}

	@Test
	public void ageAndDiscountPathsWithoutVisitTest(){
		pets.add(cat);
		pets.add(cheetah);
		pets.add(persianCat);
		pets.add(pitbull);
		pets.add(germanShepard);
		pets.add(chihuahua);
		assertEquals(9260,priceCalculator.calcPrice(pets,500,500));
	}

	@Test
	public void visitAndAgePathsWithoutDiscountTest(){
		pets.add(cat);
		pets.add(cheetah);
		pets.add(persianCat);
		pets.add(dog);
		assertEquals(2880,priceCalculator.calcPrice(pets,500,500));
	}

	@Test
	public void visitPathNotInfantWithoutDiscountTest(){
		pets.add(persianCat);
		pets.add(dog);
		pets.add(persianCat);
		pets.add(dog);
		pets.add(persianCat);
		pets.add(dog);
		assertEquals(3600,priceCalculator.calcPrice(pets,500,500));
	}



	@After
	public void tearDown(){
		dog = null;
		cat = null;
		cheetah = null;
		persianCat = null;
		pitbull = null;
		germanShepard = null;
		chihuahua = null;
		canines = null;
		felines = null;
		pets = null;
		priceCalculator = null;

	}

}
