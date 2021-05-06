package net.codejava;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component("generator")
public class TinyUrlGenerator implements  TinyGenerrator{
    @Override
      public String generate(String mainUrl ) {
        System.out.println("coming generator");
        String sTempDate=getDate();
        System.out.println("sTempDate"+sTempDate);
        if(!"".equals(sTempDate)){
            sTempDate=sTempDate + UUID.randomUUID().toString().replace("-","").substring(0,4);
            System.out.println(sTempDate);

        }
        return sTempDate;
    }

    @Override
    public String enCode(String tinyUrl) {
        return null;
    }

    private  static String getDate(){
        SimpleDateFormat sdf= new SimpleDateFormat("ddmm");
        return sdf.format(new Date());
    }
}
