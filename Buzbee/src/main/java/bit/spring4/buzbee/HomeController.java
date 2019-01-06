/*
package bit.spring4.buzbee;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import bit.spring4.buzbee.util.TrendCrawler;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/")
	public ModelAndView home(Principal principal) {
		ModelAndView mv = new ModelAndView();
		if(principal == null) {
			mv.setViewName("redirect:login");
			return mv;
		}
		
		mv.setViewName("board/board");
		mv.addObject("list", new TrendCrawler().getTIOBEs("item"));
		return mv;
	}
}
*/