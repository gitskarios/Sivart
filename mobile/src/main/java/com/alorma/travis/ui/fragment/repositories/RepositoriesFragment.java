package com.alorma.travis.ui.fragment.repositories;

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
import com.alorma.travis.ui.activity.RepositoryActivityIntentBuilder;
import com.alorma.travis.ui.adapter.ReposAdapter;
import com.alorma.travis.ui.presenter.repositories.RepositoriesPresenter;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import java.util.List;

public class RepositoriesFragment extends Fragment
    implements RepositoriesPresenter.RepositoriesPresenterCallback,
    ReposAdapter.ReposAdapterCallback {

  public static final String ACCOUNT_NAME = "ACCOUNT_NAME";

  @Bind(R.id.recycler_view) RecyclerView recyclerView;
  @Bind(R.id.progressBar) ProgressBar progressBar;

  private RepositoriesPresenter presenter;
  private ReposAdapter reposAdapter;

  private String accountName;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.repositories_fragment, null, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    presenter = new RepositoriesPresenter();

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    reposAdapter = new ReposAdapter();
    reposAdapter.setReposAdapterCallback(this);
    recyclerView.setAdapter(reposAdapter);

    if (getArguments() != null) {
      accountName = getArguments().getString(ACCOUNT_NAME);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    if (accountName != null) {
      presenter.setRepositoriesCallback(this);
      presenter.loadRepositories(accountName);
    }
  }

  @Override
  public void onStop() {
    presenter.stop();
    super.onStop();
  }

  @Override
  public void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  @Override
  public void willLoadRepositories() {
    progressBar.setVisibility(View.VISIBLE);
    reposAdapter.clear();
  }

  @Override
  public void repositoriesLoaded(List<RepositoryResponse> responses) {
    if (reposAdapter != null) {
      reposAdapter.clear();
      reposAdapter.addAll(responses);
    }
  }

  @Override
  public void didLoadRepositories() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void onRepoSelected(RepositoryResponse repositoryResponse) {
    Intent intent = new RepositoryActivityIntentBuilder(repositoryResponse).build(getActivity());
    startActivity(intent);
  }
}
