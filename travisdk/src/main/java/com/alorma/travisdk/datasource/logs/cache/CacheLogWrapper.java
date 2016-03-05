package com.alorma.travisdk.datasource.logs.cache;

import com.alorma.travisdk.bean.response.TravisLogResponse;
import com.fewlaps.quitnowcache.QNCache;
import com.fewlaps.quitnowcache.QNCacheBuilder;
import java.util.concurrent.TimeUnit;

public class CacheLogWrapper {

  private static final String REPO_KEY_PREFIX = "job_id_";

  private static QNCache<String> cache =
      new QNCacheBuilder().setDefaultKeepaliveInMillis(TimeUnit.MINUTES.toMillis(20))
          .createQNCache();

  private static String convertToEffectiveLogKey(long jobId) {
    return REPO_KEY_PREFIX + jobId;
  }

  public static void clear() {
    cache.removeAll();
  }

  public static String get(long jobId) {
    return cache.get(convertToEffectiveLogKey(jobId));
  }

  public static void set(long jobId, String log) {
    cache.set(convertToEffectiveLogKey(jobId), log);
  }
}