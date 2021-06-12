package elerick.springbreak.testentities;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true, nullable = false)
    public String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s", id, name);
    }
}
