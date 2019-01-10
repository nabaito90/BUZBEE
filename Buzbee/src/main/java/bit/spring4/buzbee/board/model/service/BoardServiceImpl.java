package bit.spring4.buzbee.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bit.spring4.buzbee.board.model.dao.BoardDAO;
import bit.spring4.buzbee.model.Board;
import bit.spring4.buzbee.model.MemberAndBoard;
import bit.spring4.buzbee.model.MemberEtc;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO dao;
	
	@Override
	public List<Board> selectByIdService(String id) {
		return dao.selectById(id);
	}
	
	@Override
	public List<MemberAndBoard> selectByNoService(long m_no) {
		return dao.selectByNo(m_no);
	}
	
	@Override
	public long insertService(long m_no, String b_content) {
		Board board = new Board();
		board.setM_no(m_no);
		board.setB_content(b_content);
		return dao.insert(board);
	}
	
	@Override
	public MemberEtc MemberEtcService(long m_no) {
		long buzzes = dao.countBuzzes(m_no);
		long follower = dao.countFollower(m_no);
		long following = dao.countFollowing(m_no);
		long likes = dao.countLikes(m_no);
		
		MemberEtc memberEtc = new MemberEtc(buzzes, follower, following, likes);
		return memberEtc;
	}
	
	// for Ajax
	@Override
	public boolean insertLikesService(long m_no, long b_no) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("b_no", b_no);
		map.put("m_no", m_no);
		return dao.insertLikes(map);
	}
}