package org.gotocy.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Asset could be an image,a pano xml file, etc.
 *
 * @author ifedorenkov
 */
@Entity
public class Asset extends BaseEntity {

	private String assetKey;

	@Enumerated(EnumType.STRING)
	private AssetType assetType;

	public String getAssetKey() {
		return assetKey;
	}

	public void setAssetKey(String assetKey) {
		this.assetKey = assetKey;
	}

	public AssetType getAssetType() {
		return assetType;
	}

	public void setAssetType(AssetType assetType) {
		this.assetType = assetType;
	}

}
