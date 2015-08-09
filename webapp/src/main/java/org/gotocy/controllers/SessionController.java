package org.gotocy.controllers;

import org.gotocy.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@Controller
public class SessionController {

	@Autowired
	private SecurityProperties securityProperties;

	@RequestMapping(value = "/session/new", method = RequestMethod.GET)
	public String _new() {
		return "session/new";
	}

	@RequestMapping(value = "/session", method = RequestMethod.POST)
	public String create(HttpSession session, @RequestParam String login, @RequestParam String password) {
		if (Objects.equals(securityProperties.getLogin(), login) &&
			Objects.equals(securityProperties.getPassword(), password))
		{
			session.setAttribute(SecurityProperties.SESSION_KEY, Boolean.TRUE);
		}

		return "redirect:/";
	}

}
