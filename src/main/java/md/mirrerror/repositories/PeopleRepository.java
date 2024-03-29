package md.mirrerror.repositories;

import md.mirrerror.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Person getPersonByFullName(String fullName);
}
