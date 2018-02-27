package com.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.lang.reflect.Field;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;


/**
 * Example生成位置
 *
 * @author wanghongen
 * 27/02/2018
 */
public class ExamplePlugin extends PluginAdapter {
    private String exampleTargetPackage;

    @Override
    public boolean validate(List<String> warnings) {
        if (!stringHasValue(properties
                .getProperty("exampleTargetPackage"))) { //$NON-NLS-1$
            warnings.add("exampleTargetPackage未设置"); //$NON-NLS-1$
            return false;
        }
        exampleTargetPackage = properties.getProperty("exampleTargetPackage");
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Class<? extends FullyQualifiedJavaType> clazz = topLevelClass.getType().getClass();
        try {
            //获取指定名字的私有域
            Field field = clazz.getDeclaredField("packageName");
            field.setAccessible(true);
            //设置私有域的值
            field.set(topLevelClass.getType(), exampleTargetPackage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
