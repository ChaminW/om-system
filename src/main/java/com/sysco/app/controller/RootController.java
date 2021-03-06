package com.sysco.app.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class RootController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @ApiOperation(value = "view API documentation", produces = "test/HTML")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView rootService() {

        logger.info("Service initiated");
        return new RedirectView("/swagger-ui.html");
    }

}
