package com.lambdaschool.usermodel.services;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Spring Boot needs to know what username to use for the auditing fields CreatedBy and ModifiedBy
 * For now, a default name will be used
 */
@Component
public class UserAuditing implements AuditorAware<String> {

  /**
   * The current user
   *
   * @return Optional(String) of current user
   */
  @Override
  public Optional<String> getCurrentAuditor() {
    String uname;
    uname = "SYSTEM";
    return Optional.of(uname);
  }
}
