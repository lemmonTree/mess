package com.belling.admin.controller.test;

import com.belling.admin.controller.test.model.Student;
import com.belling.admin.model.LoginLog;
import com.belling.admin.service.LoginLogService;
import com.belling.base.controller.BaseController;
import com.belling.base.enums.SysEnum;
import com.belling.base.model.PageListResult;
import com.belling.base.model.Pagination;
import com.belling.base.model.ResponseResult;
import com.belling.base.util.common.ExportExcel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {


    @Autowired
    private LoginLogService loginLogService;

    /**
     * 列表示例
     * @return 页面
     */
    @RequestMapping(value = "/table",method = RequestMethod.GET)
    public String table(Model model) {
        return "/test/table";
    }

    /**
     * 文件上传示例
     * @return 页面
     */
    @RequestMapping(value = "/file",method = RequestMethod.GET)
    public String file(Model model) {
        return "/test/file";
    }

    /**
     * 上传
     */
    @RequestMapping(value = "/upload")
    public @ResponseBody ResponseResult upload(HttpServletRequest request, MultipartFile file, String path) {
        String newFileName = "";
        try {
            if(file != null){
                String filePath = request.getSession().getServletContext().getRealPath("/")+ "static\\images\\user\\";
                String originalFilename = file.getOriginalFilename();
                newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
                System.out.println("filename: " + filePath + newFileName);
                File imgFile = new File(filePath + newFileName);
                file.transferTo(imgFile);
                // todo base64转码
//                String imgstr = ImageHelper.getImageStr(filePath + newFileName);
//                ImageHelper.generateImage(imgstr,request.getSession().getServletContext().getRealPath("/")+ "static\\images\\" + newFileName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseResult.createSuccessResult().setMessage("上传成功").setData("static\\images\\" + newFileName).setCode(SysEnum.SUCCESS.getType());
    }


    /**
     * 用户分页
     *
     * @return
     */
    @RequestMapping(value = "/page2", method = RequestMethod.GET)
    @ResponseBody
    public PageListResult page2(Integer page, Integer limit, String startTime, String endTime) {
        Pagination<LoginLog> pageList = new Pagination<LoginLog>(page, limit);
        Subject subject = SecurityUtils.getSubject();
        String uid = "";
        if (subject != null) {
            uid = (String)subject.getPrincipal();
        }
        pageList = loginLogService.findPaginationByTime(uid, startTime, endTime, pageList);
        return PageListResult.createResult(SysEnum.SUCCESS.getType(),pageList.getList(), pageList.getRowCount(), "success");
    }

    /**
     * excel导出
     * @param request 请求
     * @param response 响应
     * @throws Exception 异常
     */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ExportExcel<Student> ex = new ExportExcel<Student>();
        String path = "D://a.xls";
        String[] headers ={ "学号", "姓名", "年龄", "性别", "出生日期" };
        List<Student> dataset = new ArrayList<Student>();
        dataset.add(new Student(10000001, "张三", 20, true, new Date()));
        dataset.add(new Student(20000002, "李四", 24, false, new Date()));
        dataset.add(new Student(30000003, "王五", 22, true, new Date()));
        OutputStream out = new FileOutputStream(path);
        ex.exportExcel(headers, dataset, out);
        out.close();
        // path是指欲下载的文件的路径。
        File file = new File(path);
        // 取得文件名。
        String filename = file.getName();
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes()));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/vnd.ms-excel;charset=gb2312");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        if (file.exists() && file.isFile()){
            file.delete();
        }
    }
}
