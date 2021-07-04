package com.haddouti.playground.asset.boundary;

import java.util.ArrayList;
import java.util.List;

import com.haddouti.playground.asset.entity.Asset;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class AssetResponse {

	List<Asset> assets = new ArrayList<>();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AssetResponse [assets=").append(assets).append("]");
		return builder.toString();
	}

}
