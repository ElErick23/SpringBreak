package elerick.springbreak.testentities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true)
    public String name;
//    @ManyToOne(optional = false)
//    public Category category;
    public int stock;

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Stock: %d", id, name, stock);
    }
}
