package ud.prog3.pr0405;

import java.util.ArrayList;
/**
 * clase donde guardaremos todos los usuarios
 * @author Suhar
 *
 */
public class UsuarioTwitter {
	
	private String id;
	private String screenName;
	private ArrayList<String> tags;
	private String avatar;
	private Long followersCount;
	private Long friendsCount;
	private String lang;
	private Long lastSeen;
	private String tweetId;
	private ArrayList<String> friends;
	
	public UsuarioTwitter(String id, String screenName, ArrayList<String> tags, String avatar, Long followersCount,
			Long friendsCount, String lang, Long lastSeen, String tweetId, ArrayList<String> friends) {
		super();
		this.id = id;
		this.screenName = screenName;
		this.tags = tags;
		this.avatar = avatar;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.lang = lang;
		this.lastSeen = lastSeen;
		this.tweetId = tweetId;
		this.friends = friends;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}

	public Long getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(Long friendsCount) {
		this.friendsCount = friendsCount;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Long getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Long lastSeen) {
		this.lastSeen = lastSeen;
	}

	public String getTweetId() {
		return tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	public ArrayList<String> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "UsuarioTwitter [id=" + id + ", screenName=" + screenName + ", tags=" + tags + ", avatar=" + avatar
				+ ", followersCount=" + followersCount + ", friendsCount=" + friendsCount + ", lang=" + lang
				+ ", lastSeen=" + lastSeen + ", tweetId=" + tweetId + ", friends=" + friends + "]";
	}



}
