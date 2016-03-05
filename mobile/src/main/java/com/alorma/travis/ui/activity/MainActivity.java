package com.alorma.travis.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.travis.R;
import com.alorma.travis.ui.presenter.accounts.CredentialsPresenter;
import com.alorma.travisdk.bean.response.GithubAccount;
import com.alorma.travisdk.bean.utils.Credential;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import java.util.List;
import java.util.Map;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder public class MainActivity extends BaseActivity
    implements CredentialsPresenter.CredentialsPresenterCallback {

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
    for (Credential credential : credentials.keySet()) {
      ProfileDrawerItem profile = new ProfileDrawerItem();
      profile.withName(credential.getName());
      profile.withIcon(credential.getAvatar());
      profile.withEmail(credentials.get(credential).size() + " accounts");
      accountHeader.addProfiles(profile);
    }
  }

  @Override
  public void didLoadCredentials() {

  }

  @Override
  protected void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }
}
