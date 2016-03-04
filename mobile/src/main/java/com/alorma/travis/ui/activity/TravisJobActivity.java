package com.alorma.travis.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.travis.R;
import com.alorma.travis.ui.presenter.logs.LogPresenter;
import com.alorma.travisdk.bean.response.TravisJobResponse;
import com.alorma.travisdk.bean.utils.Credential;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder public class TravisJobActivity extends BaseActivity
    implements LogPresenter.LogCallback {

  @Extra TravisJobResponse jobResponse;
  @Extra Credential credential;

  @Bind(android.R.id.text1) TextView textLog;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getIntent() != null && getIntent().getExtras() != null) {
      TravisJobActivityIntentBuilder.inject(getIntent(), this);
    }
    setContentView(R.layout.activity_job);
    ButterKnife.bind(this);

    LogPresenter logPresenter = new LogPresenter(jobResponse);
    logPresenter.setLogCallback(this);
    logPresenter.start();
  }

  @Override
  protected void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  @Override
  public void onLogLoaded(String log) {
    textLog.setText(log);
  }
}
