package com.mybatis.generator.plugin;

import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * @author wanghongen
 * 27/02/2018
 */
public class LombokPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }


}
