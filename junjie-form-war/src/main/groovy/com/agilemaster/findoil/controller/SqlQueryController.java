package com.agilemaster.findoil.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agilemaster.share.service.JpaShareService;

@Controller
@RequestMapping("/admin/")
public class SqlQueryController {
	
	@Autowired
	JpaShareService jpaShareService;

	@ResponseBody
	@RequestMapping("/hqlQuery")
	public Object hqlQuery(String hql) {
		Object result = "只能执行sql查询语句，语句不能含有update,drop,delete,insert等更新语句！";
		if(!validateSql(hql)){
			try{
				result = jpaShareService.queryForHql(hql,null);
			}catch(Exception e){
				result= e.getMessage();
			}
		}
		return result;
	}
	 /**
     * 查看sql是否有更新语句，有返回true,不以select开始也返回true
     * @param sql
     * @return
     */
    private boolean validateSql(String sql){
        Boolean result = true;
        if(null!=sql){
            sql = sql.toLowerCase();
            if(!sql.startsWith("select")||sql.contains(";")){
                result =  true;
            }else{
                Pattern pattern = Pattern.compile(".*\\b(delete|update|alter|drop|create)\\b.*");
                Matcher matcher = pattern.matcher(sql);
                result = matcher.matches();
            }
        }
        return result;
    }
}
