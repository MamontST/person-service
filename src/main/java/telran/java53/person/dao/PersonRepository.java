package telran.java53.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java53.person.dto.CityPopulationDto;
import telran.java53.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	Stream<Person> findByAddressCityIgnoreCase(String city);

	Stream<Person> findPersonsByNameIgnoreCase(String name);
	
//	@Query("SELECT p FROM Person p WHERE YEAR(CURRENT_DATE) - YEAR(p.birthDate) BETWEEN :minAge AND :maxAge")
//	List<Person> findPersonsBetweenAge(Integer minAge, Integer maxAge);
	
	Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);

//	@Query("SELECT p.address.city, COUNT(*) as population FROM Citizen p GROUP BY p.address.city ORDER BY population DESC")
////	@Query(value = "SELECT city, COUNT(*) as population FROM java53.persons  GROUP BY city ORDER BY population DESC", nativeQuery = true)
//	List<Object[]> getCityPopulation();
//	
	@Query("SELECT new telran.java53.person.dto.CityPopulationDto(p.address.city, COUNT(*) as population) FROM Citizen p GROUP BY p.address.city ORDER BY population DESC")
	List<CityPopulationDto> getCityPopulation();


	
	
}
