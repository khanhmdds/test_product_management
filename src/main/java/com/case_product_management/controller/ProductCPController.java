//package com.codegym.controller;
//
//import com.codegym.utils.AppUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@RequestMapping("/cp/products")
//public class ProductCPController {
//    @Autowired
//    private AppUtils appUtils;
//
//    @GetMapping
//    public ModelAndView showProductList() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("staff", appUtils.getStaff());
//        modelAndView.setViewName("/product/listProduct");
//        return modelAndView;
//    }
//
//
//
//    @GetMapping("/create")
//    public ModelAndView showCreateForm() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("staff", appUtils.getStaff());
//        modelAndView.setViewName("/product/createProduct");
//        return modelAndView;
//    }
//
//
//
//    @GetMapping("/update")
//    public ModelAndView showUpdateForm() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("staff", appUtils.getStaff());
//        modelAndView.setViewName("product/update");
//        return modelAndView;
//    }
//}
