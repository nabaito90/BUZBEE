package bit.spring4.buzbee.board.model.dao;

import java.util.List;
import java.util.Map;
import bit.spring4.buzbee.model.Board;
import bit.spring4.buzbee.model.MemberAndBoard;

public interface BoardDAO {
	List<Board> selectById(String id);
	List<MemberAndBoard> selectByNo(long m_no);
	long insert(Board board);
	long countBuzzes(long m_no);
	long countFollower(long m_no);
	long countFollowing(long m_no);
	long countLikes(long m_no);
	List<String> followerOnline(long m_no);
	
	// for Ajax
	boolean insertLikes(Map<String, Long> map);
}
