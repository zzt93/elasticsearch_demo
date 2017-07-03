package com.superid.query.user.role;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zzt
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepoImplTest {

    @Autowired
    private RoleRepo roleRepo;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findRoleExcept() throws Exception {
        System.out.println(roleRepo.findRoleExcept(123L, "架构", new PageRequest(0, 10)).getContent());
        System.out.println(roleRepo.findRoleExcept(234L, "前端", new PageRequest(0, 10)).getContent());
    }

    @Test
    public void findRoleInterAlliance() throws Exception {
        System.out.println(roleRepo.findRoleInterAlliance("前端", new PageRequest(0, 10)).getContent());
        System.out.println(roleRepo.findRoleInterAlliance("架构", new PageRequest(0, 10)).getContent());
    }

}