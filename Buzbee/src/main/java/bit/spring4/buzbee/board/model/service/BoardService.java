package bit.spring4.buzbee.board.model.service;

import java.security.Principal;
import bit.spring4.buzbee.model.Member;

public interface BoardService {
	Member selectByIdService(Principal principal);
	Member selectByIdService(String id);
}
