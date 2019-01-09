package bit.spring4.buzbee.login.model.dao;

import bit.spring4.buzbee.model.Member;
import bit.spring4.buzbee.model.MemberAndAuth;

public interface LoginDAO {
	boolean insert(Member member);
	boolean delete(String id);
	boolean update(Member member);
	Member selectByPhone(String phone);
	Member selectByEmail(String email);
	Member selectById(String id);
	long selectM_NOById(String id);
}
