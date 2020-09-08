package org.karl.sh.core.templates;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author KARL ROSE
 * @date 2020/5/18 12:07
 **/
@Data
public class BaseModel implements Serializable {

    private Boolean delete;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDatetime;

    private Long createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDatetime;

    private Long updateUser;


}
