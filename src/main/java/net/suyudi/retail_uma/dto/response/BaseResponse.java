package net.suyudi.retail_uma.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private boolean status;
    private String code;
    private String message;
    private T data;

    public static BaseResponse error(String code, String message) {
        return new BaseResponse<>(false, code, message, null);
    }

    public static BaseResponse ok() {
        return new BaseResponse<>(true, "200", "success", null);
    }

    public static <I> BaseResponse<I> ok(I body) {
        return new BaseResponse<I>(true, "200", "success", body);
    }

    public static BaseResponse created() {
        return new BaseResponse<>(true, "201", "created", null);
    }

    public static <I> BaseResponse created(I body) {
        return new BaseResponse<I>(true, "201", "created", body);
    }

}
