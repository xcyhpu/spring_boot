package com.xcy.dao;

import com.xcy.domain.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xuchunyang on 2018/8/29 15点21分
 */
public interface UserDao extends MongoRepository<UserMongo, String> {

}
