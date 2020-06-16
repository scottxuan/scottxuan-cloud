package scottxuan.cloud.base.pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : zhaoxuan
 * @date : 2020/3/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICodeNamePair implements Serializable {
    private Integer code;
    private String name;
    public String toString() {
        if (this.name == null) {
            return this.code+"";
        } else {
            int len = (this.code+"").length() + 1 + this.name.length();
            StringBuilder buffer = new StringBuilder(len);
            buffer.append(this.code);
            buffer.append(":");
            buffer.append(this.name);
            return buffer.toString();
        }
    }
}
