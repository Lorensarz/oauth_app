package com.petrov.oauth_app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String errorMessage = (throwable != null ? throwable.getMessage() : "N/A");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        String httpMethod = (String) request.getAttribute("javax.servlet.error.method");
        String stackTrace = getStackTrace(throwable);

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("requestUri", requestUri);
        model.addAttribute("httpMethod", httpMethod);
        model.addAttribute("stackTrace", stackTrace);

        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    private String getStackTrace(Throwable throwable) {
        if (throwable == null) {
            return "N/A";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
