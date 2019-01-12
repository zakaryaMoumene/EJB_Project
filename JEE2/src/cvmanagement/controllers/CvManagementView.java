package cvmanagement.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import cvmanagement.business.NavigationBean;
import cvmanagement.business.SessionUtils;
import cvmanagement.business.interfaces.LoggedUserService;
import cvmanagement.business.interfaces.CvManagementService;
import cvmanagement.entities.Person;

@ManagedBean
@ViewScoped
public class CvManagementView implements Serializable {

    private static final long serialVersionUID = 3313636781819634928L;

    public final static String[] natures;

    static {
        natures = new String[4];
        natures[0] = "Selectionner";
        natures[1] = "experience professionnelle";
        natures[2] = "formation";
        natures[3] = "autre";
    }

    @EJB
    private CvManagementService cvManager;

    @ManagedProperty(value = "#{cvService}")
    private CvService globalCvService;

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    @PostConstruct
    public void init() {
        searchResult = new ArrayList<Person>();
        searchParams = new Object[5];
    }

    private List<Person> searchResult;

    private Object[] searchParams;

    public void search() {
        searchResult = cvManager.search(searchParams);
    }

    public Person findPerson(String personId) {
        return cvManager.findPersonById(Long.valueOf(personId));
    }

    public List<Person> getAll() {
        return globalCvService.getPersons();
    }

    public void refresh() {
        globalCvService.refresh();
    }

    public String updateCv() {
        if (FacesContext.getCurrentInstance().isValidationFailed())
            return navigationBean.redirectToCvEdtion();
        LoggedUserService loggedUser = SessionUtils.getLoggedUser();
        loggedUser.updateCv();
        cvManager.updatePerson(loggedUser.getUser());
        loggedUser.setUser(cvManager.findPersonById(loggedUser.getUser().getId()));
        return navigationBean.redirectToDisplayAll();
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

    public Object[] getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(Object[] searchParams) {
        this.searchParams = searchParams;
    }

    public List<Person> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Person> searchResult) {
        this.searchResult = searchResult;
    }

    public List<String> getNatures() {
        return Arrays.asList(natures);
    }

    public CvService getGlobalCvService() {
        return globalCvService;
    }

    public void setGlobalCvService(CvService globalCvService) {
        this.globalCvService = globalCvService;
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

}
