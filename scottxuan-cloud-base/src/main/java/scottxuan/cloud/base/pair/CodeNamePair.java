package scottxuan.cloud.base.pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeNamePair implements Serializable {
    private static final long serialVersionUID = -8553839377421958791L;

    private String code;
    private String name;

    public String toString() {
        if (this.name == null) {
            return this.code;
        } else {
            int len = this.code.length() + 1 + this.name.length();
            StringBuilder buffer = new StringBuilder(len);
            buffer.append(this.code);
            buffer.append(":");
            buffer.append(this.name);
            return buffer.toString();
        }
    }
}
