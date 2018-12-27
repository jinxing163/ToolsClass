package com.example.tools.mail;

import com.example.tools.ToolsApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author JinXing
 * @date 2018/11/12 19:45
 */
public class EmailSendServiceTest extends ToolsApplicationTests {

    @Autowired
    private EmailSendService emailSendService;

    @Test
    public void sendText() {
        boolean b = emailSendService.sendText("1012877654@qq.com", "知室实验室", "测试邮件");
        System.out.println(b);
    }

    @Test
    public void sendHtml() {

        boolean b = emailSendService.sendText("1012877654@qq.com", "知室实验室", "尊敬的金星3，您已成功报名《于洋测试用成长营》，请点击http://m.g2s.cn/eNo?ty=1&etu=j4dNwxKw，获取电子门票。请于2018年12月14日至上海4检票入场，活动准时开始。");
        System.out.println(b);

    }
}