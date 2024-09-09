package com.exampleesame.store;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @author piny73
 */

@Transactional(Transactional.TxType.REQUIRED)
public class BaseStore<TEntity>{

    @PersistenceContext
    EntityManager em;


    public TEntity save(TEntity obj) {
       
        return em.merge(obj);
        
    }

   
    public TEntity update(TEntity obj) {

        return em.merge(obj);
    }


    public void remove(TEntity obj) {

        em.remove(obj);
        
    }

}
