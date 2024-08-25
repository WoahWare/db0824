
package com.woahware.demo.service;

import java.util.List;

// All services extend this interface (or at least the ones that we want to extend it); 
//  -> this allows us to create genertic tests easily without duplicating code (great for "spot check" tests)
public interface GenericService<T> {
    T getById(long id);
    List<T> getAll();
    
}
