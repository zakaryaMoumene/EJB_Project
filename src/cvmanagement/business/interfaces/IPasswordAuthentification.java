package cvmanagement.business.interfaces;


public interface IPasswordAuthentification {

    public String hash(String passwordToHash);
    public boolean authenticate(String password, String token);
    
}
