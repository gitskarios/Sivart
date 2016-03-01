package com.alorma.travis.ui.presenter.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import com.alorma.travis.R;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.login.CredentialsDataSource;
import java.util.ArrayList;
import java.util.List;

public class AccountsCredentialsDatasource implements CredentialsDataSource {

  private final String accountType;
  private Context context;
  private AccountManager accountManager;

  public AccountsCredentialsDatasource(Context context) {
    this.context = context;
    this.accountManager = AccountManager.get(context);
    accountType = context.getString(R.string.account_type);
  }

  @Override
  public List<Credential> getCredentials() {
    List<Credential> credentials = new ArrayList<>();
    Account[] accounts = accountManager.getAccountsByType(accountType);
    for (Account account : accounts) {
      credentials.add(mapCredential(account));
    }
    return credentials;
  }

  private Credential mapCredential(Account account) {
    Credential credential = new Credential();
    credential.setAvatar(accountManager.getUserData(account, "avatar"));
    credential.setGithubToken(accountManager.getUserData(account, "github_token"));
    credential.setToken(accountManager.getPassword(account));
    credential.setUrl(accountManager.getUserData(account, "travis_url"));
    credential.setName(account.name);
    return credential;
  }

  @Override
  public Credential authenticate(Credential credential) throws Exception {
    List<Credential> credentials = getCredentials();
    for (Credential check : credentials) {
      if (credential.getName().equals(check.getName())) {
        return credential;
      }
    }
    return null;
  }

  @Override
  public void saveCredential(Credential credential) {
    Bundle bundle = new Bundle();
    bundle.putString("avatar", credential.getAvatar());
    bundle.putString("github_token", credential.getGithubToken());
    bundle.putString("travis_url", credential.getUrl());
    Account account = new Account(credential.getName(), accountType);
    accountManager.addAccountExplicitly(account, credential.getToken(), bundle);
  }
}
