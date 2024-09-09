package com.exampleesame.store;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.exampleesame.boundary.mapping.Credential;
import com.exampleesame.entity.User;
import com.exampleesame.security.SecurityEncoding;

/**
 * @author piny73
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class UserStore extends BaseStore<User>  {
    
    public List<User> all() {

        return em.createQuery("select e from User e where e.canceled = false",User.class)
                .getResultList();

    }

     public Optional<User> find(Long id){
        
        User found = em.find(User.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }
     
     
         public Optional<User> findUserbyLogin(String login) {
        try{
            
            return Optional.of(
                    em.createQuery("select e from User e where e.email = :login and e.canceled = false", User.class)
                    .setParameter("login", login)
                    .getSingleResult()
                    );
            
        } catch (NoResultException ex) {
            
            return Optional.empty();                    
            
        }
            
    }
         
    public Optional<User> login(Credential credential) {
        try{
            
            return Optional.of(
                    em.createQuery("select e from User e where e.email = :usr and e.pwd = :pwd and e.canceled = false", User.class)
                    .setParameter("usr", credential.usr)
                    .setParameter("pwd", SecurityEncoding.shaHash(credential.pwd))
                    .getSingleResult()
                    );
            
        } catch (NoResultException ex) {
            
            return Optional.empty();                    
            
        }
            
    }
    
    @Override
    public User save(User entity){
    
        entity.setPwd(SecurityEncoding.shaHash(entity.getPwd()));
        return super.save(entity);
        
    }
     
    
}
