package com.codecool.shop.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.IOException;
import java.util.Map;

public abstract class Controller {

    public abstract String render(Request req, Response res) throws IOException;

    public static String renderTemplate(Map model, String template) {
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, template));
    }

}
