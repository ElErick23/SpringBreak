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

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConnectorTest {

    private Connector<Product> prod_conn;
    private Connector<Category> cat_conn;
    private Connector<User> user_conn;
    private final static Logger LOGGER = Logger.getLogger(ConnectorTest.class.getName());

    @Autowired
    public ConnectorTest(EntityManager em) {
        this.prod_conn = new Connector<>(Product.class, em);
        this.cat_conn = new Connector<>(Category.class, em);
        this.user_conn = new Connector<>(User.class, em);
    }


    @Test
    public void uniqueFields() {
        String[] names = new String[]{"A", "B", "A"};
        for (String name : names) {
            Category c = new Category(name);
            cat_conn.add(c);
            LOGGER.info(c.toString());
        }
        int count = cat_conn.findAll().size();
        LOGGER.info(String.format("Registered: %d", count));
        assertEquals(2, count);
    }

    @Test
    public void foreignKeys() {
        Category c = new Category("Cat");
        c.id = (long) 5;
//        cat_conn.add(c);
        Product p = new Product("Stuff", c, 10);
        prod_conn.add(p);
        LOGGER.info(p.toString());
        int count = prod_conn.findAll().size();
        LOGGER.info(String.format("Registered: %d", count));
        assertEquals(0, count);
    }

    @Test
    public void notNullableFields() {
        String[] names = new String[]{"A", null, "C"};
        for (String name : names) {
            Category c = new Category(name);
            cat_conn.add(c);
            LOGGER.info(c.toString());
        }
        int count = cat_conn.findAll().size();
        LOGGER.info(String.format("Registered: %d", count));
        assertEquals(2, count);


    }


}
