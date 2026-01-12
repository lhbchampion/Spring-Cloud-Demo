package com.lhb.auth.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86131
 * @version 1.0
 * @description GitHub用户表
 * @date 2026/1/9 下午11:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GitHubUserDO {
    private Long id;
    private Long githubId;
    private String username;
    private String email;
    private String role;
}
