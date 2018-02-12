package cvmanagement.test;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.Before;
import org.junit.Test;

import cvmanagement.business.PersonServiceLocal;

public class TestDB3 {

    @EJB
    PersonServiceLocal pm;

    public TestDB3() throws Exception {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);
    }

    @Before
    public void setUp() {
        
    }
    
    @Test
    public void test(){
        Object[] params = new Object[5];
        
        params[4] = 2017;
        
        pm.search(params);
    }

}