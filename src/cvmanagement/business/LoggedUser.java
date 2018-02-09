package cvmanagement.business;

import javax.ejb.Remote;

@Remote
public interface LoggedUser {
   String doLogin();

   String doLogout();
}