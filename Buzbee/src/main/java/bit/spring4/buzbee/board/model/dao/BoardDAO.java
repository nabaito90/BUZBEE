package bit.spring4.buzbee.board.model.dao;

import java.util.List;
import bit.spring4.buzbee.model.Board;
import bit.spring4.buzbee.model.MemberAndBoard;

public interface BoardDAO {
	List<MemberAndBoard> selectById(String id);
	boolean insert(Board board);
}
