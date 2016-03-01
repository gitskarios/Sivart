package com.alorma.travis.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.travis.R;
import com.alorma.travis.ui.fragment.build.TravisBuildDetailFragment;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder public class RepositoryActivity extends AppCompatActivity {

  @Extra Credential credential;
  @Extra RepositoryResponse repository;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.tabs) TabLayout tabLayout;
  @Bind(R.id.viewPager) ViewPager pager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    RepositoryActivityIntentBuilder.inject(getIntent(), this);
    setContentView(R.layout.activity_repository);

    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

    fab.setImageDrawable(
        new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_clear).color(Color.WHITE));

    setTitle(repository.getRepo());

    pager.setAdapter(new FragmentsAdapter(getSupportFragmentManager(), credential, repository));
    tabLayout.setupWithViewPager(pager);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  private class FragmentsAdapter extends FragmentPagerAdapter {

    private final Bundle args;

    public FragmentsAdapter(FragmentManager fm, Credential credential,
        RepositoryResponse repositoryResponse) {
      super(fm);

      args = new Bundle();
      args.putParcelable(Credential.class.getSimpleName(), credential);
      args.putParcelable(RepositoryResponse.class.getSimpleName(), repositoryResponse);
    }

    @Override
    public Fragment getItem(int position) {
      if (position == 0) {
        TravisBuildDetailFragment fragment = new TravisBuildDetailFragment();
        fragment.setArguments(args);
        return fragment;
      }
      return new ListFragment();
    }

    @Override
    public int getCount() {
      return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      if (position == 0) {
        return "Last build";
      } else if (position == 1) {
        return "Builds";
      }
      return "";
    }
  }
}
