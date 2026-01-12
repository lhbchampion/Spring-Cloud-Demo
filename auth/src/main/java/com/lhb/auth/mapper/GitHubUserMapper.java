package com.lhb.auth.mapper;

import com.lhb.auth.pojo.DO.GitHubUserDO;
import com.lhb.auth.pojo.DO.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GitHubUserMapper {

    @Select("SELECT * FROM github_user WHERE github_id = #{githubId}")
    GitHubUserDO findByGithubId(Long id);
    @Insert("INSERT INTO github_user(username, email, github_id, role) VALUES(#{username}, #{email}, #{githubId}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(GitHubUserDO user);
}
