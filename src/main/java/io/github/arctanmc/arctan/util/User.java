package io.github.arctanmc.arctan.util;

public class User {

	private Long id;
	private String tag;
	private String avatarUrl;

	public User() {}

	public User(Long id, String tag, String avatarUrl) {
		this.id = id;
		this.tag = tag;
		this.avatarUrl = avatarUrl;
	}

	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTag() {
		return tag;
	}

	public User setTag(String tag) {
		this.tag = tag;
		return this;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public User setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
		return this;
	}

}
