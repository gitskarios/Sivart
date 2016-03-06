package com.alorma.travisdk.datasource.builds.cache;

import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.bean.response.TravisBuildResponse;
import com.fewlaps.quitnowcache.QNCache;
import com.fewlaps.quitnowcache.QNCacheBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheBuildWrapper {

  private static final String REPO_KEY_PREFIX = "repo_id_";
  private static final String KEY_PREFIX = "build_id_";

  private static QNCache<TravisBuild> cacheItem =
      new QNCacheBuilder().setDefaultKeepaliveInMillis(TimeUnit.MINUTES.toMillis(20))
          .createQNCache();

  private static QNCache<List<TravisBuildResponse>> cacheList =
      new QNCacheBuilder().setDefaultKeepaliveInMillis(TimeUnit.MINUTES.toMillis(20))
          .createQNCache();

  private static String convertToEffectiveReposKey(long repoId, long buildId) {
    return REPO_KEY_PREFIX + repoId + "_" + KEY_PREFIX + buildId;
  }

  private static String convertToEffectiveReposKey(String owner, String name) {
    return owner + "/" + name;
  }

  public static void clear() {
    cacheItem.removeAll();
  }

  public static TravisBuild get(long repoId, long buildId) {
    return cacheItem.get(convertToEffectiveReposKey(repoId, buildId));
  }

  public static void set(long repoId, long buildId, TravisBuild build) {
    cacheItem.set(convertToEffectiveReposKey(repoId, buildId), build);
  }

  public static List<TravisBuildResponse> get(String owner, String name) {
    return cacheList.get(convertToEffectiveReposKey(owner, name));
  }

  public static void set(String owner, String name, List<TravisBuildResponse> builds) {
    cacheList.set(convertToEffectiveReposKey(owner, name), builds);
  }
}