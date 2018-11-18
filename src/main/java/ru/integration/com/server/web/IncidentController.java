package ru.integration.com.server.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.integration.com.common.model.Address;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Incident;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eljah32 on 11/17/2018.
 */
@RestController
@RequestMapping("/rest/incidents")
public class IncidentController {

    final static Logger logger = LoggerFactory.getLogger(TodoController.class);

    Incident current=null;

    public IncidentController() {
    }

    @RequestMapping(method = RequestMethod.GET, path = "/current")
    //@ResponseStatus(HttpStatus.OK)
    public Incident current() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        if (current!=null) {
            Incident toBeReturne = current;
            current=null;
            return toBeReturne;

        } else {
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/new/{number}")
    @ResponseStatus(HttpStatus.OK)
    public void setNewIncident(@PathVariable String number) {
        current=new Incident();
        current.setIncedentStart(new Date());
        current.setPhoneNumber(number);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/address/{addressTyped}")
    @ResponseStatus(HttpStatus.OK)
    public Address setSearchAddress(@PathVariable String addressTyped) {
        Address address=new Address();
        address.setTypedAddress(addressTyped);
        List<String> nodes=new ArrayList<String>();
        nodes.add("245");
        nodes.add("247");
        nodes.add("249");
        address.setNodes(nodes);
        List<Customer> customerList=new ArrayList<Customer>();
        customerList.add(new Customer("ТСЖ Белая лебеда","Роторная, 1", "245", "79047640086"));
        customerList.add(new Customer("ТСЖ Прокрастинация","Роторная, 2", "247", "79047640087"));
        customerList.add(new Customer("ТСЖ Pathetic Brilliant Lifestyle","Роторная, 3", "248", "79047640088"));
        address.setCustomers(customerList);
        return  address;

    }
}

