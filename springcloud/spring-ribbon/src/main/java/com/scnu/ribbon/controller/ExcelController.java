package com.scnu.ribbon.controller;

import com.scnu.ribbon.excel.ExcelData;
import com.scnu.ribbon.excel.ExcelUtils;
import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void excel(@ModelAttribute RequestParam param, HttpServletResponse response) throws Exception {
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("a1");
        titles.add("a2");
        titles.add("a3");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();
        row.add("11111111111");
        row.add("22222222222");
        row.add("3333333333");
        rows.add(row);

        data.setRows(rows);


        SimpleDateFormat fdate=new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName=fdate.format(new Date());
        ExcelUtils.exportExcel(response,fileName,data);
    }

    @Data
    class RequestParam{
         private String name;
         private String addr;

    }


}
