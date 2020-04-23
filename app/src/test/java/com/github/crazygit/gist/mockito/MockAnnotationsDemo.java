package com.github.crazygit.gist.mockito;


//@Mock注解
//使用@Mock注解来定义mock对象有如下的优点：
//
//        方便mock对象的创建
//        减少mock对象创建的重复代码
//        提高测试代码可读性
//        变量名字作为mock对象的标示，所以易于排错

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockAnnotationsDemo {

    @Mock
    ArrayList arrayList;

    @Before
    public void setUp() {
        when(arrayList.size()).thenReturn(100);
        when(arrayList.get(0)).thenReturn("A");
        when(arrayList.get(1)).thenReturn("B");
    }

    @Test
    public void testMockData() {
        Assert.assertEquals(100, arrayList.size());
        Assert.assertEquals("A", arrayList.get(0));
        Assert.assertEquals("B", arrayList.get(1));
    }

}
