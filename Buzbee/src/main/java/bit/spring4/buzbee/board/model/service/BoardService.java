package bit.spring4.buzbee.board.model.service;

import java.util.List;
import bit.spring4.buzbee.model.Board;
import bit.spring4.buzbee.model.MemberAndBoard;
import bit.spring4.buzbee.model.MemberEtc;

public interface BoardService {
	List<Board> selectByIdService(String id);
	List<MemberAndBoard> selectByNoService(long m_no);
	long insertService(long m_no, String b_content);
	MemberEtc MemberEtcService(long m_no);
	
	// for Ajax
	boolean insertLikesService(long m_no, long b_no);
}
