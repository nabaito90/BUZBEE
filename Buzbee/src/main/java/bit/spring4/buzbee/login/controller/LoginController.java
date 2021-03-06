package bit.spring4.buzbee.login.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@Secured("USER_ANONYMOUS")
	@RequestMapping("/login/join")
	public String join() {
		return "login/join";
	}
	
	@Secured("USER_ANONYMOUS")
	@RequestMapping("/denied.do")
	public String denied() {
		return "login/denied";
	}
}
