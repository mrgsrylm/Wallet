package com.poywallet.poywalletbackend.service;

import com.poywallet.poywalletbackend.web.ResponseCommand;
import com.poywallet.poywalletbackend.web.api.auth.ResponseAuth;
import com.poywallet.poywalletbackend.web.api.auth.RequestJwtRefresh;
import com.poywallet.poywalletbackend.web.api.auth.RequestSignIn;
import com.poywallet.poywalletbackend.web.api.auth.RequestSignUp;

public interface AuthService {
    ResponseCommand signUp(RequestSignUp request);
    ResponseAuth signIn(RequestSignIn request);
}
