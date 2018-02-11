package cvmanagement.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cvmanagement.business.PersonServiceLocal;
import cvmanagement.entities.Activity;
import cvmanagement.entities.Person;

public class TestDB {

    @EJB
    PersonServiceLocal pm;

    public TestDB() throws Exception {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);
    }

    @Test
    public void testPersonManagerNotNull() {
        assertNotNull(pm);
    }

    @Before
    public void setUp() {
        Activity activity = new Activity();
        activity.setDescriptiveText("fefke");
        activity.setNature("autre");
        activity.setTitle("dzhdh");
        activity.setWebsite("www.zak.fr");
        activity.setYear(2015);
        List<Activity> activities = new ArrayList<Activity>();
        activities.add(activity);

        Activity activity2 = new Activity();
        activity2.setDescriptiveText("new");
        activity2.setNature("autre");
        activity2.setTitle("yeeeee");
        activity2.setWebsite("www.mjid.fr");
        activity2.setYear(2015);
        List<Activity> activities2 = new ArrayList<Activity>();
        activities2.add(activity2);

        Person person = new Person();
        person.setFirstName("Houssem");
        person.setLastName("MJID");
        person.setMail("mjid@mjid.fr");
        person.setWebsite("www.mjid.fr");
        person.setPassword("zakaryaMoumene");
        person.setBirthday(new GregorianCalendar(1994, 1, 10).getTime());
        person.setActivities(activities);

        pm.addPerson(person);

        Person person2 = new Person();
        person2.setFirstName("Houssem");
        person2.setLastName("MJID");
        person2.setMail("mjiddazd@mjid.fr");
        person2.setWebsite("www.mjid.fr");
        person2.setPassword("zakaryaMoumene");
        person2.setBirthday(new GregorianCalendar(1994, 1, 10).getTime());
        person2.setActivities(activities2);

        pm.addPerson(person2);
    }

    @After
    public void tearDown() {
        pm.deleteAll();
    }

    @Test
    public void testAddActivity() {

        Activity activity = new Activity();
        activity.setDescriptiveText("newkokkoko");
        activity.setNature("autre");
        activity.setTitle("yeeeee");
        activity.setWebsite("www.mjid.fr");
        activity.setYear(2015);

        Person p = pm.findPersonByEmail("mjid@mjid.fr");

        System.out.println(p);
        Long id = pm.saveActivity(activity, p);

        assertNotNull(id);

    }

    @Test
    public void testGetPersonByEmail() {
        System.out.println(pm.findPersonByEmail("mjid@mjid.fr"));
    }

    @Test
    public void testEditActivity() {

        Person p = pm.findPersonByEmail("mjid@mjid.fr");

        Activity activity = new Activity();
        for (Activity a : p.getActivities()) {
            activity = a;
            break;
        }
        activity.setDescriptiveText("new222222222");
        activity.setNature("autre");
        activity.setTitle("yeeeee");
        activity.setWebsite("www.mjid.fr");
        activity.setYear(2015);

        pm.saveActivity(activity, p);
    }

    @Test
    public void testRemoveActivity() throws InterruptedException {

        Person p = pm.findPersonByEmail("mjid@mjid.fr");

        Activity activity = null;
        for (Activity a : p.getActivities()) {
            activity = a;
            break;
        }

        pm.deleteActivity(activity, p);
        System.out.println("icii" + p);

    }

}