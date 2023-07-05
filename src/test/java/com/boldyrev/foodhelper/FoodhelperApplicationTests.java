package com.boldyrev.foodhelper;

import com.boldyrev.foodhelper.models.IngredientCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class FoodhelperApplicationTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    EntityManager entityManager;

    @Test
    @Rollback(false)
    @Transactional
    void contextLoads() {
        Session session = entityManager.unwrap(Session.class);


        IngredientCategory cat = session.get(IngredientCategory.class, 9);

        //cat.getChildCategories().forEach((x) -> System.out.println(x.getName()));

        //System.out.println(cat.getParentCategory().getName());
//        Query query = session.createQuery("from IngredientCategory ic", IngredientCategory.class);
//        System.out.println(query.getResultList());
    }

}
