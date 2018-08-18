package com.zhp.cloud.login.dao.impl;

import org.springframework.stereotype.Service;

import com.zhp.cloud.login.dao.LoginDao;
import com.zhp.cloud.util.RedisTools;
import com.zhp.cloud.util.constants.Constants;

@Service("loginDaoImpl")
public class LoginDaoImpl implements LoginDao {

	@Override
	public boolean getLoginInfo(String userName, String password) throws Exception {
		boolean flag = false;
		String userInfo = RedisTools.get(userName);
		if (userInfo!=null) {
			String[] split = userInfo.split("\\"+Constants.STRING_SEPARATOR);
			if (password.equals(split[0])) {
				flag=true;
			}
		}
		return flag;
	}

}
