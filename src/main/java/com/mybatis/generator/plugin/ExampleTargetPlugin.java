package com.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;

import java.util.List;
import java.util.Properties;


/**
 * Example生成位置
 *
 * @author wanghongen
 * 27/02/2018
 */
public class ExampleTargetPlugin extends PluginAdapter {
    public static final String PRO_TARGET_PACKAGE = "targetPackage";  // 配置targetPackage名
    private static String targetPackage;   // 目标包

    @Override
    public boolean validate(List<String> warnings) {
        // 获取配置的目标package
        Properties properties = getProperties();
        targetPackage = properties.getProperty(PRO_TARGET_PACKAGE);
        if (targetPackage == null) {
            warnings.add("ExampleTargetPlugin targetPackage未设置");
            return false;
        }
        return true;
    }

    /**
     * 初始化阶段
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String exampleType = introspectedTable.getExampleType();
        // 修改包名
        Context context = getContext();
        JavaModelGeneratorConfiguration configuration = context.getJavaModelGeneratorConfiguration();
        String targetPackage = configuration.getTargetPackage();
        String newExampleType = exampleType.replace(targetPackage, ExampleTargetPlugin.targetPackage);
        introspectedTable.setExampleType(newExampleType);
    }

}