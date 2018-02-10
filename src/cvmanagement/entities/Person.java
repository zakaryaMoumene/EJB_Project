package cvmanagement.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDate;
import org.joda.time.Years;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5361089819858784513L;

    public Person() {
    }
    

    public Person(String firstName, String lastName, String mail, String website, Date birthday,
            String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.website = website;
        this.birthday = birthday;
        this.password = password;
    }

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, message = "First Name is mandatory!")
    @Column(name = "firstname", length = 50)
    @Pattern(regexp = "[A-Za-z]+", message = "Nom:Caracteres invalides")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "Last Name is mandatory!")
    @Column(name = "lastname", length = 50)
    @Pattern(regexp = "[A-Za-z]+", message = "Prénom:Caracteres invalides")
    private String lastName;

    @NotNull(message = "Email field cannot be null!")
    @Length(max = 100, message = "Not greater than {max} caracteres for Email field!")
    @Column(name = "mail", length = 100, unique = true)
    @Email
    private String mail;

    @Size(min = 3, max = 100, message = "Website must be between [5-100] characters")
    @Column(name = "website", length = 50)
    private String website;

    @NotNull
    @Past(message = "Trop récent !")
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @NotNull(message = "Password may not be null")
    @Size(min = 6, max = 50, message = "Mot de passe de taille entre 6 et 50")
    @Column(name = "password", length = 50)
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "personId")
    private Collection<Activity> activities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Collection<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", mail=" + mail + ", website=" + website + ", birthday=" + birthday
                + ", password=" + password + ", activities=" + activities + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((activities == null) ? 0 : activities.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((mail == null) ? 0 : mail.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((website == null) ? 0 : website.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (activities == null) {
            if (other.activities != null)
                return false;
        } else if (!activities.equals(other.activities))
            return false;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (mail == null) {
            if (other.mail != null)
                return false;
        } else if (!mail.equals(other.mail))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (website == null) {
            if (other.website != null)
                return false;
        } else if (!website.equals(other.website))
            return false;
        return true;
    }
    
    public int getAge(){
        LocalDate birthdate = new LocalDate (birthday);
        LocalDate now = new LocalDate();
        return Years.yearsBetween(birthdate, now).getYears();
    }

}
