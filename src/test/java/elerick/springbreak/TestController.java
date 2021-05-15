package elerick.springbreak;

import elerick.springbreak.testentities.Product;
import elerick.springbreak.testentities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@CrossOrigin
@RestController
@Transactional
@RequestMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    private Connector<Product> prod_conn;
    private Connector<User> user_conn;

    @Autowired
    public TestController(EntityManager em) {
        this.prod_conn = new Connector<>(Product.class, em);
        this.user_conn = new Connector<>(User.class, em);
    }

    @GetMapping(path = "/get")
    public String doSomething() {
        Product prod = new Product();
        prod.name = "Mouse";
        prod.stock = 5;
        boolean prod_r = prod_conn.add(prod);

        User user = new User();
        user.name = "Erick";
        user.email = "erickcrzc@gmail.com";
        user.password = "password";
        boolean user_r = user_conn.add(user);

        return String.format("Prod: %s User: %s", prod_r, user_r);
    }
}
