package com.xcy.elastic.dao;

import com.xcy.elastic.User;

import java.util.List;

/**
 * Created by xuchunyang on 2018/8/31 11点09分
 */
public interface UserEsDao {

    public void saveUser(User user);

    public void saveUserBatch(List<User> users);

    public List<User> query(String searchContent);

}
