package bit.spring4.buzbee.login.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import bit.spring4.buzbee.model.Member;

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
	public Member selectByNo(long m_no) {
		return sqlSession.selectOne(ns + "selectByNo", m_no);
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
		try {
			return sqlSession.selectOne(ns + ".selectM_NOById", id);
		} catch (Exception e) {
			return -1;
		}
	}
}
