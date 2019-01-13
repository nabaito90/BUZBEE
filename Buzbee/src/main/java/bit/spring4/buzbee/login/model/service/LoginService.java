package bit.spring4.buzbee.login.model.service;

import java.security.Principal;

import bit.spring4.buzbee.model.Member;

public interface LoginService {
	boolean insertService(Member member);
	boolean deleteService(String id);
	boolean updateService(Member member);
	Member selectByNoService(long m_no);
	Member selectByPhoneService(String phone);
	Member selectByEmailService(String email);
	Member selectByIdService(String id);
	Member selectByIdService(Principal principal);
	long selectM_NOByIdService(String id);
}
