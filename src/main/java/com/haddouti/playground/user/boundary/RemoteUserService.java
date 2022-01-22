package com.haddouti.playground.user.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.quarkus.oidc.client.filter.OidcClientFilter;

/**
 * REST Client representation with a own OIDC token to interact with a remote backend service.
 * 
 * The backend service endpoint is defined in `application.yml` for the key/name as this client is named.
 * A fallback method and response is defined if the backend service is not accessible.
 * 
 * In case of @AccessToken a token propagation will be done. @OidcClientFilter
 * will retrieve a new one.
 * 
 */
@Path("")
@RegisterRestClient(configKey = "remote-user-client")
@OidcClientFilter
//@AccessToken
public interface RemoteUserService {

	@GET
	@Path("")
	@Fallback(ProtectedUserFallback.class)
	public String getProtectedUser();

	public static class ProtectedUserFallback implements FallbackHandler<String> {

		@Override
		public String handle(ExecutionContext context) {
			return "{'user': 'fallback'}";
		}

	}
}
