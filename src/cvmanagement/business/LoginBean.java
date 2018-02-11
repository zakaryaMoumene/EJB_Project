package cvmanagement.business;

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

import cvmanagement.entities.Person;

@ManagedBean
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LoginBean implements LoggedUser, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5211546593741282783L;

    /**
     * 
     */


    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    @EJB
    PersonServiceLocal pm;

    PasswordAuthentification passAuth = new PasswordAuthentification();
    
    private Person user;

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
        user = null;
        email = null;
        pwd = null;
        
        return navigationBean.redirectToLogin();
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
        return (user instanceof Person);
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

}