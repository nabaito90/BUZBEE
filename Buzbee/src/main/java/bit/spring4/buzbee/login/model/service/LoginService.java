package bit.spring4.buzbee.login.model.service;

import bit.spring4.buzbee.model.Member;

public interface LoginService {
	boolean insertService(Member member);
	boolean deleteService(String id);
	boolean updateService(Member member);
	Member selectByPhoneService(String phone);
	Member selectByEmailService(String email);
	Member selectByIdService(String id);
}
