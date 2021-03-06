package cvmanagement.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "activity")
public class Activity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6835224882692928671L;

    public Activity() {
    }

    public Activity(String title, String descriptiveText, String website, Integer year,
            String nature) {
        super();
        this.title = title;
        this.descriptiveText = descriptiveText;
        this.website = website;
        this.year = year;
        this.nature = nature;
    }

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, message = "Titre obligatoire!")
    @Column(name = "title", length = 50)
    private String title;

    @NotNull
    @Size(min = 1, message = "Description obligatoire!")
    @Column(name = "descriptivetext", length = 300)
    private String descriptiveText;

    @Size(min = 3, max = 100, message = "L'url doit �tre entre 3 et 100 caract�res")
    @Column(name = "website", length = 50)
    private String website;

    @NotNull
    @Max(value = 2018, message = "voyageur dans le temps ?")
    @Min(value = 1900, message = "immortel �tes vous?")
    @Column(name = "year")
    private Integer year;

    @NotNull(message = "Nature obligatoire!")
    @NotEmpty(message = "Nature obligatoire!")
    @Pattern(regexp = "experience professionnelle|formation|autre", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(name = "nature")
    private String nature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptiveText() {
        return descriptiveText;
    }

    public void setDescriptiveText(String descriptiveText) {
        this.descriptiveText = descriptiveText;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    @Override
    public String toString() {
        return "Activity [id=" + id + ", title=" + title + ", descriptiveText=" + descriptiveText
                + ", website=" + website + ", year=" + year + ", nature=" + nature + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descriptiveText == null) ? 0 : descriptiveText.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nature == null) ? 0 : nature.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((website == null) ? 0 : website.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
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
        Activity other = (Activity) obj;
        if (descriptiveText == null) {
            if (other.descriptiveText != null)
                return false;
        } else if (!descriptiveText.equals(other.descriptiveText))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nature == null) {
            if (other.nature != null)
                return false;
        } else if (!nature.equals(other.nature))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (website == null) {
            if (other.website != null)
                return false;
        } else if (!website.equals(other.website))
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        return true;
    }

    public boolean isNull() {
        return (id == null && (title == null || title.isEmpty())
                && (descriptiveText == null || descriptiveText.isEmpty()) && year == null
                && (website == null || website.isEmpty()) && (nature == null || nature.isEmpty()));
    }

}
