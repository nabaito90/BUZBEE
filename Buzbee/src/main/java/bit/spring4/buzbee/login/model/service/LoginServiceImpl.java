package bit.spring4.buzbee.login.model.service;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bit.spring4.buzbee.login.model.dao.LoginDAO;
import bit.spring4.buzbee.model.Member;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginDAO dao;
	
	@Override
	public boolean deleteService(String id) {
		return false;
	}
	
	@Override
	public boolean insertService(Member member) {
		return false;
	}
	
	@Override
	public Member selectByNoService(long m_no) {
		return dao.selectByNo(m_no);
	}
	
	@Override
	public Member selectByEmailService(String email) {
		return null;
	}
	
	@Override
	public Member selectByIdService(String id) {
		return dao.selectById(id);
	}
	
	@Override
	public Member selectByPhoneService(String phone) {
		return null;
	}
	
	@Override
	public boolean updateService(Member member) {
		return false;
	}
	
	@Override
	public Member selectByIdService(Principal principal) {
		String id = principal.getName();
		return dao.selectById(id);
	}
	
	@Override
	public long selectM_NOByIdService(String id) {
		return dao.selectM_NOById(id);
	}
}
