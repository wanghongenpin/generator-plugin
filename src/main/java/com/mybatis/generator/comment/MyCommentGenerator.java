package com.mybatis.generator.comment;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * mybatis generator 自定义comment生成器
 *
 * @author wanghongen
 * 27/02/2018
 */
public class MyCommentGenerator implements CommentGenerator {

    private static final Pattern PATTERN = Pattern.compile("\n", Pattern.LITERAL);
    /**
     * properties配置文件
     */
    private final Properties properties;
    /**
     * properties配置文件
     */
    private final Properties systemPro;

    /*
     * 父类时间
     */
    private boolean suppressDate;

    /**
     * 父类所有注释
     */
    private boolean suppressAllComments;

    /**
     * 当前时间
     */
    private final String currentDateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public MyCommentGenerator() {
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
    }

    /**
     * Java类的类注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" *");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append(' ');
        sb.append(getDateString());
        innerClass.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        innerClass.addJavaDocLine(" */");
    }

    /**
     * 为类添加注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(systemPro.getProperty("user.name"));
        sb.append(' ');
        sb.append(getDateString());
        innerClass.addJavaDocLine(sb.toString());
        innerClass.addJavaDocLine(" */");
    }


    /**
     * Mybatis的Mapper.xml文件里面的注释
     */
    @Override
    public void addComment(XmlElement xmlElement) {

    }

    /**
     * 从该配置中的任何属性添加此实例的属性CommentGenerator配置。
     * 这个方法将在任何其他方法之前被调用。
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    /**
     * 此方法返回格式化的日期字符串以包含在Javadoc标记中和XML注释。 如果您不想要日期，则可以返回null在这些文档元素中
     */
    protected String getDateString() {
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }

    /**
     * 此方法为其添加了自定义javadoc标签。
     *
     * @param javaElement       JavaElement
     * @param markAsDoNotDelete markAsDoNotDelete
     */
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }


    /**
     * 为枚举添加注释
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        sb.append(" * ").append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        innerEnum.addJavaDocLine(" */");
    }

    /**
     * Java属性注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        if ("orderByClause".equals(field.getName())) {
            sb.append("排序字段");
        } else if ("distinct".equals(field.getName())) {
            sb.append("过滤重复数据");
        } else if ("oredCriteria".equals(field.getName())) {
            sb.append("查询条件");
        } else {
            sb.append(introspectedTable.getFullyQualifiedTable());
        }
        field.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        field.addJavaDocLine(" */");
    }

    /**
     * 为字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        field.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        field.addJavaDocLine(" */");
    }

    /**
     * 普通方法的注释，这里主要是XXXMapper.java里面的接口方法的注释
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" *");
        if (method.isConstructor()) {
            sb.append(" 构造查询条件");
        }
        String method_name = method.getName();
        if ("setOrderByClause".equals(method_name)) {
            sb.append(" 设置排序字段");
        } else if ("setDistinct".equals(method_name)) {
            sb.append(" 设置过滤重复数据");
        } else if ("getOredCriteria".equals(method_name)) {
            sb.append(" 获取当前的查询条件实例");
        } else if ("isDistinct".equals(method_name)) {
            sb.append(" 是否过滤重复数据");
        } else if ("getOrderByClause".equals(method_name)) {
            sb.append(" 获取排序字段");
        } else if ("createCriteria".equals(method_name)) {
            sb.append(" 创建一个查询条件");
        } else if ("createCriteriaInternal".equals(method_name)) {
            sb.append(" 内部构建查询条件对象");
        } else if ("clear".equals(method_name)) {
            sb.append(" 清除查询条件");
        } else if ("countByExample".equals(method_name) || "countByCondition".equals(method_name)) {
            sb.append(" 根据指定的条件获取数据库记录数");
        } else if ("deleteByExample".equals(method_name) || "deleteByCondition".equals(method_name)) {
            sb.append(" 根据指定的条件删除数据库符合条件的记录");
        } else if ("deleteByPrimaryKey".equals(method_name) || "deleteById".equals(method_name)) {
            sb.append(" 根据主键删除数据库的记录");
        } else if ("insert".equals(method_name)) {
            sb.append(" 新写入数据库记录");
        } else if ("insertSelective".equals(method_name)) {
            sb.append(" 动态字段,写入数据库记录");
        } else if ("selectByExample".equals(method_name) || "selectByCondition".equals(method_name)) {
            sb.append(" 根据指定的条件查询符合条件的数据库记录");
        } else if ("selectByPrimaryKey".equals(method_name) || "selectById".equals(method_name)) {
            sb.append(" 根据指定主键获取一条数据库记录");
        } else if ("updateByExampleSelective".equals(method_name) || "updateByConditionSelective".equals(method_name)) {
            sb.append(" 动态根据指定的条件来更新符合条件的数据库记录");
        } else if ("updateByExample".equals(method_name) || "updateByCondition".equals(method_name)) {
            sb.append(" 根据指定的条件来更新符合条件的数据库记录");
        } else if ("updateByPrimaryKeySelective".equals(method_name) || "updateByIdSelective".equals(method_name)) {
            sb.append(" 动态字段,根据主键来更新符合条件的数据库记录");
        } else if ("updateByPrimaryKey".equals(method_name) || "updateById".equals(method_name)) {
            sb.append(" 根据主键来更新符合条件的数据库记录");
        }
        method.addJavaDocLine(sb.toString());
        List<Parameter> parameterList = method.getParameters();
        if (!parameterList.isEmpty()) {
            method.addJavaDocLine(" *");
            if ("or".equals(method_name)) {
                sb.append(" 增加或者的查询条件,用于构建或者查询");
            }
        } else {
            if ("or".equals(method_name)) {
                sb.append(" 创建一个新的或者查询条件");
            }
        }
        String paramterName;
        for (Parameter parameter : parameterList) {
            sb.setLength(0);
            sb.append(" * @param "); //$NON-NLS-1$
            paramterName = parameter.getName();
            sb.append(paramterName);
            sb.append(' ').append(parameter.getType().getShortNameWithoutTypeArguments());
            if ("orderByClause".equals(paramterName)) {
                sb.append(" 排序字段"); //$NON-NLS-1$
            } else if ("distinct".equals(paramterName)) {
                sb.append(" 是否过滤重复数据");
            } else if ("criteria".equals(paramterName)) {
                sb.append(" 过滤条件实例");
            }
            method.addJavaDocLine(sb.toString());
        }
        if (method.getReturnType() != null) {
            method.addJavaDocLine(" * ");
            sb.setLength(0);
            sb.append(" * @return " + method.getReturnType().getShortName());
            if ("int".equals(method.getReturnType().getShortName())) {
                sb.append(" 影响行数");
            } else if ("long".equals(method.getReturnType().getShortName())) {
                sb.append(" 记录数");
            } else if (introspectedTable.getTableConfiguration().getModelType().getModelType().equals(method.getReturnType().getShortName())) {
                sb.append(' ' + introspectedTable.getRemarks());
            }
            method.addJavaDocLine(sb.toString());
        }
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }


    /**
     * 给getter方法加注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(' ');
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        method.addJavaDocLine(" */");
    }

    /**
     * 给setter方法加注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(' ');
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        method.addJavaDocLine(" */");
    }

    /**
     * 给Java文件加注释，这个注释是在文件的顶部，也就是package上面。
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }

    /**
     * 为模型类添加注释
     */
    @Override
    public void addModelClassComment(TopLevelClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        if (introspectedTable.getRemarks() == null || introspectedTable.getRemarks().isEmpty()) {
            sb.append(introspectedTable.getFullyQualifiedTable());
        } else {
            sb.append(introspectedTable.getRemarks()).append(' ').append(introspectedTable.getFullyQualifiedTable());
        }
        innerClass.addJavaDocLine(PATTERN.matcher(sb.toString()).replaceAll(Matcher.quoteReplacement(" ")));
        innerClass.addJavaDocLine(" * ");
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(systemPro.getProperty("user.name"));
        innerClass.addJavaDocLine(sb.toString());
        innerClass.addJavaDocLine(" * " + getDateString());
        innerClass.addJavaDocLine(" */");
    }

    /**
     * 为调用此方法作为根元素的第一个子节点添加注释。
     */
    @Override
    public void addRootComment(XmlElement element) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
    }

}