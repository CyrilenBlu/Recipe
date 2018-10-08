package blue.springframework.recipe.domain;

import javax.persistence.*;

@Entity
public class UnitOfMeasure
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "unitOfMeas")
    private String description;

    public String getUom() {
        return description;
    }

    public void setUom(String uom) {
        this.description = uom;
    }
}
