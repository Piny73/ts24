package com.exampleesame.store;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.exampleesame.entity.TimeSheet;

/**
 * @author piny73
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class TimeSheetStore extends BaseStore<TimeSheet>  {
    
    public List<TimeSheet> all() {

        return em.createQuery("select e from TimeSheet e where e.canceled = false",TimeSheet.class)
                .getResultList();

    }

     public Optional<TimeSheet> find(Long id){
        
        TimeSheet found = em.find(TimeSheet.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }
     
     
        public Optional<TimeSheet> findTimeSheetbyProject(String project) {
        try{
            
            return Optional.of(
                    em.createQuery("select e from TimeSheet e where e.project = :project and e.canceled = false", TimeSheet.class)
                    .setParameter("project", project)
                    .getSingleResult()
                    );
            
        } catch (NoResultException ex) {
            
            return Optional.empty();                    
            
        }
            
    }
         
    /*public Optional<Customer> login(Credential credential) {
        try{
            
            return Optional.of(
                    em.createQuery("select e from User e where e.email = :usr and e.pwd = :pwd and e.canceled = false", Customer.class)
                    .setParameter("usr", credential.usr)
                    .setParameter("pwd", SecurityEncoding.shaHash(credential.pwd))
                    .getSingleResult()
                    );
            
        } catch (NoResultException ex) {
            
            return Optional.empty();                    
            
        }
            
    }/* */
    
    /*@Override
    public Customer save(Customer entity){
    
        entity.setPwd(SecurityEncoding.shaHash(entity.getPwd()));
        return super.save(entity);
        
    }*/
     
    
}

