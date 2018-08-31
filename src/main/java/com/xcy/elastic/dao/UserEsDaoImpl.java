package com.xcy.elastic.dao;

import com.xcy.elastic.User;
import io.searchbox.client.JestClient;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xuchunyang on 2018/8/31 11点12分
 */
@Repository
public class UserEsDaoImpl implements UserEsDao {

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void saveUserBatch(List<User> users) {

    }

    @Override
    public List<User> query(String searchContent) {
        return null;
    }
}
