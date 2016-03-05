package com.alorma.travis.ui.presenter.accounts;

import com.alorma.travis.ui.presenter.utils.RetrofitWrapper;
import com.alorma.travisdk.bean.request.GithubTokenRequest;
import com.alorma.travisdk.bean.response.AccessTokenResponse;
import com.alorma.travisdk.bean.utils.Credential;
import com.alorma.travisdk.datasource.login.CredentialsDataSource;
import com.alorma.travisdk.datasource.login.cloud.AuthService;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;

public class ApiCredentialsDataSource implements CredentialsDataSource {

  private RetrofitWrapper retrofitWrapper;

  public ApiCredentialsDataSource(RetrofitWrapper retrofitWrapper) {

    this.retrofitWrapper = retrofitWrapper;
  }

  @Override
  public List<Credential> getCredentials() {
    return Collections.emptyList();
  }

  @Override
  public Observable<List<Credential>> getCredentialsObs() {
    return Observable.empty();
  }

  @Override
  public Credential authenticate(Credential credential) throws Exception {
    GithubTokenRequest request = new GithubTokenRequest();
    request.githubToken = credential.getGithubToken();
    Call<AccessTokenResponse> call = retrofitWrapper.getRetrofit(credential.getTravisUrl())
        .create(AuthService.class)
        .githubToken(request);

    Response<AccessTokenResponse> response = call.execute();
    if (response.isSuccess()) {
      AccessTokenResponse token = response.body();
      credential.setToken(token.accessToken);
      return credential;
    } else {
      throw new Exception(response.errorBody().string());
    }
  }

  @Override
  public void saveCredential(Credential credential) {

  }
}
