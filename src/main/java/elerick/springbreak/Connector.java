package elerick.springbreak;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.criteria.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Connector<T> extends SimpleJpaRepository<T, Long> {

    private EntityManager em;
    private CriteriaBuilder cb;
    private Class<T> domainClass;

    public Connector(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.cb = em.getCriteriaBuilder();
        this.domainClass = domainClass;
    }

    /**
     * If given entity is valid, saves it in database
     *
     * @param entity the entity to be added
     * @return true if it was successfully inserted in the database, otherwise false
     */
    public boolean add(T entity) {
        if (isUnique(entity) && checkForeignKeys(entity)) {
            super.save(entity);
            return true;
        }
        return false;
    }


    /**
     * Validates the unique indexes of the entity
     *
     * @param entity to be validated
     * @return simón si todos campos unicos lo son en realidad
     */
    private boolean isUnique(T entity) {
        CriteriaQuery<T> query = cb.createQuery(domainClass);
        Root<T> itemRoot = query.from(domainClass);
        Predicate p = cb.conjunction();
        for (Field f : domainClass.getDeclaredFields())
            if (f.isAnnotationPresent(Column.class) && f.getAnnotation(Column.class).unique())
                try {
                    Path<T> column = itemRoot.get(f.getName());
                    p = cb.or(cb.equal(column, f.get(entity)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        query.where(p);
        return em.createQuery(query).getResultList().isEmpty();
    }

    private boolean checkForeignKeys(T entity) {
        for (Field f : domainClass.getDeclaredFields())
            if (f.isAnnotationPresent(ManyToOne.class)) {
                try {
                    Connector<?> generic = new Connector<>(f.getType(), em);
                    Long id = (Long) f.getType().getDeclaredField("id").get(f.get(entity));
                    if (id == null || !generic.existsById(id)) {
                        System.out.println("No existe D:");
                        return false;
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        return true;
    }

    private CriteriaQuery<T> getQuery() {
        CriteriaQuery<T> query = cb.createQuery(domainClass);
        Root<T> itemRoot = query.from(domainClass);
        return query;
    }
}
