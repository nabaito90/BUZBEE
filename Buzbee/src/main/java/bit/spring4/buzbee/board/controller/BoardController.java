package bit.spring4.buzbee.board.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import bit.spring4.buzbee.board.model.service.BoardService;
import bit.spring4.buzbee.login.model.service.LoginService;
import bit.spring4.buzbee.model.Member;
import bit.spring4.buzbee.model.MemberAndBoard;
import bit.spring4.buzbee.util.TrendCrawler;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/")
	public ModelAndView board(Principal principal) {
		ModelAndView mv = new ModelAndView();
		
		if(principal == null) {
			mv.setViewName("redirect:login");
			return mv;
		}
		
		List<MemberAndBoard> buzzing = boardService.selectByIdService(principal.getName()); 
		Member member = loginService.selectByIdService(principal);
		mv.setViewName("board/board");
		mv.addObject("member", member);
		mv.addObject("buzzing", buzzing);
		mv.addObject("list", new TrendCrawler().getTIOBEs("item"));
		return mv;
	}
	
	@RequestMapping("/{id}")
	public ModelAndView board(@PathVariable String id) {
		ModelAndView mv = new ModelAndView();
				
		Member member = loginService.selectByIdService(id);
		if(member == null) {
			mv.setViewName("redirect:/");
			return mv;
		}
		
		List<MemberAndBoard> buzzing = boardService.selectByIdService(id); 
		mv.setViewName("profile/profile");
		mv.addObject("member", member);
		mv.addObject("buzzing", buzzing);
		mv.addObject("list", new TrendCrawler().getTIOBEs("item"));
		return mv;
	}
}
