package com.itheima.pinyougou.page.service.impl.test;

import com.itheima.pinyougou.page.service.ItemPageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/*.xml")
public class ItemPageServiceTest {

    @Autowired
    private ItemPageService itemPageService;

    @Test
    public void testGenItemHtml(){
        boolean b = itemPageService.genItemHtml(1L);
        System.out.println("result = " + b);
    }

}
