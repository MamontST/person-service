package telran.java53.person.dao;

import java.time.LocalDate;
import java.util.List;
	import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java53.person.dto.CityPopulationDto;
import telran.java53.person.model.Child;
import telran.java53.person.model.Employee;
import telran.java53.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	Stream<Person> findByAddressCityIgnoreCase(String city);

	Stream<Person> findPersonsByNameIgnoreCase(String name);

	Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);

	@Query("SELECT new telran.java53.person.dto.CityPopulationDto(p.address.city, COUNT(*) as population) FROM Citizen p GROUP BY p.address.city ORDER BY population DESC")
	List<CityPopulationDto> getCityPopulation();

	List<Employee> findBySalaryBetween(int minSalary, int maxSalary);
	
	@Query("SELECT c FROM Child c")
	List<Child> findAllChildren();
	List<Child> findChildrenBy();

}
