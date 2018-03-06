package cvmanagement.business;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

    private static final long serialVersionUID = 1520318172495977648L;

    public String redirectToLogin() {
        return "/login.xhtml?faces-redirect=true";
    }

    public String toLogin() {
        return "/login.xhtml";
    }

    public String toDisplayAll() {
        return "/displayAll.xhtml";
    }

    public String toEdition() {
        return "/secured/cvEdition.xhtml";
    }

    public String redirectToDisplayAll() {
        return "/displayAll.xhtml?faces-redirect=true";
    }

    public String redirectToCvEdtion() {
        return "/cvEdition.xhtml?faces-redirect=true";
    }

}