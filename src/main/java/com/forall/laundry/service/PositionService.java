package com.forall.laundry.service;

import com.forall.laundry.model.Position;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by jd on 9/21/15.
 */
@Stateless
public class PositionService implements Serializable{

    @PersistenceContext
    private EntityManager em;

    public void save(final Position position){
        em.persist(position);
    }

    public void update(final Position position){

        if(position.getAmount() < 1){
            em.createQuery("DELETE FROM Position p WHERE p.id = :id")
                    .setParameter("id", position.getId())
                    .executeUpdate();
        }else{
            em.merge(position);
        }

    }
}
