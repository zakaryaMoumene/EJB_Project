package cvmanagement.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.CellEditEvent;

import cvmanagement.business.LoggedUser;
import cvmanagement.business.NavigationBean;
import cvmanagement.business.SessionUtils;
import cvmanagement.business.interfaces.CvManagementService;
import cvmanagement.business.interfaces.IPasswordAuthentification;
import cvmanagement.entities.Person;

@ManagedBean
@ViewScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserLoginView implements Serializable {

    private static final long serialVersionUID = 6854934595541609591L;

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    @EJB
    private CvManagementService pm;
    

    @Inject
    private IPasswordAuthentification passAuth;

    private Person user;
    
    private String email;
    private String pwd;

    public UserLoginView() {
    }

    public String doLogin() {
        user = pm.findPersonByEmail(email);
        if (user != null && passAuth.authenticate(pwd, user.getPassword())) {
            LoggedUser loggedUser = new LoggedUser();
            loggedUser.setUser(user);

            SessionUtils.getSession().setAttribute("loggedUser", loggedUser);
            return navigationBean.redirectToDisplayAll();
        }

        FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return navigationBean.redirectToLogin();
    }

    @Remove
    public String doLogout() {
        user = null;
        email = null;
        pwd = null;

        SessionUtils.getSession().removeAttribute("loggedUser");
        return navigationBean.redirectToLogin();
    }

    public String createNewUser() {
        if (!isLoggedIn()) {
            user = new Person();
            System.out.println("new user "+user );
            return "/signIn.xhtml?faces-redirect=true";
        }
        return navigationBean.redirectToDisplayAll();
    }

    public String doSignIn() {
        email = user.getMail();
        pwd = user.getPassword();
        pm.addPerson(user);
        return doLogin();
    }
    
    public boolean isLoggedIn() {
        return (SessionUtils.getLoggedUser() instanceof LoggedUser);
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

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }
    
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public CvManagementService getPm() {
        return pm;
    }

    public void setPm(CvManagementService pm) {
        this.pm = pm;
    }

    public IPasswordAuthentification getPassAuth() {
        return passAuth;
    }

    public void setPassAuth(IPasswordAuthentification passAuth) {
        this.passAuth = passAuth;
    }
    
}