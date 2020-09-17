package com.lambdaschool.usermodel.services;

import static org.junit.Assert.assertEquals;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {
  @Autowired
  private UserService userService;

  @Autowired
  RoleService roleService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void tearDown() {}

  @Test
  public void a_findAll() {
    assertEquals(5, userService.findAll().size());
  }

  @Test
  public void b_findUserById() {
    List<User> users = userService.findAll();
    Random random = new Random();
    var toExpect = users.get(random.nextInt(users.size()));
    User dataUser = userService.findUserById(toExpect.getUserid());
    assertEquals(toExpect.getUsername(), dataUser.getUsername());
  }

  @Test(expected = ResourceNotFoundException.class)
  public void c_findUserByIdNotFound() {
    assertEquals("", userService.findUserById(50000).getUsername());
  }

  @Test
  public void d_findByNameContaining() {
    assertEquals(1, userService.findByNameContaining("cinnamo").size());
  }
}
