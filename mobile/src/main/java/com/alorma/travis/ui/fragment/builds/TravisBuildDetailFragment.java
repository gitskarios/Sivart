package com.alorma.travis.ui.fragment.builds;

import android.content.Intent;
import android.graphics.Color;
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
import com.alorma.travis.ui.activity.TravisJobActivityIntentBuilder;
import com.alorma.travis.ui.adapter.BuildInfo;
import com.alorma.travis.ui.adapter.BuildInfosAdapter;
import com.alorma.travis.ui.adapter.JobBuildInfo;
import com.alorma.travis.ui.holder.drawable.IconicCompoundDrawableHolder;
import com.alorma.travis.ui.holder.drawable.IconicDrawableHolder;
import com.alorma.travis.ui.holder.string.LongStringPrimaryHolder;
import com.alorma.travis.ui.holder.string.StringPrimaryHolder;
import com.alorma.travis.ui.presenter.builds.LastBuildPresenter;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.response.TravisBuild;
import com.alorma.travisdk.bean.response.TravisJobResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.octicons_typeface_library.Octicons;
import java.util.ArrayList;
import java.util.List;

public class TravisBuildDetailFragment extends Fragment
    implements LastBuildPresenter.LastBuildCallback, BuildInfosAdapter.BuildInfoCallback {

  private Credential credential;
  private RepositoryResponse repositoryResponse;
  private LastBuildPresenter presenter;

  @Bind(R.id.travisBuildStates) RecyclerView travisStates;
  @Bind(R.id.progressBar) ProgressBar progressBar;

  private BuildInfosAdapter buildInfosAdapter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.job_build_fragment, null, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    presenter = new LastBuildPresenter();

    buildInfosAdapter = new BuildInfosAdapter();
    buildInfosAdapter.setBuildInfoCallback(this);
    travisStates.setLayoutManager(new LinearLayoutManager(getActivity()));
    travisStates.setAdapter(buildInfosAdapter);

    readArguments();
  }

  @Override
  public void onStart() {
    super.onStart();

    presenter.setLastBuildCallback(this);
    if (credential != null && repositoryResponse != null) {
      presenter.start(credential, repositoryResponse);
    }
  }

  @Override
  public void onStop() {
    presenter.stop();
    super.onStop();
  }

  private void readArguments() {
    if (getArguments() != null) {
      credential = getArguments().getParcelable(Credential.class.getSimpleName());
      repositoryResponse = getArguments().getParcelable(RepositoryResponse.class.getSimpleName());
    }
  }

  @Override
  public void willLoadBuild() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void buildLoaded(TravisBuild build) {
    List<BuildInfo> infos = mapBuildInfos(build);
    buildInfosAdapter.clear();
    buildInfosAdapter.addAll(infos);
  }

  private List<BuildInfo> mapBuildInfos(TravisBuild build) {
    List<BuildInfo> infos = new ArrayList<>();

    mapBuildCommit(build, infos);

    return infos;
  }

  private void mapBuildCommit(TravisBuild build, List<BuildInfo> infos) {
    if (build.getCommit() != null) {
      BuildInfo buildInfoBranch = new BuildInfo();
      buildInfoBranch.setDrawableHolder(getIcon(Octicons.Icon.oct_git_branch));
      buildInfoBranch.setTitle(new StringPrimaryHolder("Branch"));
      buildInfoBranch.setValue(new StringPrimaryHolder(build.getCommit().getBranch()));
      infos.add(buildInfoBranch);

      BuildInfo buildInfoCommitter = new BuildInfo();
      buildInfoCommitter.setDrawableHolder(getIcon(Octicons.Icon.oct_person));
      buildInfoCommitter.setTitle(new StringPrimaryHolder("Author"));
      buildInfoCommitter.setValue(new StringPrimaryHolder(build.getCommit().getAuthorName()));
      infos.add(buildInfoCommitter);

      BuildInfo buildInfoCommitSha = new BuildInfo();
      buildInfoCommitSha.setDrawableHolder(getIcon(Octicons.Icon.oct_git_commit));
      buildInfoCommitSha.setTitle(new StringPrimaryHolder("Commit"));
      buildInfoCommitSha.setValue(
          new StringPrimaryHolder(build.getCommit().getSha().substring(0, 8)));
      infos.add(buildInfoCommitSha);

      BuildInfo buildInfoStatus = new BuildInfo();
      buildInfoStatus.setDrawableHolder(
          getIcon(Octicons.Icon.oct_git_commit).color(getColorFromState(build)));
      buildInfoStatus.setTitle(new LongStringPrimaryHolder(build.getBuild().getNumber()));
      buildInfoStatus.setValue(new StringPrimaryHolder(build.getBuild().getState().toLowerCase()));
      infos.add(buildInfoStatus);

      List<TravisJobResponse> jobs = build.getJobs();

      if (jobs != null && jobs.size() > 1) {
        for (TravisJobResponse job : jobs) {
          JobBuildInfo jobBuild = new JobBuildInfo();
          jobBuild.setDrawableHolder(getIcon(getIconFromState(job)).color(getColorFromState(job)));
          jobBuild.setTitle(new StringPrimaryHolder(job.getNumber()));
          jobBuild.setJob(job);
          infos.add(jobBuild);
        }
      }
    }
  }

  private int getColorFromState(TravisBuild build) {
    if (build.isBuildOk()) {
      return Color.GREEN;
    } else if (build.getBuild().getState().equalsIgnoreCase("failed")) {
      return Color.RED;
    } else if (build.getBuild().getState().equalsIgnoreCase("started") || build.getBuild()
        .getState()
        .equalsIgnoreCase("created")) {
      return Color.YELLOW;
    }
    return Color.DKGRAY;
  }

  private int getColorFromState(TravisJobResponse job) {
    if (job.isJobOk()) {
      return Color.GREEN;
    } else if (job.getState().equalsIgnoreCase("failed")) {
      return Color.RED;
    } else if (job.getState().equalsIgnoreCase("started") || job.getState()
        .equalsIgnoreCase("created")) {
      return Color.YELLOW;
    }
    return Color.DKGRAY;
  }

  private Octicons.Icon getIconFromState(TravisJobResponse job) {
    if (job.isJobOk()) {
      return Octicons.Icon.oct_check;
    } else if (job.getState().equalsIgnoreCase("failed")) {
      return Octicons.Icon.oct_x;
    } else if (job.getState().equalsIgnoreCase("started") || job.getState()
        .equalsIgnoreCase("created")) {
      return Octicons.Icon.oct_zap;
    }
    return Octicons.Icon.oct_terminal;
  }

  private IconicDrawableHolder getIcon(IIcon icon) {
    return new IconicCompoundDrawableHolder(getActivity(), icon).size(
        R.dimen.materialize_baseline_grid_x2).color(Color.DKGRAY);
  }

  @Override
  public void didLoadBuild() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  @Override
  public void onJobSelected(TravisJobResponse jobResponse) {
    Intent intent =
        new TravisJobActivityIntentBuilder(jobResponse, credential).build(getActivity());
    startActivity(intent);
  }
}
