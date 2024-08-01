package com.wanted.springcafe.utility;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FilterManager {

    private final EntityManager entityManager;

    public void enableFilter(String filterName, String paramName, Object paramValue){
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter(filterName);
        filter.setParameter(paramName, paramValue);
    }

    public void disableFilter(String filterName){
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter(filterName);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
