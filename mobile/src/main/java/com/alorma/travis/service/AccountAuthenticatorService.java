package com.alorma.travis.service;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountAuthenticatorService extends Service {
  @Override
  public IBinder onBind(Intent intent) {
    IBinder ret = null;

    if (intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
      ret = new AccountsBinder(this).getIBinder();
    }

    return ret;
  }
}
