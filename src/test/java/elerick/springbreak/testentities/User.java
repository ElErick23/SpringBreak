package elerick.springbreak.testentities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String name;
    @Column(unique = true)
    public String email;
    public String password;

    @Override
    public String toString() {
        return name + " | " + email;
    }
}
