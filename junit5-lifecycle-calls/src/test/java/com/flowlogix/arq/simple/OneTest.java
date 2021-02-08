/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flowlogix.arq.simple;

import static com.flowlogix.arq.simple.CreateFileTest.RunsWhere.CLIENT;
import static com.flowlogix.arq.simple.CreateFileTest.RunsWhere.SERVER;
import static com.flowlogix.arq.simple.CreateFileTest.appendToFile;
import static com.flowlogix.arq.simple.CreateFileTest.checkRunsWhere;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 *
 * @author lprimak
 */
@ExtendWith(ArquillianExtension.class)
public class OneTest {
    @Inject
    Greeter greeter;

    @Test
    @Order(1)
    void one() {
        assertEquals("one", "one");
        appendToFile("test_one");
        checkRunsWhere(SERVER);
    }

    @Test
    @Order(2)
    void should_create_greeting() {
        assertEquals("Hello, Earthling!", greeter.createGreeting("Earthling"));
        greeter.greet(System.out, "Earthling");
        appendToFile("test_two");
        checkRunsWhere(SERVER);
    }

    @BeforeAll
    static void beforeAll() {
        appendToFile("before_all");
        checkRunsWhere(CLIENT);
    }

    @BeforeEach
    void beforeEach() {
        appendToFile("before_each");
        checkRunsWhere(SERVER);
    }

    @AfterEach
    void afterEeach() {
        appendToFile("after_each");
        checkRunsWhere(SERVER);
    }

    @AfterAll
    static void afterAll() {
        appendToFile("after_all");
        checkRunsWhere(CLIENT);
    }


    @Deployment
    static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClass(Greeter.class)
                .addClass(CreateFileTest.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }
}
