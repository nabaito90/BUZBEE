package bit.spring4.buzbee.util;

import java.util.*;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import bit.spring4.buzbee.model.MemberAndAuth;
import bit.spring4.buzbee.model.UserCustom;

public class CustomUserDetailService implements UserDetailsService {
    // DB �� ���� ���������� �������� ���� ���Ե� ����
    @Autowired
    private SqlSession sqlSession;

    // ��ť��Ƽ�� ���� �� �Ķ���͸� �߰��ϰ� ���� ��, �Ʒ� ���
    //  ��������: Controller ���� Auth�� ������ ��, UserCustom ���� �޾ƾ� ��.
    //  ��) (���� ��) @AuthenticationPrincipal User user => (���� ��) @AuthenticationPrincipal UserCustom user
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;
    
    public CustomUserDetailService() {}
    	  
    public CustomUserDetailService(SqlSessionTemplate sqlSession) {
    	this.sqlSession = sqlSession;
	}
  
	@Override
	public UserDetails loadUserByUsername(String m_id)
	   throws UsernameNotFoundException {
		MemberAndAuth member = sqlSession.selectOne("bit.spring4.buzbee.model.Member.customLogin", m_id);
	    if(member == null) throw new UsernameNotFoundException(m_id);
	    List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
	    gas.add(new SimpleGrantedAuthority(member.getAuthority()));
	    return new UserCustom(member.getUsername()
                , member.getPassword()
                , enabled, accountNonExpired, credentialsNonExpired, accountNonLocked
                , gas
                , member.getM_no()
                , member.getM_name()
                , member.getM_email()
                , member.getM_phone()
                , member.getM_profile()
                , member.getM_header()
                , member.getM_delete()
                , member.getM_rdate()
        );
	 }
}
