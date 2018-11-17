package ru.integration.com.server.web;

import ru.integration.com.common.model.Todo;
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

    List<Todo> todoList = new ArrayList<>();

    public CustomerController(){
        todoList.add(new Todo("Todo #1"));
        todoList.add(new Todo("Todo #2"));
        todoList.add(new Todo("Todo #3"));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> all() {
        return todoList;
    }
}

