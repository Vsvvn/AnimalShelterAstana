package animal.shelter.animalsshelter.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Cat {
    public enum CatType {
        KITTEN,
        CAT_CAT,
        DISABLED_CAT
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private long id;

    private long chatId;

    private int stateId;
    private String name;
    private String age;
    private String breed;
    @Column(name = "cat_type")
    @Enumerated(EnumType.STRING)
    private CatType catType;
    private String description;
    private byte[] photo;

}
