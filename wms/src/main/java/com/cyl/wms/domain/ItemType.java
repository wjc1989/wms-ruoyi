package com.cyl.wms.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Goods 类型表对象 wms_item_type
 *
 * @author zhangcheng
 */
@ApiModel(description = "Goods")
@TableName("wms_item_type")
public class ItemType extends BaseAudit {

    @ApiModelProperty("Goods Type ID")
    @TableId(value = "item_type_id", type = IdType.AUTO)
    private Long itemTypeId;

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setChildren(List<ItemType> children) {
        this.children = children;
    }

    @ApiModelProperty("Parent ID")
    @Excel(name = "Parent ID")
    private Long parentId;

    @ApiModelProperty("Top")
    @Excel(name = "Top")
    private String ancestors;

    @ApiModelProperty("Goods Type Name")
    @Excel(name = "Goods Type Name")
    private String typeName;

    @ApiModelProperty("Sort")
    @Excel(name = "Sort")
    private String orderNum;

    @ApiModelProperty("Status")
    @Excel(name = "Status", readConverterExp = "0=Normal,1=Disabled")
    private String status;

    @ApiModelProperty("DeleteFlag（0 Normal 2 Deleted）")
    private String delFlag;

    /**
     * 父部门名称
     */
    @TableField(exist = false)
    private String parentName = "11";
    /**
     * 子部门
     */
    @TableField(exist = false)
    private List<ItemType> children = new ArrayList<ItemType>();

    public List<ItemType> getChildren() {
        return children;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }


    @NotBlank(message = "Sort can not blank")
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("itemTypeId", getItemTypeId())
                .append("parentId", getParentId())
                .append("ancestors", getAncestors())
                .append("typeName", getTypeName())
                .append("orderNum", getOrderNum())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
