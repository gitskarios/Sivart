package com.alorma.travis.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.travis.R;
import com.alorma.travis.ui.presenter.logs.LogPresenter;
import com.alorma.travisdk.bean.response.TravisJobResponse;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder public class TravisJobActivity extends BaseActivity
    implements LogPresenter.LogCallback {

  @Extra TravisJobResponse jobResponse;

  @Bind(android.R.id.text1) TextView textLog;
  @Bind(R.id.scrollView) NestedScrollView scrollView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getIntent() != null && getIntent().getExtras() != null) {
      TravisJobActivityIntentBuilder.inject(getIntent(), this);
    }
    setContentView(R.layout.activity_job);
    ButterKnife.bind(this);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onLogLoaded(String log) {
    textLog.setText(log);
    sendScroll();
  }

  private void sendScroll() {
    final Handler handler = new Handler();
    new Thread(() -> {
      handler.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }).start();
  }
}
