package bit.spring4.buzbee.board.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bit.spring4.buzbee.board.model.dao.BoardDAO;
import bit.spring4.buzbee.model.Board;
import bit.spring4.buzbee.model.MemberAndBoard;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO dao;
	
	@Override
	public List<MemberAndBoard> selectByIdService(String id) {
		return dao.selectById(id);
	}
	
	@Override
	public boolean insertService(long m_no, String b_content) {
		Board board = new Board();
		board.setM_no(m_no);
		board.setB_content(b_content);
		return dao.insert(board);
	}
}
