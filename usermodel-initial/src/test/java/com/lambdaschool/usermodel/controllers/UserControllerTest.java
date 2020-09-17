package com.lambdaschool.usermodel.controllers;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private Role role1;
  private Role role2;
  private List<User> users;

  @Before
  public void setUp() {
    users = new ArrayList<>();
    FakeValuesService fakeValuesService = new FakeValuesService(
      new Locale("en-US"),
      new RandomService()
    );
    Faker nameFaker = new Faker(new Locale("en-US"));
    role1 = new Role("Admin");
    role1.setRoleid(1);
    role2 = new Role("User");
    role2.setRoleid(2);
    for (int i = 0; i < 25; i++) {
      new User();
      User fakeUser;

      fakeUser =
        new User(
          nameFaker.name().username() + i,
          "password",
          nameFaker.internet().emailAddress() + i
        );
      fakeUser.getRoles().add(new UserRoles(fakeUser, role2));
      fakeUser
        .getUseremails()
        .add(
          new Useremail(fakeUser, fakeValuesService.bothify("????##@gmail.com"))
        );
      fakeUser.setUserid(i);
      users.add(fakeUser);
    }
  }

  @After
  public void tearDown() {}

  @Test
  public void a_listAllUsers() throws Exception {
    var apiUrl = "/users/users";
    Mockito.when(userService.findAll()).thenReturn(users);

    var builder = MockMvcRequestBuilders
      .get(apiUrl)
      .accept(MediaType.APPLICATION_JSON);
    var result = mockMvc.perform(builder).andReturn();
    var jsonResult = result.getResponse().getContentAsString();

    var usersAsJson = new ObjectMapper().writeValueAsString(users);
    assertEquals(jsonResult, usersAsJson);
  }
}
