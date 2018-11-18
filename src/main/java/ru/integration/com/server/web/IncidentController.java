package ru.integration.com.server.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

    static boolean requested=false;

    public IncidentController() {
    }

    @RequestMapping(method = RequestMethod.GET, path = "/current")
    //@ResponseStatus(HttpStatus.OK)
    public Incident current() {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {

        }

        if (!requested) {
            Incident toBeReturne = new Incident();
            toBeReturne.setIncedentStart(new Date());
            requested=true;
            return toBeReturne;

        } else {
            return null;
        }
    }
}

