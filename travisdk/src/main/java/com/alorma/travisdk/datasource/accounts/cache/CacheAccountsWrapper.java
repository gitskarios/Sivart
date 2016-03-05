package com.alorma.travisdk.datasource.accounts.cache;

import com.alorma.travisdk.bean.response.GithubAccount;
import com.fewlaps.quitnowcache.QNCache;
import com.fewlaps.quitnowcache.QNCacheBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheAccountsWrapper {

  private static final String ACCOUNTS_KEY_PREFIX = "accounts:";

  private static QNCache<List<GithubAccount>> cache =
      new QNCacheBuilder().setDefaultKeepaliveInMillis(TimeUnit.MINUTES.toMillis(10))
          .createQNCache();

  private static String convertToEffectiveKey(String name, String token) {
    return ACCOUNTS_KEY_PREFIX + name + ":" + token;
  }

  public static void clear() {
    cache.removeAll();
  }

  public static List<GithubAccount> get(String name, String token) {
    return cache.get(convertToEffectiveKey(name, token));
  }

  public static void set(String name, String token, List<GithubAccount> items) {
    List<GithubAccount> githubAccounts = get(name, token);
    if (githubAccounts == null || githubAccounts.isEmpty()) {
      cache.set(convertToEffectiveKey(name, token), items);
    } else {
      githubAccounts.addAll(items);
      cache.set(convertToEffectiveKey(name, token), githubAccounts);
    }
  }
}