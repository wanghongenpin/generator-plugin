package com.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

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
public class RenameExampleClassPlugin extends PluginAdapter {
    private static final Pattern KEY_$ = Pattern.compile("Key$");
    private static final Pattern PRIMARY_KEY = Pattern.compile("PrimaryKey", Pattern.LITERAL);
    private static final Pattern EXAMPLE = Pattern.compile("Example", Pattern.LITERAL);
    private String searchString;
    private String replaceString;
    private Pattern pattern;


    public RenameExampleClassPlugin() {
    }

    @Override
    public boolean validate(List<String> warnings) {

        searchString = properties.getProperty("searchString");
        replaceString = properties.getProperty("replaceString");

        boolean valid = stringHasValue(searchString)
                && stringHasValue(replaceString);

        if (valid) {
            pattern = Pattern.compile(searchString);
        } else {
            if (!stringHasValue(searchString)) {
                warnings.add(getString("ValidationError.18",
                        "RenameExampleClassPlugin",
                        "searchString"));
            }
            if (!stringHasValue(replaceString)) {
                warnings.add(getString("ValidationError.18",
                        "RenameExampleClassPlugin",
                        "replaceString"));
            }
        }

        return valid;
    }

    @Override
    public void initialized(IntrospectedTable intrp) {
        String oldType = intrp.getExampleType();
        Matcher matcher = pattern.matcher(oldType);
        oldType = matcher.replaceAll(replaceString);

        intrp.setExampleType(oldType);
        intrp.setCountByExampleStatementId(exampleToCondition(intrp.getCountByExampleStatementId()));
        intrp.setDeleteByExampleStatementId(exampleToCondition(intrp.getDeleteByExampleStatementId()));

        intrp.setExampleWhereClauseId(exampleToCondition(intrp.getExampleWhereClauseId()));
        intrp.setMyBatis3UpdateByExampleWhereClauseId(exampleToCondition(intrp.getMyBatis3UpdateByExampleWhereClauseId()));

        intrp.setSelectByExampleStatementId(exampleToCondition(intrp.getSelectByExampleStatementId()));
        intrp.setSelectByExampleWithBLOBsStatementId(exampleToCondition(intrp.getSelectByExampleWithBLOBsStatementId()));

        intrp.setUpdateByExampleStatementId(exampleToCondition(intrp.getUpdateByExampleStatementId()));
        intrp.setUpdateByExampleSelectiveStatementId(exampleToCondition(intrp.getUpdateByExampleSelectiveStatementId()));
        intrp.setUpdateByExampleWithBLOBsStatementId(exampleToCondition(intrp.getUpdateByExampleWithBLOBsStatementId()));

        intrp.setDeleteByPrimaryKeyStatementId(primaryKeyToId(intrp.getDeleteByPrimaryKeyStatementId()));
        intrp.setSelectByPrimaryKeyStatementId(primaryKeyToId(intrp.getSelectByPrimaryKeyStatementId()));

        intrp.setUpdateByPrimaryKeySelectiveStatementId(primaryKeyToId(intrp.getUpdateByPrimaryKeySelectiveStatementId()));
        intrp.setUpdateByPrimaryKeyStatementId(primaryKeyToId(intrp.getUpdateByPrimaryKeyStatementId()));
        intrp.setUpdateByPrimaryKeyWithBLOBsStatementId(primaryKeyToId(intrp.getUpdateByPrimaryKeyWithBLOBsStatementId()));

        intrp.setPrimaryKeyType(primaryKeyTypeToId(intrp.getPrimaryKeyType()));

    }

    private static String exampleToCondition(String stringWithExample) {
        return EXAMPLE.matcher(stringWithExample).replaceAll(Matcher.quoteReplacement("Condition"));
    }

    private static String primaryKeyToId(String stringWithPrimaryKey) {
        return PRIMARY_KEY.matcher(stringWithPrimaryKey).replaceAll(Matcher.quoteReplacement("Id"));
    }

    private static String primaryKeyTypeToId(String stringWithPrimaryKeyType) {
        return KEY_$.matcher(stringWithPrimaryKeyType).replaceAll("Id");
    }

}