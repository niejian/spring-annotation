package cn.com.spring.bean;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "oa_no_code")
public class OaNoCode {
    @Id //如果没有@Id的标识，ioc容器就会报错
    @Column(name = "codeid")
    private String codeid;
    @Column(name = "command_name")
    private String commandName;
    @Column(name = "type_code")
    private String typeCode;
    @Column(name = "command_type")
    private String commandType;
    @Column(name = "increasse_rule")
    private String increasseRule;
    @Column(name = "last_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTime;
    @Column(name = "code_num")
    private String codeNum;
    @Column(name = "remark")
    private String remark;


    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getIncreasseRule() {
        return increasseRule;
    }

    public void setIncreasseRule(String increasseRule) {
        this.increasseRule = increasseRule;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OaNoCode{" +
                "codeid='" + codeid + '\'' +
                ", commandName=" + commandName +
                ", typeCode='" + typeCode + '\'' +
                ", commandType='" + commandType + '\'' +
                ", increasseRule='" + increasseRule + '\'' +
                ", lastTime=" + lastTime +
                ", codeNum='" + codeNum + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

