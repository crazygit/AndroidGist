package com.github.crazygit.gist.mockito;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

//@Spy注解
//使用@Spy生成的类，所有方法都是真实方法，返回值和真实方法一样的，是使用Mockito.spy()的快捷方式
@RunWith(MockitoJUnitRunner.class)
public class SpyWithAnnotations {

    @Spy
    ArrayList<String> arrayList;

    @Before
    public void setUp() {
        // 调用size()方法时返回假的值，其他方法调用时间的方法
        when(arrayList.size()).thenReturn(100);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSpy() {
        Assert.assertEquals(arrayList.size(), 100);
        // 虽然模拟了size()方法，但是get方法会调用真是的get方法，所以仍然会报错
        arrayList.get(0);
    }

}
