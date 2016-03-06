package com.alorma.travis.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.travis.R;
import com.alorma.travis.ui.fragment.repositories.RepositoriesFragment;
import com.alorma.travis.ui.presenter.accounts.CredentialsPresenter;
import com.alorma.travisdk.bean.response.GithubAccount;
import com.alorma.travisdk.bean.utils.Credential;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import java.util.List;
import java.util.Map;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder public class MainActivity extends BaseActivity
    implements CredentialsPresenter.CredentialsPresenterCallback, Drawer.OnDrawerItemClickListener {

  private CredentialsPresenter credentialsPresenter;

  @Bind(R.id.toolbar) Toolbar toolbar;
  private AccountHeader accountHeader;
  private Drawer drawer;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainActivityIntentBuilder.inject(getIntent(), this);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    accountHeader = new AccountHeaderBuilder().withActivity(this)
        .withHeaderBackground(R.color.primary_dark)
        .withCompactStyle(true)
        .build();
    drawer = new DrawerBuilder().withActivity(this)
        .withToolbar(toolbar)
        .withAccountHeader(accountHeader)
        .withOnDrawerItemClickListener(this)
        .build();

    credentialsPresenter = new CredentialsPresenter(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    credentialsPresenter.setCallback(this);
    credentialsPresenter.start();
  }

  @Override
  protected void onStop() {
    super.onStop();
    credentialsPresenter.stop();
  }

  @Override
  public void willLoadCredentials() {

  }

  @Override
  public void showLogin() {

  }

  @Override
  public void showListCredentials(Map<Credential, List<GithubAccount>> credentials) {
    accountHeader.clear();
    drawer.removeAllItems();
    for (Credential credential : credentials.keySet()) {
      ProfileDrawerItem profile = new ProfileDrawerItem();
      profile.withName(credential.getName());
      profile.withIcon(credential.getAvatar());
      accountHeader.addProfiles(profile);
    }

    if (credentials.size() > 0) {
      List<GithubAccount> accountList = credentials.get(credentials.keySet().iterator().next());

      if (accountList != null) {
        for (GithubAccount githubAccount : accountList) {
          ProfileDrawerItem subItem = new ProfileDrawerItem();
          subItem.withName(githubAccount.getLogin());
          subItem.withIcon(githubAccount.getAvatarUrl());
          subItem.withIdentifier(githubAccount.getId());

          drawer.addItem(subItem);
        }

        if (accountList.size() > 0) {
          drawer.setSelection(accountList.get(0).getId(), true);
        }
      }
    }
  }

  @Override
  public void didLoadCredentials() {

  }

  @Override
  public void loadAccount(GithubAccount account) {
    setTitle(account.getLogin());
    RepositoriesFragment fragment = new RepositoriesFragment();
    Bundle bundle = new Bundle();
    bundle.putString(RepositoriesFragment.ACCOUNT_NAME, account.getLogin());
    fragment.setArguments(bundle);
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.content, fragment);
    ft.commit();
  }

  @Override
  protected void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  @Override
  public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
    credentialsPresenter.selectAccountWithId(drawerItem.getIdentifier());

    return false;
  }
}
