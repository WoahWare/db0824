
package com.woahware.demo.service;

import java.util.List;
import javax.transaction.Transactional;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

// Generic service test that other tests extend; allows for less duplicated code and flexibility for the code overall.

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class GenericServiceTest<T, S extends GenericService<T>> {

    @Autowired
    protected S service;

    @Test
    public void testGetById() {
        T entity = service.getById(1L);
        assertNotNull(entity);
    }

    @Test
    public void testGetAll() {
        List<T> entities = service.getAll();
        assertNotNull(entities);
        assertFalse(entities.isEmpty());
    }
    
    
    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            System.out.println("Starting Test:  " + description.getMethodName() + "()  for:  " + service.getClass().getSimpleName());
        }
    };
    
}

