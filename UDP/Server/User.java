import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -633557141458398639L;
	private String userName;
	private String password;
	private String address;
	private String birthday;
	private String sex;
	private String description;
	
	
	public User(String userName, String password, String address, String birthday, String sex, String description) {
		super();
		this.userName = userName;
		this.password = password;
		this.address = address;
		this.birthday = birthday;
		this.sex = sex;
		this.description = description;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
