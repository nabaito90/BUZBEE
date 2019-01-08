package bit.spring4.buzbee.board.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import bit.spring4.buzbee.board.model.service.BoardService;
import bit.spring4.buzbee.model.Member;
import bit.spring4.buzbee.util.TrendCrawler;

@Controller
public class BoardController {
	@Autowired
	private BoardService service;
	
	@RequestMapping("/")
	public ModelAndView board(Principal principal) {
		ModelAndView mv = new ModelAndView();
		
		if(principal == null) {
			mv.setViewName("redirect:login");
			return mv;
		}
		
		Member member = service.selectByIdService(principal);
		mv.setViewName("board/board");
		mv.addObject("member", member);
		mv.addObject("list", new TrendCrawler().getTIOBEs("item"));
		return mv;
	}
	
	@RequestMapping("/{id}")
	public ModelAndView board(@PathVariable String id) {
		ModelAndView mv = new ModelAndView();
				
		Member member = service.selectByIdService(id);
		if(member == null) {
			mv.setViewName("redirect:/");
			return mv;
		}
		
		mv.setViewName("board/board");
		mv.addObject("member", member);
		mv.addObject("list", new TrendCrawler().getTIOBEs("item"));
		return mv;
	}
}
