package bit.spring4.buzbee.board.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import bit.spring4.buzbee.board.model.service.BoardService;
import bit.spring4.buzbee.model.Board;
import bit.spring4.buzbee.model.MemberAndBoard;
import bit.spring4.buzbee.model.MemberEtc;
import bit.spring4.buzbee.model.UserCustom;

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
		
		UserCustom loginDTO = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MemberEtc etc = service.MemberEtcService(loginDTO.getM_no());
		List<MemberAndBoard> buzzing = service.selectByNoService(loginDTO.getM_no());
		
		mv.setViewName("board/board");
		mv.addObject("buzzing", buzzing);
		mv.addObject("etc", etc);
		return mv;
	}
	
	@RequestMapping("/{id}")
	public ModelAndView board(@PathVariable String id) {
		ModelAndView mv = new ModelAndView();
		
		List<Board> buzzing = service.selectByIdService(id);
		
		mv.setViewName("profile/profile");
		mv.addObject("buzzing", buzzing);
		return mv;
	}
	
	// for Ajax
	
	@RequestMapping("/ajax/likes")
	@ResponseBody
	public boolean board(long b_no) {
		UserCustom loginDTO = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return service.insertLikesService(loginDTO.getM_no(), b_no);
	}
}
