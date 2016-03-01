package com.alorma.travisdk.datasource.repos.cache;

import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.fewlaps.quitnowcache.QNCache;
import com.fewlaps.quitnowcache.QNCacheBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheReposWrapper {

  private static final String REPO_KEY_PREFIX = "repos_owned_";

  private static QNCache<List<RepositoryResponse>> cache =
      new QNCacheBuilder().setDefaultKeepaliveInMillis(TimeUnit.MINUTES.toMillis(10))
          .createQNCache();

  private static String convertToEffectiveReposKey(String owner) {
    return REPO_KEY_PREFIX + owner;
  }

  public static void clear() {
    cache.removeAll();
  }

  public static List<RepositoryResponse> get(String owner) {
    return cache.get(convertToEffectiveReposKey(owner));
  }

  public static void set(String owner, List<RepositoryResponse> items) {
    cache.set(convertToEffectiveReposKey(owner), items);
  }
}