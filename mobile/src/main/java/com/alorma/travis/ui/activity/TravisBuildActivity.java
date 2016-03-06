package com.alorma.travis.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import com.alorma.travis.R;
import com.alorma.travis.ui.Enumerators;
import com.alorma.travis.ui.fragment.builds.TravisBuildDetailFragment;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder public class TravisBuildActivity extends BaseActivity {

  @Extra Long repoId;
  @Extra Long buildId;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TravisBuildActivityIntentBuilder.inject(getIntent(), this);
    setContentView(R.layout.activity_travis_build);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    TravisBuildDetailFragment buildDetailFragment = new TravisBuildDetailFragment();
    Bundle args = new Bundle();
    args.putLong(Enumerators.Keys.Repository.EXTRA_REPO_ID, repoId);
    args.putLong(Enumerators.Keys.Repository.EXTRA_BUILD_ID, buildId);
    buildDetailFragment.setArguments(args);

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.content, buildDetailFragment);
    ft.commit();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return super.onOptionsItemSelected(item);
  }
}
