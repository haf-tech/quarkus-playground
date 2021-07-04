package com.haddouti.playground.asset.boundary;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;

import com.haddouti.playground.asset.entity.Asset;

import io.quarkus.security.identity.SecurityIdentity;

@Path("v1/assets")
public class AssetResource {

	private static final Logger LOG = Logger.getLogger(AssetResource.class);

	@Inject
	SecurityIdentity securityIdentity;

//	@Inject
//	JsonWebToken jwt;

	@Path("")
	@GET
	@PermitAll
	@NoCache
	public AssetResponse getAll(@Context SecurityContext sc) {
		LOG.info("getAll()");
		LOG.debugf("getAll(): isSecure: %s, authSchema: %s", sc.isSecure(), sc.getAuthenticationScheme());

		Asset asset = new Asset();
		asset.id = 1L;
		asset.name = "Asset1";

		AssetResponse ar = new AssetResponse();
		ar.assets.add(asset);

		LOG.infof("getAll(): assetResponse=%s", ar);
		return ar;
	}

	@POST
	@RolesAllowed("manager")
	public AssetResponse update(Asset asset, @Context SecurityContext sc) {

		LOG.debugf("update(): isSecure: %s, authSchema: %s", sc.isSecure(), sc.getAuthenticationScheme());
		LOG.infof("update(): principle.name=%s", securityIdentity.getPrincipal().getName());
		return new AssetResponse();
	}
}
