package cvmanagement.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.Before;
import org.junit.Test;

import cvmanagement.business.PersonServiceLocal;
import cvmanagement.entities.Activity;
import cvmanagement.entities.Person;

public class TestDB2 {

    @EJB
    PersonServiceLocal pm;

    public TestDB2() throws Exception {
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

        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar
                .getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(1994, 0, 10);

        Person person = new Person();
        person.setFirstName("Houssem");
        person.setLastName("MJID");
        person.setMail("mjid@mjid.fr");
        person.setWebsite("www.mjid.fr");
        person.setPassword("zakaryaMoumene");
        person.setBirthday(cal.getTime());
        person.setActivities(activities);

        System.out.println(person);
        pm.addPerson(person);

        Person person2 = new Person();
        person2.setFirstName("Houssem");
        person2.setLastName("MJID");
        person2.setMail("mjiddazd@mjid.fr");
        person2.setWebsite("www.mjid.fr");
        person2.setPassword("zakaryaMoumene");

        person2.setBirthday(cal.getTime());
        person2.setActivities(activities2);

        pm.addPerson(person2);
    }

}