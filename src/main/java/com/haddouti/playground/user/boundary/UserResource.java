package com.haddouti.playground.user.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("v1/users")
public class UserResource {

	private static final Logger LOG = Logger.getLogger(UserResource.class);

	@Inject
	@RestClient
	RemoteUserService remoteClient;

	@Inject
	JsonWebToken accessToken;

	@GET
	@Path("remote")
	public String getRemoteUserProfile() {

		LOG.infof("getRemoteUserProfile(): jwt.accessToken=%s", accessToken);

		return remoteClient.getProtectedUser();
	}
}
