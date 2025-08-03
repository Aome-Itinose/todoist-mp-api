package com.aome.todoist_mp_api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MpServiceFacadeImpl implements MpServiceFacade {

    @Override
    public int currentMp() {
        log.debug("Getting current MP value");
        int mp = 0;
        log.info("Current MP value: {}", mp);
        return mp;
    }
}
