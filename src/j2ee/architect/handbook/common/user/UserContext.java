package j2ee.architect.handbook.common.user;

import j2ee.architect.handbook.common.BaseVO;

import java.util.Set;

/**
 * User context that makes needed information about the user available to all classes that need it.
 * @author D. Ashmore
 *
 */
public class UserContext {
	private static ThreadLocal<UserContext> currentUserContext = new ThreadLocal<UserContext>();
	
	private UserVO currentUser = null;
	private UserVO emulatedUser = null;
	
	public static UserContext getCurrent() {
		return currentUserContext.get();
	}
	
	public static void clear() {
		currentUserContext.remove();
	}
	
	public UserContext(UserVO loggedInUser) {
		currentUserContext.set(this);
		this.currentUser = loggedInUser;
	}
	
	/**
	 * Provides current user information.
	 * @return current user information
	 */
	public UserVO getUser() {
		if (this.emulatedUser != null) {
			return this.emulatedUser;
		}
		return this.currentUser;
	}
	
	/**
	 * Will be provided by getUser() as the currently executing user on request.
	 * @param emulatedUser
	 */
	public void setEmulatedUser(UserVO emulatedUser) {
		this.emulatedUser = emulatedUser;
	}

	public static class UserVO extends BaseVO {
		private static final long serialVersionUID = -3910422863255935980L;
		
		private String userId;	
		private String firstName;
		private String lastName;
		private String middleInitial;
		private String emailAddress;
		private String title;
		private Set<String> roleSet;

		public Set<String> getRoleSet() {
			return roleSet;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getMiddleInitial() {
			return middleInitial;
		}
		public void setMiddleInitial(String middleInitial) {
			this.middleInitial = middleInitial;
		}
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
	}
}
