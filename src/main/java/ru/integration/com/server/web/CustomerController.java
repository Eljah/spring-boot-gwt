package ru.integration.com.server.web;

import ru.integration.com.common.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eljah32 on 11/17/2018.
 */
@RestController
@RequestMapping("/rest/customers")
public class CustomerController {

    final static Logger logger = LoggerFactory.getLogger(TodoController.class);

    List<Customer> customerList = new ArrayList<>();

    public CustomerController(){
        customerList.add(new Customer("ТСЖ Белая лебеда","Роторная, 1", "245", "79047640086"));
        customerList.add(new Customer("ТСЖ Прокрастинация","Роторная, 2", "247", "79047640087"));
        customerList.add(new Customer("ТСЖ Pathetic Brilliant Lifestyle","Роторная, 3", "248", "79047640088"));
        customerList.add(new Customer("ТСЖ Ярканат","Роторная, 4", "249l", "79047640089"));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)


    public List<Customer> all() {

        return customerList;
    }
}

