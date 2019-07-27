package kr.co.userinsight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RDEditorController {
	@RequestMapping(value = "/rd/edit", method = RequestMethod.GET)
	public String rdEdit(Model model) {
		return "team1/edit";
	}
}
