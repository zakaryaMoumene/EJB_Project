package cvmanagement.business;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

import org.hibernate.Hibernate;

import cvmanagement.business.interfaces.IPasswordAuthentification;
import cvmanagement.business.interfaces.CvManagementService;
import cvmanagement.entities.Activity;
import cvmanagement.entities.Person;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CvManagerBean implements CvManagementService {

    private static final String SELECT_ALL = "SELECT p FROM Person p";
    private static final String SELECT_MAIL = "SELECT p FROM Person p WHERE p.mail=:mail";
    private static final String PARAM_MAIL = "mail";

    @PersistenceContext(unitName = "cvunit")
    private EntityManager entityManager;

    public CvManagerBean() {
    }

    @Inject
    IPasswordAuthentification passAuth;

    @Override
    public Long addPerson(@Valid Person person) {
        person.setPassword(passAuth.hash(person.getPassword()));
        entityManager.persist(person);
        return person.getId();
    }

    @Override
    public Person findPersonByEmail(String personEmail) {
        Query requete = entityManager.createQuery(SELECT_MAIL);
        requete.setParameter(PARAM_MAIL, personEmail);
        if (requete.getResultList().isEmpty())
            return null;
        Person p = (Person) requete.getSingleResult();
        Hibernate.initialize(p.getActivities());

        return p;
    }

    @Override
    public void updatePerson(@Valid Person person) {
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
    public Long saveActivity(@Valid Activity activity, @Valid Person person) {
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
    public void deleteActivity(@Valid Activity activity, @Valid Person person) {
        person.getActivities().remove(activity);
        if (activity.getId() != null)
            entityManager.merge(person);
    }

    public void cleanActivities(Collection<Activity> collection) {
        for (Activity a : collection)
            if (a.isNull())
                collection.remove(a);
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
        Person p = entityManager.find(Person.class, personId);

        if (p != null)
            Hibernate.initialize(p.getActivities());

        return p;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> search(Object[] params) {
        if (params.length < 5)
            return null;
        String query = "SELECT DISTINCT p FROM Person p LEFT JOIN p.activities a WHERE";
        String[] paramNames = { "firstName", "lastName", "mail", "title", "year" };
        String[] queryParams = { " p.firstName", " p.lastName", " p.mail", " a.title", " a.year" };
        String[] operators = { " LIKE :", " LIKE :", " LIKE :", " LIKE :", " = :" };
        boolean flag = true;

        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && !params[i].equals("")) {
                if (flag) {
                    query += (queryParams[i] + operators[i] + paramNames[i]);
                    flag = false;
                } else {
                    query += (" and " + queryParams[i] + operators[i] + paramNames[i]);
                }
            }
        }

        if (flag)
            return null;

        Query requete = entityManager.createQuery(query);

        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && !params[i].equals(""))
                if (i != 4 || params[i] instanceof Integer)
                    requete.setParameter(paramNames[i], params[i]);
                else
                    requete.setParameter(paramNames[i], Integer.valueOf((String) params[i]));
        }

        return requete.getResultList();
    }

}
