package bit.spring4.buzbee.board.model.dao;

import bit.spring4.buzbee.model.Member;

public interface BoardDAO {
	Member selectById(String id);
}
