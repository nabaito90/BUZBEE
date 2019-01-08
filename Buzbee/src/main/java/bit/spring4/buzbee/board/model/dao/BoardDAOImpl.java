package bit.spring4.buzbee.board.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bit.spring4.buzbee.model.Member;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;
	private String ns = "bit.spring4.buzbee.model.Member";
	
	@Override
	public Member selectById(String id) {
		return sqlSession.selectOne(ns + ".selectById", id);
	}
}
