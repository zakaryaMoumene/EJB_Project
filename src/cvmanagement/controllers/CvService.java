package cvmanagement.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import cvmanagement.business.interfaces.CvManagementService;
import cvmanagement.entities.Person;

@ManagedBean
@ApplicationScoped
public class CvService implements Serializable {

    private static final long serialVersionUID = 5447942886613590146L;

    @EJB
    CvManagementService cvManager;
    
    private List<Person> persons;
    
    @PostConstruct
    public void init(){
        persons = cvManager.findAll();
    }
    
    public void refresh(){
        persons = cvManager.findAll();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    
    
}
