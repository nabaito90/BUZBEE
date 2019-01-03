package bit.spring4.buzbee.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@RequestMapping("")
	public String board() {
		return "board/board";
	}
}
