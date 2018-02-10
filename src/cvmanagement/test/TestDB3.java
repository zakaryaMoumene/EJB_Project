package cvmanagement.test;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.junit.Before;
import org.junit.Test;

import cvmanagement.business.PersonServiceLocal;

public class TestDB3 {

    @EJB
    PersonServiceLocal pm;

    public TestDB3() throws Exception {
    }

    @Before
    public void setUp() {
        
    }
    
    @Test
    public void test(){
        List<Object> params = new ArrayList<Object>();
        params.add(null);
        params.add(null);
        params.add(null);
        params.add(null);
        params.add(null);
        
        params.set(0,"houssem");
        params.set(1,"mjid");
        params.set(4,"2017");
        System.out.println(params);
        
    }

}