package com.exampleesame.store;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.exampleesame.entity.Customer;

/**
 * @author piny73
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class CustomerStore extends BaseStore<Customer>  {
    
    public List<Customer> all() {

        return em.createQuery("select e from Customer e where e.canceled = false",Customer.class)
                .getResultList();

    }

     public Optional<Customer> find(Long id){
        
        Customer found = em.find(Customer.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }
     
     
        public Optional<Customer> findCustomerbyCustomerName(String Name) {
        try{
            
            return Optional.of(
                    em.createQuery("select e from Customer e where e.name = :email and e.canceled = false", Customer.class)
                    .setParameter("name", Name)
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

