package bit.spring4.buzbee.model;

import lombok.Data;

@Data
public class Member {
	private long m_no;
	private String m_id;
	private String m_name;
	private String m_email;
	private String m_phone;
	private String m_password;
	private long m_profile;
	private long m_header;
	private int m_delete;
	private String m_rdate;
	
	public Member() {}

	public Member(long m_no, String m_id, String m_name, String m_email, String m_phone, String m_password,
			long m_profile, long m_header, int m_delete, String m_rdate) {
		this.m_no = m_no;
		this.m_id = m_id;
		this.m_name = m_name;
		this.m_email = m_email;
		this.m_phone = m_phone;
		this.m_password = m_password;
		this.m_profile = m_profile;
		this.m_header = m_header;
		this.m_delete = m_delete;
		this.m_rdate = m_rdate;
	}
}
