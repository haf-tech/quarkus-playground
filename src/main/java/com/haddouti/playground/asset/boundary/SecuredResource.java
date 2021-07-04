package com.haddouti.playground.asset.boundary;

import java.time.LocalDate;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import com.haddouti.playground.user.boundary.UserResource;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

/**
 * Secured REST endpoint
 * 
 * Only *authenticated* user can access this REST endpoint. If the REST method
 * is annotated with @RolesAllowed defines which role is expected to access the
 * method. If nothing is defined is only authentication enough.
 * 
 */
@Path("/secured")
@Authenticated
public class SecuredResource {

	private static final Logger LOG = Logger.getLogger(SecuredResource.class);

	@Inject
	SecurityIdentity securityIdentity;

	@Inject
	JsonWebToken accessToken;

	@Inject
	UserResource userService;

	@GET
	@Path("date")
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
//	@RolesAllowed("user")
	public String getDate(@Context SecurityContext sc) {

		LOG.debugf("getDate(): isSecure: %s, authSchema: %s", sc.isSecure(), sc.getAuthenticationScheme());
		LOG.infof("getDate(): principle.name=%s, allAttributes=%s", securityIdentity.getPrincipal().getName(),
				securityIdentity.getAttributes());
		LOG.debugf("getDate(): jwt.accessToken=%s", accessToken);

		return LocalDate.now().toString();
	}

	@GET
	@Path("user")
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
//	@RolesAllowed("user")
	public String getUserWithTokenDelegation(@Context SecurityContext sc) {

		LOG.debugf("getUserWithTokenDelegation(): isSecure: %s, authSchema: %s", sc.isSecure(),
				sc.getAuthenticationScheme());
		LOG.infof("getUserWithTokenDelegation(): principle.name=%s, allAttributes=%s",
				securityIdentity.getPrincipal().getName(), securityIdentity.getAttributes());
		LOG.debugf("getUserWithTokenDelegation(): jwt.accessToken=%s", accessToken);

		LOG.debug("getUserWithTokenDelegation(): calling remoteUserService...");
		String user = userService.getRemoteUserProfile();
		LOG.debugf("getUserWithTokenDelegation(): ...remoteUserService done. RemoteUser=%s", user);

		return user;
	}

	@GET
	@Path("dateForManager")
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	@RolesAllowed({ "manager", "Manager" })
	public String getDateTime(@Context SecurityContext sc) {

		LOG.debugf("getDateTime(): isSecure: %s, authSchema: %s", sc.isSecure(), sc.getAuthenticationScheme());
		LOG.infof("getDateTime(): principle.name=%s, allAttributes=%s", securityIdentity.getPrincipal().getName(),
				securityIdentity.getAttributes());

		return LocalDate.now().toString();
	}
}
