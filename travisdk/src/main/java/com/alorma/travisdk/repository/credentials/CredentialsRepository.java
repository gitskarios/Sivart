package com.alorma.travisdk.repository.credentials;

import com.alorma.travisdk.bean.utils.Credential;
import java.util.List;

public interface CredentialsRepository {
  List<Credential> getCredentials();
}
