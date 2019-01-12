package cvmanagement.business;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import cvmanagement.business.interfaces.LoggedUserService;
import cvmanagement.entities.Activity;
import cvmanagement.entities.Person;

@Stateful
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 20)
public class LoggedUser implements LoggedUserService {

    private Person user;

    private List<Activity> activities_t;

    @Override
    public String initEdition() {
        activities_t = new ArrayList<Activity>();
        activities_t.addAll(user.getActivities());
        return "/secured/cvEdition.xhtml?faces-redirect=true";
    }

    @Override
    public void addActivity() {

        activities_t.add(new Activity());

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity added",
                "Activity added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @Override
    public void deleteActivity(Activity activity) {

        activities_t.remove(activity);

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity deleted",
                "Activity deleted");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @Override
    public void updateCv() {
        user.getActivities().clear();
        for (Activity a : activities_t)
            if (!a.isNull())
                user.getActivities().add(a);
        System.out.println(user);
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

    @Override
    public String print() {
        return "LoggedUser [user=" + user + "]";
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public List<Activity> getActivities_t() {
        return activities_t;
    }

    public void setActivities_t(List<Activity> activities_t) {
        this.activities_t = activities_t;
    }

}
