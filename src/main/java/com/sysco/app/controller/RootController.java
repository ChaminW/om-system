package com.sysco.app.controller;

import com.sysco.app.model.Restaurant;
import com.sysco.app.repository.RestaurantRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@RestController
//@Api(value = "home", description = "Expose Swagger API Documentation")
@ApiIgnore
public class RootController {

    @Autowired
    MessageSource messageSource;

    @Qualifier("restaurantRepository")
    @Autowired
    RestaurantRepository restaurantRepository;

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @ApiOperation(value = "view API documentation", produces = "test/HTML")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView rootService() {

        logger.info("Service initiated");

        return new RedirectView("/swagger-ui.html#/");
    }

}
