package com.Interfaces;

// ממשק גנרי שיכריח את כל הסרוסים לממש אותו-פונקציות crud בסיסיות

import java.util.List;

public interface ICrudService<T, ID> {
    T save(T dto);
    List<T> findAll();
    T findById(ID id);
    void delete(ID id);
    T update(ID id, T dto); 
}

