package com.yjy.test.report;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/7/7.
 */
public class StringTest {


    private  static final  Logger logger= LoggerFactory.getLogger(StringTest.class);
    public static void main(String[] args) {
        String[] fuck_sf_sfs = StringUtils.splitByCharacterTypeCamelCase("fuckYouNiMei");
        logger.info(fuck_sf_sfs[0]);
        logger.info(fuck_sf_sfs[1]);
        logger.info(fuck_sf_sfs[2]);
        logger.info(fuck_sf_sfs[3]);
        logger.info(StringUtils.capitalize("fuckuouniMei"));
    }
}
