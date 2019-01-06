package bit.spring4.buzbee.board.model.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bit.spring4.buzbee.board.model.dao.BoardDAO;
import bit.spring4.buzbee.model.Member;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO dao;
	
	@Override
	public Member selectByIdService(Principal principal) {
		String id = principal.getName();
		return dao.selectById(id);
	}
	
	@Override
	public Member selectByIdService(String id) {
		return dao.selectById(id);
	}
}
