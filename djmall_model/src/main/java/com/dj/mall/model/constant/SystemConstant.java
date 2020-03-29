package com.dj.mall.model.constant;

public class SystemConstant {
	/**
	 * 系统正在维护...请稍后再试
	 */
	public static final String ERROR = "系统正在维护...请稍后再试";

	/**
	 * 用户名或密码不得为空
	 */
	public static final String LOGIN_VERIFY = "用户名或密码不得为空";

	/**
	 * 用户名或密码错误
	 */
	public static final String LOGIN_ERROR = "用户名或密码错误";

	/**
	 * 用户名不得为空
	 */
    public static final String USSERNAME_EMPTY = "用户名不得为空";

	/**
	 * 该用户已被删除
	 */
	public static final String DEL = "该用户已被删除";

	/**
	 * 该用户未被激活，请联系管理员激活
	 */
	public static final String NOT_ACTIVE = "该用户未被激活，请联系管理员激活";

	/**
	 * 正常显示
	 */
    public static final Integer NOT_DEL = 1;

	/**
	 * 激活邮件主题
	 */
	public static final String SUBJECT = "验证邮箱激活";

	/**
	 * 正常激活 1
	 */
    public static final Integer ACTIVE_SUCCESS = 1;

	/**
	 * 手机号不存在
	 */
	public static final String NOT_PHONE = "手机号未注册!";

	/**
	 * 验证码不正确
	 */
	public static final String NOT_CODE = "验证码不正确!";

	/**
	 * 验证码已失效
	 */
	public static final String FALSE_CODE = "验证码已失效!";

	/**
	 * 菜单展示
	 */
    public static final Integer MENU_SHOW = 1;

	/**
	 * 正常显示
	 */
	public static final Integer NOT_SHOW = 1;

	/**
	 * 已被激活
	 */
	public static final String YES_ACTIVE = "已被激活";
}
