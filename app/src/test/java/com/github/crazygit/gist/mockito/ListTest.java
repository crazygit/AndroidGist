package com.github.crazygit.gist.mockito;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

// 参考
// https://www.jianshu.com/p/a3b59fad17e6
public class ListTest {
    @Test
    public void testGet() {
        // 创建mock对象
        List mockedList = Mockito.mock(List.class);

        // 设置mock对象的行为 － 当调用其get方法获取第0个元素时，返回"one"
        Mockito.when(mockedList.get(0)).thenReturn("one");

        // 使用mock对象 － 会返回前面设置好的值"one"，即便列表实际上是空的
        String str = (String) mockedList.get(0);

        Assert.assertEquals("one", str);
        Assert.assertEquals(0, mockedList.size());

        // 验证mock对象的get方法被调用过，而且调用时传的参数是0
        Mockito.verify(mockedList).get(0);
    }

    @Test
    public void testGetWithoutMockMethod() {
        // 创建mock对象
        List mockedList = Mockito.mock(List.class);
        // 通过Mockito.mock()方法mock出来的对象，如果不指定的话，一个mock对象的所有非void方法都将返回默认值：int、long类型方法将返回0，
        // boolean方法将返回false，对象方法将返回null等等；而void方法将什么都不做
        Assert.assertNull(mockedList.get(100));
    }

    @Test
    public void testSpy() {
        List list = new LinkedList();
        List spy = Mockito.spy(list);


        //Impossible: real method is called so spy.get(0) throwsIndexOutOfBoundsException (the list is yet empty)
        when(spy.get(0)).thenReturn("foo");

        //You have to use doReturn() for stubbing
        doReturn("foo").when(spy).get(0);
    }
}
