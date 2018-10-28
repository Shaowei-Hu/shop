package com.shaowei.shop.cucumber.stepdefs;

import com.shaowei.shop.ShopApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ShopApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
