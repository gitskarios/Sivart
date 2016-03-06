package com.alorma.travis.ui.fragment.builds;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.travis.R;
import com.alorma.travis.ui.Enumerators;
import com.alorma.travis.ui.activity.TravisBuildActivityIntentBuilder;
import com.alorma.travis.ui.adapter.BuildsAdapter;
import com.alorma.travis.ui.presenter.builds.BuildListPresenter;
import com.alorma.travisdk.bean.response.TravisBuildResponse;
import java.util.List;

public class TravisBuildsListFragment extends Fragment
    implements BuildListPresenter.BuildsCallback, BuildsAdapter.BuildAdapterCallback {

  private BuildListPresenter presenter;

  @Bind(R.id.recycler) RecyclerView recyclerView;
  @Bind(R.id.progressBar) ProgressBar progressBar;

  private BuildsAdapter buildsAdapter;

  private String repoOwner;
  private String repoName;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.build_list_fragment, null, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    presenter = new BuildListPresenter();

    buildsAdapter = new BuildsAdapter();
    buildsAdapter.setBuildAdapterCallback(this);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(buildsAdapter);

    readArguments();
  }

  @Override
  public void onStart() {
    super.onStart();

    presenter.setBuildsCallback(this);
    presenter.start(repoOwner, repoName);
  }

  @Override
  public void onStop() {
    presenter.stop();
    super.onStop();
  }

  private void readArguments() {
    if (getArguments() != null) {
      repoOwner = getArguments().getString(Enumerators.Keys.Repository.EXTRA_OWNER);
      repoName = getArguments().getString(Enumerators.Keys.Repository.EXTRA_NAME);
    }
  }

  @Override
  public void willLoadBuilds() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void buildsLoaded(List<TravisBuildResponse> builds) {
    buildsAdapter.clear();
    buildsAdapter.addAll(builds);
  }

  @Override
  public void didLoadBuilds() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  @Override
  public void onBuildSelected(TravisBuildResponse travisBuild) {
    Intent intent =
        new TravisBuildActivityIntentBuilder(travisBuild.getRepoId(), travisBuild.getId()).build(
            getActivity());

    startActivity(intent);
  }
}
