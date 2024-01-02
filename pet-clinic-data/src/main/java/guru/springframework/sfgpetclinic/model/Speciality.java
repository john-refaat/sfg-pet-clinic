package guru.springframework.sfgpetclinic.model;

/**
 * @author john
 * @since 30/12/2023
 */
public class Speciality extends BaseEntity  {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
