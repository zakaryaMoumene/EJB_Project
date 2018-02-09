package cvmanagement.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cvmanagement.entities.Activity;
import cvmanagement.entities.Person;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CvManager implements PersonServiceLocal {

    private static final String SELECT_ALL = "SELECT p FROM Person p";
    private static final String SELECT_MAIL = "SELECT p FROM Person p WHERE p.mail=:mail";
    private static final String PARAM_MAIL = "mail";
    private static final String SELECT_FIRSTNAME = "SELECT p FROM Person p WHERE p.firstname=:firstname";
    private static final String PARAM_FIRSTNAME = "firstname";
    private static final String SELECT_LASTNAME = "SELECT p FROM Person p WHERE p.lastname=:lastname";
    private static final String PARAM_LASTNAME = "lastname";

    @PersistenceContext(unitName = "cvunit")
    private EntityManager entityManager;

    @Override
    public Long addPerson(Person person) {
        entityManager.persist(person);
        return person.getId();
    }

    @SuppressWarnings("unchecked")
    @Override
    // if firstname is not unique so we must change return type to List
    public List<Person> findPersonByFirstName(String firstName) {
        Query requete = entityManager.createQuery(SELECT_FIRSTNAME);
        requete.setParameter(PARAM_FIRSTNAME, firstName);
        return (List<Person>) requete.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    // if lastname is not unique so we must change return type to List
    public List<Person> findPersonByLastName(String lastName) {
        Query requete = entityManager.createQuery(SELECT_LASTNAME);
        requete.setParameter(PARAM_LASTNAME, lastName);
        return (List<Person>) requete.getResultList();
    }

    @Override
    public Person findPersonByEmail(String personEmail) {
        Query requete = entityManager.createQuery(SELECT_MAIL);
        requete.setParameter(PARAM_MAIL, personEmail);
        if (requete.getResultList().isEmpty())
            return null;
        return (Person) requete.getSingleResult();
    }

    @Override
    public void updatePerson(Person person) {
        entityManager.merge(person);
    }

    @Override
    public void deletePersonById(Long personId) {
        Person person = entityManager.find(Person.class, personId);
        entityManager.remove(person);
    }

    @Override
    public void deleteAll() {
        entityManager.createNativeQuery("delete from activity").executeUpdate();
        entityManager.createNativeQuery("delete from person").executeUpdate();
    }

    @Override
    public Long saveActivity(Activity activity, Person person) {
        for (Activity a : person.getActivities())
            if (a.getId() == activity.getId()) {
                person.getActivities().remove(a);
                person.getActivities().add(activity);
                entityManager.merge(person);
                return person.getId();
            }
        person.getActivities().add(activity);
        entityManager.merge(person);
        return person.getId();
    }

    @Override
    public void deleteActivity(Activity activity, Person person) {
        person.getActivities().remove(activity);
        if (activity.getId() != null)
            entityManager.merge(person);
    }

    public void cleanActivities() {
        entityManager.createNativeQuery("delete from activity where personId is null")
                .executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> findAll() {
        Query requete = entityManager.createQuery(SELECT_ALL);
        if (requete.getResultList().isEmpty())
            return null;
        return (List<Person>) requete.getResultList();
    }

    @Override
    public Person findPersonById(Long personId) {
       return entityManager.find(Person.class, personId);
    }

}

// package cvmanagement.business;
//
// import javax.ejb.Stateless;
// import javax.ejb.TransactionManagement;
// import javax.ejb.TransactionManagementType;
// import javax.persistence.EntityManager;
// import javax.persistence.PersistenceContext;
// import javax.persistence.Query;
//
// import cvmanagement.entities.Person;
//
// @Stateless
// @TransactionManagement(TransactionManagementType.CONTAINER)
// public class PersonManager implements PersonServiceLocal {
//
// @PersistenceContext(unitName = "cvunit")
// private EntityManager entityManager;
//
// @Override
// public Long addPerson(Person person) {
// entityManager.persist(person);
// return person.getId();
// }
//
// @Override
// public Person findPersonById(Long personId) {
// return entityManager.find(Person.class, personId);
// }
//
// @Override
// public Person findPersonByFirstName(String firstName) {
// return null;
//
// }
//
// @Override
// public Person findPersonByLastName(String lastName) {
// // TODO Auto-generated method stub
// return null;
// }
//
// @Override
// public Person findPersonByEmail(String email) {
// Query query = entityManager.createQuery("SELECT p FROM Person p WHERE p.mail
// LIKE ?1");
// query.setParameter(1, email);
// if(query.getResultList().isEmpty())
// return null;
// return (Person) query.getSingleResult();
// }
//
// @Override
// public int editPerson(Person person) {
// // TODO Auto-generated method stub
// return 0;
// }
//
// @Override
// public int deletePersonById(Long personId) {
// // TODO Auto-generated method stub
// return 0;
// }
//
// @Override
// public void deleteAll() {
// entityManager.createNativeQuery("delete from activity").executeUpdate();
// entityManager.createNativeQuery("delete from person").executeUpdate();
// }
//
// }
