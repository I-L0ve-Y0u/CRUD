package servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@WebServlet(urlPatterns = "/checkcode")
public class CheckCodeServlet extends HttpServlet {

    //图片宽度
    private int WIDTH = 120;
    //图片高度
    private int HEIGHT = 30;
    //随机字
    String str = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm锕旎媾鲨";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //实现验证码：
        //图片画布：java的图形方法的用法
        BufferedImage image  = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        //得到画笔
        Graphics g = image.getGraphics();
        //绘制画布的背景
        fillBgColor(g);
        //绘制边框
        filBorder(g);
        //绘制几条干扰线
        fillRandomLine(g);
        //绘制号码

        String vsCode = fillRandomStr(g);
        request.getSession().setAttribute("vsCode",vsCode);


        //把绘制好的图形，放到页面上
        OutputStream out = response.getOutputStream();
        //告诉页面，要设置的类型是图片类型
        response.setContentType("image/jpeg");
        //把图片回显到页面上
        ImageIO.write(image,"jpeg",out);
    }

    private String fillRandomStr(Graphics g) {
        String vsCode = "";//服务器拼接的验证码
        Graphics2D g2d = (Graphics2D)g;
        //起始位置
        int x = 10;
        Random r = new Random();
        //设置字体
        Font font = new Font("宋体",Font.BOLD,20);
        //写4个字
        for (int i = 0; i < 4; i++) {
            //R:0-255  G:0-255 B:0-255
            g2d.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
            g2d.setFont(font);
            //旋转-30~30度
            //1角度=PI/180弧度
            //r.nextInt():任意的一个整数 ：负数 正数
            int degree = r.nextInt()%30;
            g2d.rotate(degree*Math.PI/180,x,HEIGHT-5);
            //得到字母随机的位置
            char c = str.charAt(r.nextInt(str.length()));
            //把得到的字符进行拼接
            vsCode += c;

            g2d.drawString(c+"",x,HEIGHT-5);
            //把画笔归位
            g2d.rotate(-degree*Math.PI/180,x,HEIGHT-5);
            //画完一个之后，把位置往后面移一下
            x+=30;
        }
        return vsCode;
        //返回拼接后得到的验证码（String）
    }

    private void fillRandomLine(Graphics g) {
        //干扰线
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            g.setColor(Color.green);
            //设置起点，设置终点
            int x1 = r.nextInt(WIDTH);
            int y1 = r.nextInt(HEIGHT);

            int x2 = r.nextInt(WIDTH);
            int y2 = r.nextInt(HEIGHT);
            //画直线
            g.drawLine(x1,y1,x2,y2);
        }
    }

    private void filBorder(Graphics g) {
        g.setColor(Color.black);
        //绘制矩形边框
        g.drawRect(0,0,WIDTH-1,HEIGHT-1);
    }

    private void fillBgColor(Graphics g) {
        //java.awt包下面
        g.setColor(Color.white);
        //填充矩形
        g.fillRect(0,0,WIDTH,HEIGHT);
    }
}
