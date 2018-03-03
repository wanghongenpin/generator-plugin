package com.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Example和mapper类中文注释
 *
 * @author wanghongen
 * 27/02/2018
 */
public class CommentPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine(" * ");
        sb.append(" * @author ");
        sb.append(System.getProperties().getProperty("user.name"));
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" * " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        topLevelClass.addJavaDocLine(" */");
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * " + introspectedTable.getRemarks());
        interfaze.addJavaDocLine(" * ");
        sb.append(" * @author ").append(System.getProperties().getProperty("user.name"));
        interfaze.addJavaDocLine(sb.toString());
        interfaze.addJavaDocLine(" * " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        interfaze.addJavaDocLine(" */");
        return true;
    }


}
