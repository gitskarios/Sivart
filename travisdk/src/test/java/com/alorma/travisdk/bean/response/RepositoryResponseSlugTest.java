package com.alorma.travisdk.bean.response;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryResponseSlugTest {

  private RepositoryResponse repositoryResponse;

  @Before
  public void setUp() throws Exception {
    repositoryResponse = new RepositoryResponse();
  }

  @Test
  public void givenRepositorySlugShouldOwnerNotNull() {
    repositoryResponse.setSlug("alorma/timeline");

    assertThat(repositoryResponse.getOwner()).isEqualTo("alorma");
  }

  @Test
  public void givenRepositorySlugShouldRepoNotNull() {
    repositoryResponse.setSlug("alorma/timeline");

    assertThat(repositoryResponse.getRepo()).isEqualTo("timeline");
  }
}