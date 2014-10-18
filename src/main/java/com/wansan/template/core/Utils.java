package com.wansan.template.core;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 14-4-13.
 */
public class Utils {
    public static String code;
    private static Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9' };

    public static String encodePassword(String oldPassword,String salt){
        return encoder.encodePassword(oldPassword,salt);
    }

    public static String getNewUUID(){
        return java.util.UUID.randomUUID().toString();
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isDate(String inDate) {

        if (inDate == null)
            return false;

        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = (SimpleDateFormat) Constants.formatShort;

        if (inDate.trim().length() != dateFormat.toPattern().length())
            return false;

        dateFormat.setLenient(false);

        try {
            //parse the inDate parameter
            dateFormat.parse(inDate.trim());
        }
        catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static Timestamp getNow(){
        return new Timestamp((new Date()).getTime());
    }
    public static BufferedImage randomDrawImage(int width, int height,
                                                int codeCount)
    {
        int coord_X = width / (codeCount + 1);
        int fontHeight = height - 2;
        int coord_Y = height - 4;
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics2D g = buffImg.createGraphics();

        Random random = new Random();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        g.setFont(font);

        g.setColor(Color.blue);
        g.drawRect(0, 0, width - 1, height - 1);

        g.setColor(Color.BLACK);
//		for (int i = 0; i < 20; i++)
//		{
//			int x = random.nextInt(width);
//			int y = random.nextInt(height);
//			int xl = random.nextInt(12);
//			int yl = random.nextInt(12);
//			g.drawLine(x, y, x + xl, y + yl);
//		}
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        for (int i = 0; i < codeCount; i++)
        {
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * coord_X, coord_Y);

            randomCode.append(strRand);
        }
        code = randomCode.toString();

        return buffImg;

    }

    public static String getRandomString(int length)
    {
        String str="abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sf= new StringBuilder();
        for(int i=0;i<length;i++)
        {
            int number=random.nextInt(62);//0~61
            sf.append(str.charAt(number));


        }
        return sf.toString();
    }
    public static String getRandomNumString(int length)
    {
        String str="0123456789";
        Random random=new Random();
        StringBuilder sf= new StringBuilder();
        for(int i=0;i<length;i++)
        {
            int number=random.nextInt(10);//0~61
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }

    public static String getNewFilename(){

        DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String newName = format.format(new Date())+getRandomNumString(3);
        return newName;

    }
}
