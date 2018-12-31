package bit.spring4.buzbee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bit.spring4.buzbee.util.TrendCrawler;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/")
	public ModelAndView home() {
		TrendCrawler tc = new TrendCrawler();
		
		return new ModelAndView("websocket-echo", "list", tc.getTIOBEs("item"));
	}
}
