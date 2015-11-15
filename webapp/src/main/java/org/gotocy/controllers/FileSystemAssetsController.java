package org.gotocy.controllers;

import org.gotocy.beans.AssetsManager;
import org.gotocy.config.Profiles;
import org.gotocy.controllers.exceptions.DomainObjectNotFoundException;
import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.PanoXml;
import org.gotocy.domain.PdfFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ifedorenkov
 */
@Controller
@Profile(Profiles.LOCAL_DEV)
public class FileSystemAssetsController {

	@Autowired
	private AssetsManager assetsManager;

	@RequestMapping("/fs_assets")
	public @ResponseBody byte[] getAsset(@RequestParam String key) {
		Asset asset;

		if (key.endsWith("jpg") || key.endsWith("jpeg")) {
			asset = new Image(key);
		} else if (key.endsWith("pdf")) {
			asset = new PdfFile(key);
		} else if (key.endsWith("xml")) {
			asset = new PanoXml(key);
		} else {
			throw new DomainObjectNotFoundException();
		}

		return assetsManager.loadUnderlyingObject(asset).getBytes();
	}

}
