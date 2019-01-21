package com.example.tools.Blob;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author JinXing
 * @date 2018/6/28 11:39
 */

@RestController
public class Blob {

                        
    @RequestMapping("/test")
    public OutputStream test(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {

        /*
         * 在这里可以进行权限验证等操作
         */

//创建文件对象
        File f = new File("E:/test/1.mp4");
//获取文件名称
        String fileName = f.getName();
//导出文件
        String agent = httpServletRequest.getHeader("User-Agent").toUpperCase();
        InputStream fis = null;
        OutputStream os = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(f.getPath()));
            byte[] buffer;
            buffer = new byte[fis.available()];
            fis.read(buffer);
            httpServletResponse.reset();
            //由于火狐和其他浏览器显示名称的方式不相同，需要进行不同的编码处理
            if (agent.contains("FIREFOX")) {//火狐浏览器
                httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
            } else {//其他浏览器
                httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            }
            //设置response编码
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.addHeader("Content-Length", "" + f.length());
            //设置输出文件类型
            httpServletResponse.setContentType("video/mpeg4");
            //获取response输出流
            os = httpServletResponse.getOutputStream();
            // 输出文件
            os.write(buffer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //关闭流
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (os != null) {
                        os.flush();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } finally {
                    try {
                        if (os != null) {
                            os.close();
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }

        return os;

    }

    
}
