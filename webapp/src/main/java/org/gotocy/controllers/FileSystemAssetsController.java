package org.gotocy.controllers;

import org.gotocy.config.Profiles;
import org.gotocy.controllers.exceptions.NotFoundException;
import org.gotocy.controllers.master.MasterPropertiesController;
import org.gotocy.domain.Asset;
import org.gotocy.domain.Image;
import org.gotocy.domain.PanoXml;
import org.gotocy.domain.PdfFile;
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
import java.util.function.Supplier;

/**
 * @author ifedorenkov
 */
@Controller
@Profile(Profiles.LOCAL_DEV)
public class FileSystemAssetsController {

	@Autowired
	private AssetsManager assetsManager;

	@Autowired
	private MasterPropertiesController masterPropertiesController;

	@RequestMapping("/fs_assets")
	public @ResponseBody byte[] getAsset(@RequestParam String key) {

		Supplier<? extends Asset> factory;

		if (key.endsWith("jpg") || key.endsWith("jpeg")) {
			factory = Image::new;
		} else if (key.endsWith("pdf")) {
			factory = PdfFile::new;
		} else if (key.endsWith("xml")) {
			factory = PanoXml::new;
		} else {
			throw new NotFoundException();
		}

		return assetsManager.getAsset(factory, key).orElseThrow(NotFoundException::new).getBytes();
	}

	@RequestMapping(value = "/properties.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<PropertyDto> indexJson() {
		return masterPropertiesController.indexJson();
	}

}
