package com.ruoyi.generator.domain;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.mybatis.MybatisId;
import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 业务表 gen_table
 *
 * @author ruoyi
 */
public class GenTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @MybatisId
    private Long tableId;

    /** 表名称 */
    @NotBlank(message = "表名称 can not blank")
    private String tableName;

    /** 表描述 */
    @NotBlank(message = "表描述 can not blank")
    private String tableComment;

    /** 关联父表的表名 */
    private String subTableName;

    /** 本表关联父表的外键名 */
    private String subTableFkName;

    /** 实体类名称(首字母大写) */
    @NotBlank(message = "实体类名称 can not blank")
    private String className;

    /** 使用的模板（crud单表操作 tree树表操作 sub主子表操作） */
    private String tplCategory;

    /** 生成包路径 */
    @NotBlank(message = "生成包路径 can not blank")
    private String packageName;

    /** 生成模块名 */
    @NotBlank(message = "生成模块名 can not blank")
    private String moduleName;

    /** 生成业务名 */
    @NotBlank(message = "生成业务名 can not blank")
    private String businessName;

    /** 生成功能名 */
    @NotBlank(message = "生成功能名 can not blank")
    private String functionName;

    /** 生成作者 */
    @NotBlank(message = "作者 can not blank")
    private String functionAuthor;

    /** 生成代码方式（0zip压缩包 1自定义路径） */
    private String genType;

    /** 生成路径（不填默认项目路径） */
    private String genPath;

    /** 主键信息 */
    private GenTableColumn pkColumn;

    /** 子表信息 */
    private GenTable subTable;

    /** 表列信息 */
    @Valid
    private List<GenTableColumn> columns;

    /** 其它生成选项 */
    private String options;

    /** 树编码字段 */
    private String treeCode;

    /** 树父编码字段 */
    private String treeParentCode;

    /** 树名称字段 */
    private String treeName;

    /** 上级菜单ID字段 */
    private String parentMenuId;

    /** 上级菜单名称字段 */
    private String parentMenuName;

    /** 是否是审计表 */
    private Integer audit;

    public Integer getAudit() {
        if (this.audit == null) {
            this.audit = calcAudit();
        }
        return audit;
    }

    private int calcAudit() {
        if (this.columns == null) {
            return 0;
        }
        return (
                this.columns.stream().anyMatch(it -> StrUtil.equalsIgnoreCase(it.getColumnName(), "create_time") && StrUtil.startWithIgnoreCase(it.getColumnType(), "datetime"))
                        && this.columns.stream().anyMatch(it -> StrUtil.equalsIgnoreCase(it.getColumnName(), "update_time") && StrUtil.startWithIgnoreCase(it.getColumnType(), "datetime"))
                        && this.columns.stream().anyMatch(it -> StrUtil.equalsIgnoreCase(it.getColumnName(), "create_by") && StrUtil.startWithIgnoreCase(it.getColumnType(), "bigint"))
                        && this.columns.stream().anyMatch(it -> StrUtil.equalsIgnoreCase(it.getColumnName(), "update_by") && StrUtil.startWithIgnoreCase(it.getColumnType(), "bigint"))
                        ? 1 : 0
        );
    }

    /**
     * 有省市区
     *
     * @return 是否有
     */
    public boolean hasPct() {
        if (this.columns == null) {
            return false;
        }
        return this.columns.stream().anyMatch(it -> StrUtil.equalsIgnoreCase(it.getJavaField(), "provinceId"))
                && this.columns.stream().anyMatch(it -> StrUtil.equalsIgnoreCase(it.getJavaField(), "cityId"))
                && this.columns.stream().anyMatch(it -> StrUtil.equalsIgnoreCase(it.getJavaField(), "townId"));
    }

    public boolean hasRichText() {
        if (this.columns == null) {
            return false;

        }
        return this.columns.stream().anyMatch(it -> Objects.equals(it.getHtmlType(), "editor"));
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Long getTableId()
    {
        return tableId;
    }

    public void setTableId(Long tableId)
    {
        this.tableId = tableId;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableComment()
    {
        return tableComment;
    }

    public void setTableComment(String tableComment)
    {
        this.tableComment = tableComment;
    }

    public String getSubTableName()
    {
        return subTableName;
    }

    public void setSubTableName(String subTableName)
    {
        this.subTableName = subTableName;
    }

    public String getSubTableFkName()
    {
        return subTableFkName;
    }

    public void setSubTableFkName(String subTableFkName)
    {
        this.subTableFkName = subTableFkName;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getTplCategory()
    {
        return tplCategory;
    }

    public void setTplCategory(String tplCategory)
    {
        this.tplCategory = tplCategory;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String getBusinessName()
    {
        return businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getFunctionName()
    {
        return functionName;
    }

    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }

    public String getFunctionAuthor()
    {
        return functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor)
    {
        this.functionAuthor = functionAuthor;
    }

    public String getGenType()
    {
        return genType;
    }

    public void setGenType(String genType)
    {
        this.genType = genType;
    }

    public String getGenPath()
    {
        return genPath;
    }

    public void setGenPath(String genPath)
    {
        this.genPath = genPath;
    }

    public GenTableColumn getPkColumn()
    {
        return pkColumn;
    }

    public void setPkColumn(GenTableColumn pkColumn)
    {
        this.pkColumn = pkColumn;
    }

    public GenTable getSubTable()
    {
        return subTable;
    }

    public void setSubTable(GenTable subTable)
    {
        this.subTable = subTable;
    }

    public List<GenTableColumn> getColumns()
    {
        return columns;
    }

    public void setColumns(List<GenTableColumn> columns)
    {
        this.columns = columns;
    }

    public String getOptions()
    {
        return options;
    }

    public void setOptions(String options)
    {
        this.options = options;
    }

    public String getTreeCode()
    {
        return treeCode;
    }

    public void setTreeCode(String treeCode)
    {
        this.treeCode = treeCode;
    }

    public String getTreeParentCode()
    {
        return treeParentCode;
    }

    public void setTreeParentCode(String treeParentCode)
    {
        this.treeParentCode = treeParentCode;
    }

    public String getTreeName()
    {
        return treeName;
    }

    public void setTreeName(String treeName)
    {
        this.treeName = treeName;
    }

    public String getParentMenuId()
    {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId)
    {
        this.parentMenuId = parentMenuId;
    }

    public String getParentMenuName()
    {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName)
    {
        this.parentMenuName = parentMenuName;
    }

    public boolean isSub()
    {
        return isSub(this.tplCategory);
    }

    public static boolean isSub(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_SUB, tplCategory);
    }

    public boolean isTree()
    {
        return isTree(this.tplCategory);
    }

    public static boolean isTree(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
    }

    public boolean isCrud()
    {
        return isCrud(this.tplCategory);
    }

    public static boolean isCrud(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
    }

    public boolean isSuperColumn(String javaField)
    {
        return isSuperColumn(this.tplCategory, javaField);
    }

    public boolean isSuperColumn(String tplCategory, String javaField)
    {
        // 树节点
        if (isTree(tplCategory)) {
            return StringUtils.equalsAnyIgnoreCase(javaField,
                    ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
        }
        // 审计节点
        if (Objects.equals(getAudit(), 1)) {
            return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
        }
        return false;
    }

    public boolean hasDate() {
        return columns.stream().anyMatch(GenTableColumn::isDate);
    }

    public List<String> dateImports() {
        return columns.stream().filter(GenTableColumn::isDate)
                .map(it -> StrUtil.equals(it.getJavaType(), GenConstants.TYPE_DATE) ? "java.util.Date" : ("java.time." + it.getJavaType()))
                .distinct().collect(Collectors.toList());
    }
}
