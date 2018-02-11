package cvmanagement.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import cvmanagement.entities.Activity;
import cvmanagement.entities.Person;

@ManagedBean
@SessionScoped
public class CvController {

    public final static String[] natures;

    static {
        natures = new String[3];
        natures[0] = "Experience professionnelle";
        natures[1] = "Formation";
        natures[2] = "Autre";
    }

    @EJB
    PersonServiceLocal cvManager;

    private List<Person> persons = new ArrayList<Person>();
    private List<Activity> activities_t = new ArrayList<Activity>();

    private List<Person> searchResult = new ArrayList<Person>();

    private Object[] searchParams = new Object[5];

    private Person person;

    public String displayCv(Person p) {
        person = p;
        return "/cvDisplay.xhtml?faces-redirect=true";
    }
    
    public boolean validate(){
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public List<String> getNatures() {
        return Arrays.asList(natures);
    }

    public void search() {
        searchResult = cvManager.search(searchParams);
    }

    public String initEdition(Person p) {
        person = cvManager.findPersonByEmail(p.getMail());
        activities_t.clear();
        activities_t.addAll(person.getActivities());
        return "/secured/cvEdition.xhtml?faces-redirect=true";
    }

    public void refresh() {
        person = cvManager.findPersonByEmail(person.getMail());
        LoginBean lb = (LoginBean) SessionUtils.getSession().getAttribute("loginBean");
        lb.setUser(person);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Old: " + oldValue,
                    " New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }

    public void addActivity() {
        activities_t.add(new Activity());
    }

    public void deleteActivity(Activity activity) {

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity deleted",
                "Activity deleted");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        activities_t.remove(activity);
    }

    public String updateCv() {
        person.setActivities(activities_t);
        cvManager.updatePerson(person);
        cvManager.cleanActivities();
        refresh();
        return "/displayAll.xhtml?faces-redirect=true";
    }

    public Person findPerson(String personId) {
        return cvManager.findPersonById(Long.valueOf(personId));
    }

    public List<Person> getAll() {
        if (persons.isEmpty()){
            persons =  cvManager.findAll();
        }
        return persons;
    }

    public Object[] getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(Object[] searchParams) {
        this.searchParams = searchParams;
    }

    public List<Activity> getActivities_t() {
        return activities_t;
    }

    public void setActivities_t(List<Activity> activities_t) {
        this.activities_t = activities_t;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Person> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Person> searchResult) {
        this.searchResult = searchResult;
    }

}
