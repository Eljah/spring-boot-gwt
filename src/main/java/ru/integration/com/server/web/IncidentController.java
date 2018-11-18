package ru.integration.com.server.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
}

