package com.springboot.ecommerceapp.integrationtests;

import com.springboot.ecommerceapp.exception.UserNotFoundException;
import com.springboot.ecommerceapp.services.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProfileTests {

	@Autowired
	UserService userService;

	@Given("^ User is logged in")
	public void userLoggedIn() {

	}

	@When("^ User clicks on view profile")
	public void userClickedOnViewProfile() throws UserNotFoundException {
		userService.getUser(1);
	}

	@Then("^ User view the details of the profile")
	public void validateUserProfile() {

	}

}
