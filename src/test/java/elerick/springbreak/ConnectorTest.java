package elerick.springbreak;

import elerick.springbreak.testentities.Category;
import elerick.springbreak.testentities.Product;
import elerick.springbreak.testentities.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConnectorTest {

    private Connector<Product> prod_conn;
    private Connector<Category> cat_conn;
    private Connector<User> user_conn;

    @Autowired
    public ConnectorTest(EntityManager em) {
        this.prod_conn = new Connector<>(Product.class, em);
        this.cat_conn = new Connector<>(Category.class, em);
        this.user_conn = new Connector<>(User.class, em);
    }

    @Test
    public void uniqueFields() {
        Product p1 = new Product();
        p1.name = "Mouse";
        p1.stock = 5;
        prod_conn.add(p1);


        Product p2 = new Product();
        p2.name = "Mouse";
        p2.stock = 9;
        prod_conn.add(p2);
        System.out.println(prod_conn.findAll());
//        System.out.println(prod_conn.findById((long) 2));
    }


    @Test
    public void AddTest() {
        Product prod = new Product();
        prod.name = "Mouse";
        prod.stock = 5;

        int prod_size = prod_conn.findAll().size();
        boolean success = prod_conn.add(prod);
        if (success) {
            System.out.println(prod_conn.findById((long) 1));
        }

        int result = success ? prod_size + 1 : prod_size;
        assertEquals(result, prod_conn.findAll().size());
    }
}
