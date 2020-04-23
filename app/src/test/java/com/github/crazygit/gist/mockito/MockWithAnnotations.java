package com.github.crazygit.gist.mockito;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

//https://www.jianshu.com/p/a3b59fad17e6


// 要使用Mockito的注解,如@Mock, @Spy等，需要先初始化它。方法有两种
// 1. 在@Before里面初始化, 初始化的方法为：MockitoAnnotations.initMocks(testClass)参数testClass是你所写的测试类
//     MockitoAnnotations.initMocks(this);
// 2. 使用@RunWith注解
//    @RunWith(MockitoJUnitRunner.class)

//用Mockito提供的Junit Runner: MockitoJUnitRunner,
@RunWith(MockitoJUnitRunner.class)
public class MockWithAnnotations {

    @Mock
    ArrayList arrayList;


    @Before
    public void initMocks() {
        //一般情况下在Junit4的@Before定义的方法中执行初始化工作，如下：
        // 要使用只有Annotation还不够，要让它们工作起来还需要进行初始化工作。初始化的方法为：MockitoAnnotations.initMocks(testClass)参数testClass是你所写的测试类。一般情况下在Junit4的@Before定义的方法中执行初始化工作
//        MockitoAnnotations.initMocks(this);
        when(arrayList.get(0)).thenReturn(0);
    }

    @Test
    public void testGet() {
        Assert.assertEquals(arrayList.get(0), 0);
    }

}
