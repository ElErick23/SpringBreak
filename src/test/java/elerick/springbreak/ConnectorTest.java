package elerick.springbreak;

import elerick.springbreak.testentities.Product;
import elerick.springbreak.testentities.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ConnectorTest {

    private Connector<Product> prod_conn;
    private Connector<User> user_conn;

    @Autowired
    public ConnectorTest(EntityManager em) {
        this.prod_conn = new Connector<>(Product.class, em);
        this.user_conn = new Connector<>(User.class, em);
    }

    @Test
    public void AddTest() {
        Product prod = new Product();
        prod.name = "Mouse";
        prod.stock = 5;
        int prod_size = prod_conn.findAll().size();
        boolean prod_r = prod_conn.add(prod);
        int result = prod_r ? prod_size + 1 : prod_size;
        assertEquals(result, prod_conn.findAll().size());

//        User user = new User();
//        user.name = "Erick";
//        user.email = "erickcrzc@gmail.com";
//        user.password = "password";
//        boolean user_r = user_conn.add(user);
    }
}
