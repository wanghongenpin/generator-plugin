## Mybatis Generator 插件

### 1.重新生成覆盖原mapper.XML文件
>
`<plugin type="com.mybatis.generator.plugin.OverIsMergeablePlugin" />`
### 2.生成中文注释
>
`<commentGenerator type="com.mybatis.generator.comment.MyCommentGenerator"> 
        <!-- 是否禁止显示日期 true：是 ： false:否 -->
        <property name="suppressDate" value="false" />
        <!-- 是否去除自动生成的所有注释 true：是 ： false:否 -->
        <property name="suppressAllComments" value="false" />
</commentGenerator>`
### 3.Example生成位置
>
`<!-- Example 目标包修改插件 -->
<plugin type="com.mybatis.generator.plugin.ExampleTargetPlugin">
     <!-- example包名 -->
     <property name="targetPackage" value="${example.target.package}"/>
 </plugin>`