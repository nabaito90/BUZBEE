package bit.spring4.buzbee.login.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import bit.spring4.buzbee.model.Member;
import bit.spring4.buzbee.model.MemberAndAuth;

@Repository
public class LoginDAOImpl implements LoginDAO {
	@Autowired
	private SqlSession sqlSession;
	private String ns = "bit.spring4.buzbee.model.Member";
	
	@Override
	public boolean delete(String id) {
		return false;
	}
	
	@Override
	public boolean insert(Member member) {
		return false;
	}
	
	@Override
	public Member selectByEmail(String email) {
		return null;
	}
	
	@Override
	public Member selectById(String id) {
		return sqlSession.selectOne(ns + ".selectById", id);
	}
	
	@Override
	public Member selectByPhone(String phone) {
		return null;
	}
	
	@Override
	public boolean update(Member member) {
		return false;
	}
	
	@Override
	public long selectM_NOById(String id) {
		return sqlSession.selectOne(ns + ".selectM_NOById", id);
	}
}
