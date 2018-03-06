package cvmanagement.business.interfaces;

import javax.ejb.Local;

import cvmanagement.entities.Activity;
import cvmanagement.entities.Person;

@Local
public interface LoggedUserService {
    
    String initEdition();

    void addActivity();

    void updateCv();

    void deleteActivity(Activity activity);

    Person getUser();
    
    void setUser(Person user);
    
   String print();

}