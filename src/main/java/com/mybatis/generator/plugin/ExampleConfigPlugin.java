package com.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;


/**
 * 修改生成的Example类的类名和Mapper类中的方法名或参数名
 *
 * @author wanghongen
 * 27/02/2018
 */
public class ExampleConfigPlugin extends PluginAdapter {
    public static final String PRO_TARGET_PACKAGE = "targetPackage";  // 配置targetPackage名
    private static String targetPackage;   // 目标包

    public static final String VALIDATION_ERROR_MESSAGE = "ValidationError.18";
    public static final String CLASS_SEARCH_PROPERTY = "classMethodSearchString";
    public static final String CLASS_REPLACE_PROPERTY = "classMethodReplaceString";
    public static final String PARAM_SEARCH_PROPERTY = "parameterSearchString";
    public static final String PARAM_REPLACE_PROPERTY = "parameterReplaceString";

    private String classMethodReplaceString;
    private Pattern classMethodPattern;
    private String parameterReplaceString;
    private Pattern parameterPattern;

    @Override
    public boolean validate(List<String> warnings) {
        String classMethodSearchString = properties.getProperty(CLASS_SEARCH_PROPERTY);
        classMethodReplaceString = properties.getProperty(CLASS_REPLACE_PROPERTY);

        String parameterSearchString = properties.getProperty(PARAM_SEARCH_PROPERTY);
        parameterReplaceString = properties.getProperty(PARAM_REPLACE_PROPERTY);

        // 获取配置的目标package
        targetPackage = properties.getProperty(PRO_TARGET_PACKAGE);
        if (targetPackage == null) {
            warnings.add("ExampleConfigPlugin targetPackage未设置");
            return false;
        }
        boolean valid = stringHasValue(classMethodSearchString) && stringHasValue(classMethodReplaceString)
                && stringHasValue(parameterSearchString) && stringHasValue(parameterReplaceString);

        if (valid) {
            classMethodPattern = Pattern.compile(classMethodSearchString);
            parameterPattern = Pattern.compile(parameterSearchString);
        } else {
            if (!stringHasValue(classMethodSearchString)) {
                warnings.add(getString(VALIDATION_ERROR_MESSAGE,
                        getClass().getSimpleName(), CLASS_SEARCH_PROPERTY));
            }
            if (!stringHasValue(classMethodReplaceString)) {
                warnings.add(getString(VALIDATION_ERROR_MESSAGE,
                        getClass().getSimpleName(), CLASS_REPLACE_PROPERTY));
            }
            if (!stringHasValue(parameterSearchString)) {
                warnings.add(getString(VALIDATION_ERROR_MESSAGE,
                        getClass().getSimpleName(), PARAM_SEARCH_PROPERTY));
            }
            if (!stringHasValue(parameterReplaceString)) {
                warnings.add(getString(VALIDATION_ERROR_MESSAGE,
                        getClass().getSimpleName(), PARAM_REPLACE_PROPERTY));
            }
        }

        return valid;
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
        String newExampleType = classMethodPattern.matcher(exampleType.replace(targetPackage, ExampleConfigPlugin.targetPackage)).replaceAll(Matcher.quoteReplacement(classMethodReplaceString));
        introspectedTable.setExampleType(newExampleType);
    }

    boolean renameMethod(Method method) {
        String oldMethodName = method.getName();
        Matcher matcher = classMethodPattern.matcher(oldMethodName);
        String newMethodName = matcher.replaceAll(classMethodReplaceString);
        method.setName(newMethodName);
        for (int i = 0, size = method.getJavaDocLines().size(); i < size; i++) {
            method.getJavaDocLines().set(i, parameterPattern.matcher(method.getJavaDocLines().get(i)).replaceAll(parameterReplaceString));
        }
        for (int i = 0, size = method.getParameters().size(); i < size; i++) {
            Parameter parameter = method.getParameters().get(i);
            String oldParamName = parameter.getName();
            matcher = parameterPattern.matcher(oldParamName);
            if (matcher.lookingAt()) {
                String newName = matcher.replaceAll(parameterReplaceString);
                Parameter newParam = new Parameter(parameter.getType(), newName, parameter.isVarargs());
                for (String annotation : parameter.getAnnotations()) {
                    newParam.addAnnotation(annotation);
                }
                method.getParameters().set(i, newParam);
            }
        }

        return true;
    }


    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze,
                                                       IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                       IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze,
                                                        IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                        IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
                                                           IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                           IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                 IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                    IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                 IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                 IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                    IntrospectedTable introspectedTable) {
        return renameMethod(method);
    }

}