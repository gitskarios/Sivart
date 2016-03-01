package com.alorma.travis.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.github.sdk.bean.dto.response.Organization;
import com.alorma.github.sdk.bean.dto.response.User;
import com.alorma.travis.R;
import com.alorma.travis.ui.adapter.AccountsAdapter;
import com.alorma.travis.ui.adapter.ReposAdapter;
import com.alorma.travis.ui.presenter.overview.AccountsPresenter;
import com.alorma.travis.ui.presenter.repositories.RepositoriesPresenter;
import com.alorma.travisdk.bean.response.RepositoryResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Random;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder public class OverviewActivity extends BaseActivity
    implements AccountsPresenter.AccountsPresenterCallback,
    RepositoriesPresenter.RepositoriesPresenterCallback, ReposAdapter.ReposAdapterCallback,
    AccountsAdapter.AccountsCallback {

  @Extra Credential credential;
  private AccountsPresenter accountsPresenter;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.accountLy) View accountLy;
  @Bind(R.id.currentAccountAvatar) ImageView currentAccountAvatar;
  @Bind(R.id.currentAccountName) TextView currentAccountName;
  @Bind(R.id.accountsExpand) ImageView accountsExpand;
  @Bind(R.id.repositoriesRecycler) RecyclerView repositoriesRecycler;

  private int[] travisMascots = new int[] {
      R.drawable.travis_mascot_1, R.drawable.travis_mascot_2, R.drawable.travis_mascot_3,
      R.drawable.travis_mascot_4, R.drawable.travis_mascot_5, R.drawable.travis_mascot_6,
      R.drawable.travis_mascot_7
  };

  private AccountsAdapter accountsAdapter;
  private ReposAdapter reposAdapter;
  private AlertDialog accountsDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getIntent() != null && getIntent().getExtras() != null) {
      OverviewActivityIntentBuilder.inject(getIntent(), this);
    }
    setContentView(R.layout.activity_overview);

    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    setTitle("Accounts");

    ViewCompat.setElevation(toolbar,
        getResources().getDimensionPixelOffset(R.dimen.materialize_baseline_grid));

    accountsAdapter = new AccountsAdapter();

    accountsAdapter.setAccountsCallback(this);

    accountsPresenter = new AccountsPresenter(credential);
    accountsExpand.setOnClickListener((v) -> expand());

    repositoriesRecycler.setLayoutManager(new LinearLayoutManager(this));
    reposAdapter = new ReposAdapter();
    reposAdapter.setReposAdapterCallback(this);
    repositoriesRecycler.setAdapter(reposAdapter);

    accountsAdapter.clear();
    accountsPresenter.setAccountsCallback(this);
    accountsPresenter.setRepositoriesCallback(this);
    accountsPresenter.start();
  }

  @Override
  public void onAccountSelected(Organization organization) {
    if (accountsDialog != null && accountsDialog.isShowing()) {
      accountsDialog.dismiss();
      accountsDialog = null;
    }
    showOrganization(organization);
  }

  private void showOrganization(Organization organization) {
    int logo = getRandomLogo();
    Glide.with(this)
        .load(organization.avatar_url)
        .placeholder(logo)
        .error(logo)
        .dontAnimate()
        .into(currentAccountAvatar);

    if (!TextUtils.isEmpty(organization.name)) {
      currentAccountName.setText(organization.name);
    } else {
      currentAccountName.setText(organization.login);
    }

    accountsPresenter.loadRepositories(organization.login);
  }

  private int getRandomLogo() {
    Random random = new Random();
    int length = travisMascots.length;
    int randomInt = random.nextInt(length);
    return travisMascots[randomInt];
  }

  private void expand() {
    if (accountsAdapter.getItemCount() > 0) {

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Select account");
      View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_recycler_view, null);

      RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bottom_sheet_recycler_view);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(accountsAdapter);

      builder.setView(view);
      accountsDialog = builder.show();
    }
  }

  @Override
  public void willLoadOrganizations() {

  }

  @Override
  public void userLoaded(User user) {
    accountsAdapter.add(user);
    showOrganization(user);
  }

  @Override
  public void organizationsLoaded(List<Organization> organizations) {
    accountsAdapter.addAll(organizations);
  }

  @Override
  public void onRepoSelected(RepositoryResponse repositoryResponse) {
    Intent intent = new RepositoryActivityIntentBuilder(credential, repositoryResponse).build(this);
    startActivity(intent);
  }

  @Override
  public void didLoadOrganizations() {

  }

  @Override
  protected void onStop() {
    accountsPresenter.stop();
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  @Override
  public void willLoadRepositories() {
    reposAdapter.clear();
  }

  @Override
  public void repositoriesLoaded(List<RepositoryResponse> responses) {
    reposAdapter.addAll(responses);
  }

  @Override
  public void didLoadRepositories() {

  }
}
