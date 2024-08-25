
package com.woahware.demo.repository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;


// Generic repo test that other tests extend; allows for less duplicated code and flexibility for the code overall.
//  --> could also later add test cases for various crud operations like create, delete, etc.


@Transactional      // Ensures each test method runs in a transaction, rolling back after each method
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest     
public abstract class GenericRepositoryTest<T, R extends CrudRepository> {

    @Autowired
    protected R repository;

    @Test
    public void testFindById() {
        // Fetch the entity by its ID
        Optional<T> foundEntity = repository.findById(1L);
        assertTrue(foundEntity.isPresent());
    }

    @Test
    public void testFindAll() {
        // Fetch all entities
        List<T> entities = (List) repository.findAll();
        assertNotNull(entities);
        assertFalse(entities.isEmpty());
    }


    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            System.out.println("Starting Test:  " + description.getMethodName() + "()  for:  " + AopProxyUtils.proxiedUserInterfaces(repository)[0].getSimpleName());
        }
    };
    
}

