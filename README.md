## Mybatis Generator 插件

### 1.重新生成覆盖原mapper.XML文件
```xml
<plugin type="com.mybatis.generator.plugin.OverIsMergeablePlugin" />
```

### 2.生成中文注释
```xml
<commentGenerator type="com.mybatis.generator.comment.MyCommentGenerator">     
    <!-- 是否禁止显示日期 true：是 ： false:否 -->  
    <property name="suppressDate" value="false" />  
    <!-- 是否去除自动生成的所有注释 true：是 ： false:否 -->  
    <property name="suppressAllComments" value="false" />  
</commentGenerator>
```
### 3.修改生成的Example包名、类名和Mapper类中的方法名或参数名
>
```xml
<!-- 修改生成的Example类的类名和Mapper类中的方法名或参数名 -->
<plugin type="com.mybatis.generator.plugin.RenameExampleClassPlugin">
       <property name="searchString" value="Example$"/>
       <property name="replaceString" value="Condition"/>
</plugin>
```
 
 ### 4.Lombok插件 默认在实体类里增加@Data 不生成Get、Set方法
 ```xml
<!-- Lombok插件 -->
<plugin type="com.mybatis.generator.plugin.LombokPlugin">
    <property name="lombokAnnotations" value="@Data,@Builder"/>
</plugin>
```
 ### 5.Example和mapper类中文注释
 ```xml
<!-- Example和mapper类中文注释 -->
<plugin type="com.mybatis.generator.plugin.CommentPlugin"/>
```