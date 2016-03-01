package com.alorma.travisdk.datasource.builds.cache;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.fewlaps.quitnowcache.QNCache;
import com.fewlaps.quitnowcache.QNCacheBuilder;
import java.util.concurrent.TimeUnit;

public class CacheBuildWrapper {

  private static final String REPO_KEY_PREFIX = "repo_id_";
  private static final String KEY_PREFIX = "build_id_";

  private static QNCache<TravisBuild> cache =
      new QNCacheBuilder().setDefaultKeepaliveInMillis(TimeUnit.MINUTES.toMillis(20))
          .createQNCache();

  private static String convertToEffectiveReposKey(long repoId, long buildId) {
    return REPO_KEY_PREFIX + repoId + "_" + KEY_PREFIX + buildId;
  }

  public static void clear() {
    cache.removeAll();
  }

  public static TravisBuild get(long repoId, long buildId) {
    return cache.get(convertToEffectiveReposKey(repoId, buildId));
  }

  public static void set(long repoId, long buildId, TravisBuild build) {
    cache.set(convertToEffectiveReposKey(repoId, buildId), build);
  }
}