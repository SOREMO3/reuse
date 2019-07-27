package kr.co.userinsight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SearchHistoryController {


    @RequestMapping(value = "/rd/search_history", method = RequestMethod.GET)
    public String rdSearch() {

        return "team3/rd-search-history";
    }
}
