package com.lhb.auth.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GitHubUser {
    private Long id;
    private String login;
    private String avatar_url;
    private String email;

}
