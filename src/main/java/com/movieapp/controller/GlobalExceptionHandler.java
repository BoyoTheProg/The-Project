package com.movieapp.controller;

import com.movieapp.model.ObjectNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class, ObjectNotFoundException.class})
    public ModelAndView handleNotFoundException(HttpServletRequest req, Exception ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("404");
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleInternalServerError(HttpServletRequest req, Exception ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("500");
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }
}