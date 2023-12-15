package guru.springframework.sfgpetclinic.model;

import java.io.Serializable;

/**
 * @author john
 * @since 15/12/2023
 */
public class BaseEntity implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
