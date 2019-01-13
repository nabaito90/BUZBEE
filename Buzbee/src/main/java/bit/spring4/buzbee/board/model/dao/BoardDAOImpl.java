package bit.spring4.buzbee.board.model.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import bit.spring4.buzbee.model.Board;
import bit.spring4.buzbee.model.MemberAndBoard;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;
	private String ns = "bit.spring4.buzbee.model.Board";
	
	@Override
	public List<MemberAndBoard> selectByIdMember(String m_id) {
		return sqlSession.selectList(ns + ".selectByIdMember", m_id);
	}
	
	@Override
	public List<MemberAndBoard> selectById(String m_id) {
		return sqlSession.selectList(ns + ".selectById", m_id);
	}
	
	@Override
	public List<MemberAndBoard> selectByNo(long m_no) {
		return sqlSession.selectList(ns + ".selectByNo", m_no);
	}
	
	@Override
	public long insert(Board board) {
		sqlSession.insert(ns + ".insert", board);
		return sqlSession.selectOne(ns + ".maxBoardNo", board.getM_no());
	}
	
	@Override
	public long countBuzzes(long m_no) {
		return sqlSession.selectOne(ns + ".countBuzzes", m_no);
	}
	
	@Override
	public long countFollower(long m_no) {
		return sqlSession.selectOne(ns + ".countFollower", m_no);
	}
	
	@Override
	public long countFollowing(long m_no) {
		return sqlSession.selectOne(ns + ".countFollowing", m_no);
	}
	
	@Override
	public long countLikes(long m_no) {
		return sqlSession.selectOne(ns + ".countLikes", m_no);
	}
	
	@Override
	public List<String> followerOnline(long m_no) {
		return sqlSession.selectList(ns + ".followerOnline", m_no);
	}
	
	// for Ajax
	@Override
	public boolean insertLikes(Map<String, Long> map) {
		try {
			sqlSession.insert(ns + ".insertLikes", map);
			sqlSession.update(ns + ".plusLikes", map);
			return true;
		} catch (Exception e) {
			sqlSession.insert(ns + ".deleteLikes", map);
			sqlSession.update(ns + ".minusLikes", map);
			return false;
		}
	}
	
	@Override
	public MemberAndBoard content(long b_no) {
		return sqlSession.selectOne(ns + ".content", b_no);
	}
}
