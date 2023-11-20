package com.case_product_management.controller;

import com.case_product_management.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cp/staffs")
public class AdminCPController {
    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ModelAndView showStaffList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("staff", appUtils.getStaff());
        modelAndView.setViewName("staff/list");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("staff", appUtils.getStaff());
        modelAndView.setViewName("staff/create");
        return modelAndView;
    }

    @GetMapping("/view")
    public ModelAndView showViewForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("staff", appUtils.getStaff());
        modelAndView.setViewName("staff/view");
        return modelAndView;
    }
}
