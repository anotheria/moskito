package net.anotheria.moskito.webui.auth.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

public class AuthApiFactory implements APIFactory<AuthApi>, ServiceFactory<AuthApi> {
    @Override
    public AuthApi createAPI() {
        return new AuthApiImpl();
    }

    @Override
    public AuthApi create() {
        APIFinder.addAPIFactory(AuthApi.class, this);
        return APIFinder.findAPI(AuthApi.class);
    }
}