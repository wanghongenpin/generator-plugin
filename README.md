## Mybatis Generator 插件
>
重新生成覆盖原mapper.XML文件
`<plugin type="com.mybatis.generator.plugin.OverIsMergeablePlugin" />`
生成中文注释
<commentGenerator type="com.mybatis.generator.comment.MyCommentGenerator">
        <!-- 是否禁止显示日期 true：是 ： false:否 -->
        <property name="suppressDate" value="false" />
        <!-- 是否去除自动生成的所有注释 true：是 ： false:否 -->
        <property name="suppressAllComments" value="false" />
</commentGenerator>