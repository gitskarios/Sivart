package com.alorma.travisdk.bean.response;

public enum BuildStateResponse {
  CREATED,
  QUEUED,
  STARTED,
  PASSED,
  FAILED,
  ERRORED,
  CANCELED,
  READY;
}