package com.forall.laundry.mobile;

import com.forall.laundry.model.Ordery;
import com.forall.laundry.service.CustomerService;
import com.forall.laundry.service.OrderyService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jd on 8/24/15.
 */
@Named
@RequestScoped
public class MobileOrderyController implements Serializable{

    @Inject
    private MobileController mc;

    @EJB
    private CustomerService customerService;

    @EJB
    private OrderyService orderyService;

    public void finishOrder(){
        Ordery order = customerService.getCurrentOrder(mc.getCustomer());
        order.setDate(new Date());

        orderyService.update(order);
        Ordery o = new Ordery();
        o.setCustomer(mc.getCustomer());
        customerService.update(mc.getCustomer());
        orderyService.save(o);

        mc.update();
    }
}
