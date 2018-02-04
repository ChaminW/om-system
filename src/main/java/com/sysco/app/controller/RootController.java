package com.sysco.app.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Api(value = "home", description = "Expose Swagger API Documentation")
public class RootController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView rootService() {

        logger.info("Service initiated");

        // return new ResponseEntity<String>("Service initiated", HttpStatus.OK);
        return new RedirectView("/swagger-ui.html#/");
    }
}
