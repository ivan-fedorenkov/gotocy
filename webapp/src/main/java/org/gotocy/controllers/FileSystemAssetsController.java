package org.gotocy.controllers;

import org.gotocy.config.Profiles;
import org.gotocy.controllers.exceptions.NotFoundException;
import org.gotocy.controllers.master.MasterPropertiesController;
import org.gotocy.domain.Asset;
import org.gotocy.dto.PropertyDto;
import org.gotocy.service.AssetsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ifedorenkov
 */
@Controller
@Profile(Profiles.DEV)
public class FileSystemAssetsController {

	@Autowired
	private AssetsManager assetsManager;

	@Autowired
	private MasterPropertiesController masterPropertiesController;

	@RequestMapping("/fs_assets")
	public @ResponseBody byte[] getAsset(@RequestParam String key) {
		return assetsManager.getAsset(DevAsset::new, key).orElseThrow(NotFoundException::new).getBytes();
	}

	@RequestMapping(value = "/properties.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<PropertyDto> indexJson() {
		return masterPropertiesController.indexJson();
	}

	private static class DevAsset extends Asset {
		@Override
		public String getContentType() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getFileExtension() {
			throw new UnsupportedOperationException();
		}
	}

}
