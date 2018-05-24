package com.waben.stock.applayer.operation.warpper.mail;

import com.deepoove.poi.XWPFTemplate;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by yuyidi on 2018/3/3.
 * @desc
 */
public class ExcelUtil {

    static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 询价单生成Excel模板
     * @param quotoInquiry
     * @return
     */
    private static String renderInquiry(QuotoInquiry quotoInquiry, String contextPath) {
        String file = null;
        String url = "/officetemplate" + File.separator + "excel" + File.separator + "inquiry.xlsx";
        try (InputStream is = ExcelUtil.class.getResourceAsStream(url)) {
            if (is == null) {
                logger.error("模板文件找不到");
            }
            Date date = new Date();
            String path = contextPath + new SimpleDateFormat("yyyy/MM/dd/").format(date)
                            .replace("/", File.separator);
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".xlsx";
            file = path + fileName;

            Map<String, Object> model = new HashMap<>();
            model.put("date", DateUtils.formatDate(quotoInquiry.getDate(), "yyyy/MM/dd"));
            model.put("tenor", quotoInquiry.getTenor());
            model.put("structure", "ATM Call");
            model.put("underlying", quotoInquiry.getUnderlying());
            model.put("code", quotoInquiry.getCode());
            model.put("strike", quotoInquiry.getStrike());
            model.put("amount", quotoInquiry.getAmount());
            model.put("price", quotoInquiry.getPrice());
            try (OutputStream os = new FileOutputStream(file)) {
                Workbook workbook = new ExcelTransformer().transform(is, model);
                workbook.write(os);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 申购单生成Excel模板
     * @param quotoPurchase
     * @return
     */
    private static String renderPurchase(QuotoPurchase quotoPurchase, String contextPath) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("underlying", quotoPurchase.getUnderlying());
        model.put("code", quotoPurchase.getCode());
        model.put("begin", DateUtils.formatDate(quotoPurchase.getBegin(), "yyyy/MM/dd"));
        model.put("end", DateUtils.formatDate(quotoPurchase.getEnd(), "yyyy/MM/dd"));
        model.put("strike", quotoPurchase.getStrike());
        model.put("amount", quotoPurchase.getAmount());
        model.put("rate", quotoPurchase.getRate());
        return commonWordRender(model, "purchase", contextPath);
    }

    /**
     * 行权单生成Excel模板
     * @param quotoExenise
     * @return
     */
    private static String renderExenise(QuotoExenise quotoExenise, String contextPath) {
        Map<String, Object> model = new HashMap<>();
        model.put("exenise", DateUtils.formatDate(quotoExenise.getExenise(), "yyyy/MM/dd"));
        model.put("dueTo", DateUtils.formatDate(quotoExenise.getDueTo(), "yyyy/MM/dd"));
        model.put("underlying", quotoExenise.getUnderlying());
        model.put("code", quotoExenise.getCode());
        model.put("strike", quotoExenise.getStrike());
        model.put("amount", quotoExenise.getAmount());
        return commonWordRender(model, "exercise", contextPath);
    }


    /**
     * 模板公共处理方法
     * @param contextPath 模板上下文路径
     * @param obj 模板实体类 QuotoInquiry，QuotoPurchase，QuotoExenise
     * @return
     */
    public static String commonRender(String contextPath, Object obj) {
        String file = "";
        if (null == obj) {
            return "";
        }
        if (obj instanceof QuotoInquiry) {
            file = renderInquiry((QuotoInquiry) obj, contextPath);
        } else if (obj instanceof QuotoPurchase) {
            file = renderPurchase((QuotoPurchase) obj, contextPath);
        } else if (obj instanceof QuotoExenise) {
            file = renderExenise((QuotoExenise) obj, contextPath);
        }
        return file;
    }

    /**
     * Word模板公共处理方法
     * @param templateName 模板名称“inquiry”，“purchase”，“exenise”
     * @param datas 模板数据
     * @return
     */
    public static String commonWordRender(Map<String, Object> datas, String templateName, String contextPath){
        XWPFTemplate template = null;
        Date date = new Date();
        String path = contextPath + new SimpleDateFormat("yyyy/MM/dd").format(date)
                        .replace("/", File.separator) + File.separator + templateName;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String file = path + File.separator + System.currentTimeMillis()  + ".docx";
        String url = "/officetemplate" + File.separator + "world" + File.separator + templateName + ".docx";
        try (InputStream is = ExcelUtil.class.getResourceAsStream(url);
             FileOutputStream out = new FileOutputStream(file)) {
             template = XWPFTemplate.compile(is).render(datas);
             template.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (template != null) {
                    template.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

}
