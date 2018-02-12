package cvmanagement.controllers;

import javax.ejb.Remote;

@Remote
public interface Login {
   String doLogin();

   String doLogout();
   
   String createNewUser();
   
   String doSignIn();
}