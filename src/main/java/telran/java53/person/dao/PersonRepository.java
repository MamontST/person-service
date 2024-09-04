package telran.java53.person.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java53.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	List<Person> findByAddressCity(String city);

	List<Person> findPersonsByName(String name);
	
	@Query("SELECT p FROM Person p WHERE YEAR(CURRENT_DATE) - YEAR(p.birthDate) BETWEEN :minAge AND :maxAge")
	List<Person> findPersonsBetweenAge(Integer minAge, Integer maxAge);

	@Query("SELECT p.address.city, COUNT(*) FROM Person p GROUP BY p.address.city")
	List<Object[]> getCityPopulation();

	
	
}
