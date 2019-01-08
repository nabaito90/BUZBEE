package bit.spring4.buzbee.board.model.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import bit.spring4.buzbee.model.Board;
import bit.spring4.buzbee.model.MemberAndBoard;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;
	private String ns_board = "bit.spring4.buzbee.model.Board";
	
	@Override
	public List<MemberAndBoard> selectById(String id) {
		return sqlSession.selectList(ns_board + ".selectById", id);
	}
	
	@Override
	public boolean insert(Board board) {
		int i = sqlSession.insert(ns_board + ".insert", board);
		return i > 0 ? true : false;
	}
}
