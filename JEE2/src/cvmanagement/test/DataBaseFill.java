package cvmanagement.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cvmanagement.business.interfaces.CvManagementService;
import cvmanagement.entities.Activity;
import cvmanagement.entities.Person;


public class DataBaseFill {

    @EJB
    CvManagementService pm;
    
    public DataBaseFill() throws NamingException {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);
    }
    
    @Before
    public void setUp() throws Exception {
        pm.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        String line = "";
        String cvsSplitBy = ",";
        Random rand = new Random();
        
        List<Activity> activities = new ArrayList<Activity>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("testData/activity.csv"))) {

            String [] natures = {"experience professionnelle","formation","autre"};
            while ((line = br.readLine()) != null) {

                
                
                // use comma as separator
                String[] activity = line.split(cvsSplitBy);

                activities.add(new Activity(activity[0],activity[1],activity[2],Integer.valueOf(activity[3]),natures[rand.nextInt(3)]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(activities);

        try (BufferedReader br = new BufferedReader(
                new FileReader("testData/person.csv"))) {

            int k=0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] person = line.split(cvsSplitBy);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                
                Person p =new Person(person[1],person[2],person[3],person[4],df.parse(person[5]),person[6]);
                p.setActivities(new ArrayList<Activity>());

                int i=k;
                int j;
                for(j=i; j<i+rand.nextInt(6)+3;j++)
                    p.getActivities().add(activities.get(j));
                
                k=j;
                //System.out.println(p);
                
                pm.addPerson(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        

    }

}
