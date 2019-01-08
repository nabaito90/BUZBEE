package bit.spring4.buzbee.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import bit.spring4.buzbee.login.model.dao.LoginDAO;
import bit.spring4.buzbee.model.Member;

public class LoginHandler implements AuthenticationSuccessHandler {
	@Autowired
	private LoginDAO dao;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Member loginDTO = dao.selectById(authentication.getName());
		HttpSession session = request.getSession();
		session.setAttribute("loginInfo", loginDTO);
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, "/");
	}
}
