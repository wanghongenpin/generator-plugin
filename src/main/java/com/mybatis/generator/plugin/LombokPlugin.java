package com.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author wanghongen
 * 27/02/2018
 */
public class LombokPlugin extends PluginAdapter {
    private static final String DATA = "@Data";
    private static final String BUILDER = "@Builder";
    private static final String SETTER = "@Setter";
    private static final String GETTER = "@Getter";
    private static final String TO_STRING = "@ToString";
    private static final String EQUALS_AND_HASH_CODE = "@EqualsAndHashCode";
    private static final String NO_ARGS_CONSTRUCTOR = "@NoArgsConstructor";
    private static final String ALL_ARGS_CONSTRUCTOR = "@AllArgsConstructor";
    private static final String VALUE = "@Value";
    private static final String LOG = "@Log";
    private static final String SLF4J = "@Slf4j";

    private static final Map<String, String> lombokAnnotationMap = new HashMap<>();

    static {
        lombokAnnotationMap.put(DATA, "lombok.Data");
        lombokAnnotationMap.put(BUILDER, "lombok.Builder");
        lombokAnnotationMap.put(SETTER, "lombok.Setter");
        lombokAnnotationMap.put(GETTER, "lombok.Getter");
        lombokAnnotationMap.put(TO_STRING, "lombok.ToString");
        lombokAnnotationMap.put(EQUALS_AND_HASH_CODE, "lombok.EqualsAndHashCode");
        lombokAnnotationMap.put(NO_ARGS_CONSTRUCTOR, "lombok.NoArgsConstructor");
        lombokAnnotationMap.put(ALL_ARGS_CONSTRUCTOR, "lombok.AllArgsConstructor");
        lombokAnnotationMap.put(VALUE, "lombok.Value");
        lombokAnnotationMap.put(LOG, "lombok.extern.java.Log");
        lombokAnnotationMap.put(SLF4J, "lombok.extern.slf4j.Slf4j");
    }

    private List<String> lombokAnnotationList = new ArrayList<>();

    @Override
    public boolean validate(List<String> warnings) {
        String lombokAnnotations = properties.getProperty("lombokAnnotations");
        if (lombokAnnotations == null || lombokAnnotations.trim().isEmpty()) {
            lombokAnnotationList = Collections.singletonList(DATA);
        } else {
            boolean noneAnno = Stream.of(lombokAnnotations.split(",")).noneMatch(lombokAnnotationMap::containsKey);
            if (noneAnno) {
                warnings.add("LombokPlugin lombokAnnotations值设置不正确,可使用value" + lombokAnnotationMap.keySet());
                return false;
            }
            lombokAnnotationList = Stream.of(lombokAnnotations.split(",")).filter(lombokAnnotationMap::containsKey).collect(toList());
        }
        return true;
    }


    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        lombokAnnotationList.forEach(anno -> {
            topLevelClass.addImportedType(lombokAnnotationMap.get(anno));
            topLevelClass.addAnnotation(anno);
        });

        return true;
    }


    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

}
