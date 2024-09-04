package telran.java53.person.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import telran.java53.person.dao.PersonRepository;
import telran.java53.person.dto.AddressDto;
import telran.java53.person.dto.CityPopulationDto;
import telran.java53.person.dto.PersonDto;
import telran.java53.person.dto.exception.PersonNotFoundException;
import telran.java53.person.model.Address;
import telran.java53.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		return modelMapper.map(person, PersonDto.class);
	}

	@Transactional
	@Override
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		Address addressPerson = modelMapper.map(addressDto, Address.class);
		person.setAddress(addressPerson);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto[] findPersonsByCity(String city) {
		List<Person> persons = personRepository.findByAddressCity(city);
		return persons.stream()
				.map(person -> modelMapper.map(person, PersonDto.class))
				.toArray(PersonDto[]::new);
	}

	@Override
	public PersonDto[] findPersonsByName(String name) {
		List<Person> persons = personRepository.findPersonsByName(name);
		return persons.stream()
				.map(person -> modelMapper.map(person, PersonDto.class))
				.toArray(PersonDto[]::new);
	}

	@Override
	public PersonDto[] findPersonsBetweenAge(Integer minAge, Integer maxAge) {
		List<Person> persons = personRepository.findPersonsBetweenAge(minAge, maxAge);
		return persons.stream()
				.map(person -> modelMapper.map(person, PersonDto.class))
				.toArray(PersonDto[]::new);
	}

	@Override
	public List<CityPopulationDto> getCitiesPopulatin() {
		List<Object[]> results = personRepository.getCityPopulation();
		return results.stream()
				.map(result -> new CityPopulationDto((String) result[0], (Long) result[1]))
				.toList();
	}

}
