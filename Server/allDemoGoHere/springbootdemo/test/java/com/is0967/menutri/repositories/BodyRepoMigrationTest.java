//package com.is0967.menutri.repositories;
//
//import com.is0967.menutri.config.Config;
//import com.is0967.menutri.entities.Body;
//import com.is0967.menutri.entities.BodyCondition;
//import com.is0967.menutri.services.BodyService;
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * Created by phuctran93 on 4/7/2017.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.NONE)
//@ContextConfiguration(classes = Config.class)
//public class BodyRepoMigrationTest
//{
//
//    private static final Logger logger = Logger.getLogger(BodyRepoMigrationTest.class);
//
//    @Autowired
//    private BodyRepo bodyRepo;
//    @Autowired
//    private BodyService bodyService;
//    @Autowired
//    private BodyConditionRepo bodyConditionRepo;
//
//    @Test
//    public void migration()
//    {
//        Iterable<Body> bodyList = bodyRepo.findAll();
//        for (Body body : bodyList) {
//            logger.info(body);
//            if (body.getMenstruation() != null) {
//                bodyService.setCondition(
//                        body.getId(),
//                        "Menstruation",
//                        body.getMenstruation(),
//                        null,
//                        null);
//            }
//            if (body.getHealthy() != null) {
//                bodyService.setCondition(
//                        body.getId(),
//                        "Healthy",
//                        body.getHealthy(),
//                        null,
//                        null
//                );
//            }
//            if (body.getHiv() != null) {
//                bodyService.setCondition(
//                        body.getId(),
//                        "HIV",
//                        body.getHiv(),
//                        null,
//                        null
//                );
//            }
//            if (body.getAids() != null) {
//                bodyService.setCondition(
//                        body.getId(),
//                        "AIDS",
//                        body.getAids(),
//                        null,
//                        null
//                );
//            }
//        }
//        Iterable<BodyCondition> bodyConditionIterable = bodyConditionRepo.findAll();
//        logger.debug(bodyConditionIterable);
//    }
//}