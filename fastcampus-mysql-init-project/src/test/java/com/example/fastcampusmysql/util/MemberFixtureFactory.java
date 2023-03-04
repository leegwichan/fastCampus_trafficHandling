package com.example.fastcampusmysql.util;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import java.util.Random;

public class MemberFixtureFactory {

    private static Random RANDOM = new Random();

    public static Member create() {
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.setSeed(RANDOM.nextLong());
        return new EasyRandom(parameters).nextObject(Member.class);
    }
}
