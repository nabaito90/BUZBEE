package bit.spring4.buzbee.board.model.service;

import java.util.List;
import bit.spring4.buzbee.model.MemberAndBoard;

public interface BoardService {
	List<MemberAndBoard> selectByIdService(String id);
	boolean insertService(long m_no, String b_content);
}
