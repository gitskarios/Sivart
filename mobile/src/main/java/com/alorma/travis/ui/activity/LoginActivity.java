package com.alorma.travis.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alorma.travis.R;
import com.alorma.travis.ui.adapter.CredentialsAdapter;
import com.alorma.travis.ui.presenter.credential.LoginPresenter;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.interactor.credentials.ActiveCredentialRepository;
import com.alorma.travisdk.interactor.credentials.ActiveCredentialRepositoryImpl;
import java.util.List;

public class LoginActivity extends BaseActivity implements LoginPresenter.LoginPresenterCallback,
    CredentialsAdapter.CredentialsAdapterCallback {

  @Bind(R.id.travis_url) EditText travisUrlEditText;
  @Bind(R.id.github_enterprise_url) EditText githubEnterpriseUrl;
  @Bind(R.id.github_token) EditText githubTokenEditText;
  @Bind(R.id.gh_login) View githubLoginButton;
  @Bind(R.id.gh_en_login) View githubEntepriseLoginButton;

  @Bind(R.id.login_form) View loginForm;

  private LoginPresenter presenter;
  private ProgressDialog loadingDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    presenter = new LoginPresenter(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.setCallback(this);
    presenter.start();
  }

  @Override
  protected void onStop() {
    presenter.stop();
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  @Override
  public void willLoadCredentials() {
    if (loadingDialog == null) {
      loadingDialog = new ProgressDialog(this);
      loadingDialog.setMessage("Loading credentials");
      loadingDialog.setIndeterminate(true);
      loadingDialog.show();
    }
  }

  @Override
  public void showLoginForm() {
    loginForm.setVisibility(View.VISIBLE);

    githubLoginButton.setOnClickListener(v -> githubLogin());
    githubEntepriseLoginButton.setOnClickListener(v -> githubEnterpriseLogin());
  }

  private void githubLogin() {
    String ghToken = githubTokenEditText.getText().toString();
    String url = travisUrlEditText.getText().toString();
    presenter.githubLogin(ghToken, url);
  }

  private void githubEnterpriseLogin() {
    String ghToken = githubTokenEditText.getText().toString();
    String url = travisUrlEditText.getText().toString();
    String enterpriseUrl = githubEnterpriseUrl.getText().toString();
    presenter.githubLoginEnterprise(ghToken, url, enterpriseUrl);
  }

  @Override
  public void showListCredentials(List<Credential> credentials) {
    loginForm.setVisibility(View.GONE);
  }

  @Override
  public void didLoadCredentials() {
    if (loadingDialog != null) {
      loadingDialog.dismiss();
      loadingDialog = null;
    }
  }

  @Override
  public void loadCredential(Credential credential) {
    onCredentialSelected(credential);
  }

  @Override
  public void onCredentialSelected(Credential credential) {
    ActiveCredentialRepository repository = ActiveCredentialRepositoryImpl.getInstance();
    repository.set(credential);
    Intent intent = new MainActivityIntentBuilder().build(this);
    startActivity(intent);
    finish();
  }
}
