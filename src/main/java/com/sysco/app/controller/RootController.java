package com.sysco.app.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;

@RestController
@Api(value = "home", description = "Expose Swagger API Documentation")
public class RootController {

    @Autowired
    MessageSource messageSource;

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView rootService() {

        logger.info("Service initiated");

        return new RedirectView("/swagger-ui.html#/");
    }

//    @GetMapping(value = "/")
//    public String rootService() {
//        Locale locale = LocaleContextHolder.getLocale();
//        return messageSource.getMessage(
//                "welcome.message", new Object[]{"User"}, locale);
//    }
}
