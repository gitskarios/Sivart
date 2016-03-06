package com.alorma.travis.ui.fragment.builds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.travis.R;
import com.alorma.travis.ui.adapter.BuildsAdapter;
import com.alorma.travis.ui.presenter.builds.BuildListPresenter;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.response.TravisBuildResponse;
import java.util.List;

public class TravisBuildsListFragment extends Fragment
    implements BuildListPresenter.BuildsCallback, BuildsAdapter.BuildAdapterCallback {

  private RepositoryResponse repositoryResponse;
  private BuildListPresenter presenter;

  @Bind(R.id.recycler) RecyclerView recyclerView;
  @Bind(R.id.progressBar) ProgressBar progressBar;

  private BuildsAdapter buildsAdapter;

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
    if (repositoryResponse != null) {
      presenter.start(repositoryResponse);
    }
  }

  @Override
  public void onStop() {
    presenter.stop();
    super.onStop();
  }

  private void readArguments() {
    if (getArguments() != null) {
      repositoryResponse = getArguments().getParcelable(RepositoryResponse.class.getSimpleName());
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
    Toast.makeText(getActivity(), "Build: " + travisBuild.getNumber(), Toast.LENGTH_SHORT).show();
  }
}
