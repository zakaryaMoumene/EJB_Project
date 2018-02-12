package cvmanagement.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import cvmanagement.business.NavigationBean;
import cvmanagement.business.PasswordAuthentification;
import cvmanagement.business.PersonServiceLocal;
import cvmanagement.entities.Person;

@ManagedBean
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LoginBean implements Login, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6854934595541609591L;

    /**
     * 
     */

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    @EJB
    PersonServiceLocal pm;

    PasswordAuthentification passAuth = new PasswordAuthentification();

    private Person user = null;

    private Person newUser = null;

    private String email;
    private String pwd;

    public LoginBean() {
    }

    @Override
    public String doLogin() {
        Person user_t = pm.findPersonByEmail(email);
        if (user_t != null && passAuth.authenticate(pwd, user_t.getPassword())) {
            user = user_t;
            return navigationBean.redirectToDisplayAll();
        }

        FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return navigationBean.redirectToLogin();
    }

    @Remove
    public String doLogout() {
        System.out.println("aaaaaaaaadqsdqaaaaaaaaaaa");
        user = null;
        email = null;
        pwd = null;

        return navigationBean.redirectToLogin();
    }

    @Override
    public String createNewUser() {
        newUser = new Person();
        return "/signIn.xhtml?faces-redirect=true";
    }

    @Override
    public String doSignIn() {
        user = newUser;
        newUser = null;
        pm.addPerson(newUser);
        return navigationBean.redirectToDisplayAll();
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

    public boolean isLoggedIn() {
        System.out.println(user instanceof Person && user.getId() != null);
        return (user instanceof Person && user.getId() != null);
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public Person getNewUser() {
        return newUser;
    }

    public void setNewUser(Person newUser) {
        this.newUser = newUser;
    }

    @Override
    public String toString() {
        return "LoginBean [user=" + user + ", newUser=" + newUser + ", email=" + email + ", pwd="
                + pwd + "]";
    }

    
}