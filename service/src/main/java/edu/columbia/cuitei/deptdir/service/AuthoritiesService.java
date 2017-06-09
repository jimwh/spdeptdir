package edu.columbia.cuitei.deptdir.service;

import edu.columbia.cuitei.deptdir.domain.AuthoritiesRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthoritiesService {
    @Resource(name="authoritiesRepository")
    private AuthoritiesRepository authoritiesRepository;

}
